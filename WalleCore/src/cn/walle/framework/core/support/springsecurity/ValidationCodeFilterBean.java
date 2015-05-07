package cn.walle.framework.core.support.springsecurity;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.GenericFilterBean;

import com.octo.captcha.image.ImageCaptcha;

public class ValidationCodeFilterBean extends GenericFilterBean {

    private String validationCodeParameter = "j_validation_code";

    private String filterProcessesUrl;
    private String defaultFailureUrl;
    private boolean forwardToDestination = false;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (! requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        
		String requestValidationCode = request.getParameter(validationCodeParameter);

		HttpSession session = request.getSession();
		Object validationObject = session.getAttribute(validationCodeParameter);
		session.removeAttribute(validationCodeParameter);

		if (validationObject instanceof String) {
			if (! ((String) validationObject).equals(requestValidationCode)) {
				onAuthenticationFailure(request, response);
				return;
			}
		} else if (validationObject instanceof ImageCaptcha) {
			if (! ((ImageCaptcha) validationObject).validateResponse(requestValidationCode)) {
				onAuthenticationFailure(request, response);
				return;
			}
		} else {
			onAuthenticationFailure(request, response);
			return;
		}
		
        chain.doFilter(request, response);
	}

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }
    
    protected void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException {
        if (defaultFailureUrl == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "validation code error");
        } else {
            if (forwardToDestination) {
                request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
            } else {
                redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
            }
        }
    }
    
	public String getValidationCodeParameter() {
		return validationCodeParameter;
	}

	public void setValidationCodeParameter(String validationCodeParameter) {
		this.validationCodeParameter = validationCodeParameter;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	public boolean isForwardToDestination() {
		return forwardToDestination;
	}

	public void setForwardToDestination(boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

}
