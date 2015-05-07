package cn.walle.framework.core.support.springsecurity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.StringUtils;

public class StatelessSecurityContextRepository implements SecurityContextRepository, ApplicationListener<AbstractAuthenticationEvent>{

	private static final String DELIMITER = ":";
    
	private String cookieName = "SESSION";

	private String key = "KEY_";

	private ThreadLocal<HttpRequestResponseHolder> requestResponseHolder = new ThreadLocal<HttpRequestResponseHolder>();

    public boolean containsContext(HttpServletRequest request) {
    	if (request.getCookies() == null) {
    		return false;
    	}
    	for (Cookie cookie : request.getCookies()) {
			if (cookieName.equals(cookie.getName())) {
				return true;
			}
		}
        return false;
    }

    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
    	this.requestResponseHolder.set(requestResponseHolder);

    	SecurityContext context = SecurityContextHolder.createEmptyContext();
    	HttpServletRequest request = requestResponseHolder.getRequest();
    	if (request.getCookies() == null) {
    		return context;
    	}
    	for (Cookie cookie : request.getCookies()) {
			if (cookieName.equals(cookie.getName())) {
				try {
					String[] cookieValues = StringUtils.delimitedListToStringArray(cookie.getValue(), DELIMITER);
					if (cookieValues.length != 2) {
						throw new InvalidCookieException("signature error");
					}
					String token = cookieValues[0];
					String signatureValue = cookieValues[1];
					String expectedTokenSignature = makeTokenSignature(token);
					if (! expectedTokenSignature.equals(signatureValue)) {
						throw new InvalidCookieException("signature error");
					}
					byte[] bytes = Base64.decode(token.getBytes());
					ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
					ObjectInputStream ois = new ObjectInputStream(bais);
					AcegiUserDetails userDetails = (AcegiUserDetails) ois.readObject();
					Authentication authentication = new TestingAuthenticationToken(userDetails, null, "ROLE_USER");
					context.setAuthentication(authentication);
					return context;
				} catch (Exception ex) {
					throw new AuthenticationServiceException(ex.toString(), ex);
				}
			}
		}
        return context;
    }

	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		//can not save cookie here, it will be lost after sendRedirect to the index page.
		//use AuthenticationEvent instead.
    }
	
    @Override
	public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {
    	if (authenticationEvent instanceof AuthenticationSuccessEvent
    			|| authenticationEvent instanceof InteractiveAuthenticationSuccessEvent) {
        	Authentication authentication = authenticationEvent.getAuthentication();
        	if (authentication != null) {
        		Object principal = authentication.getPrincipal();
        		if (principal instanceof AcegiUserDetails) {
        			try {
        				ByteArrayOutputStream baos = new ByteArrayOutputStream();
        				ObjectOutputStream oos = new ObjectOutputStream(baos);
        				oos.writeObject(principal);
        				String token = new String(Base64.encode(baos.toByteArray()));
        		        String signatureValue = makeTokenSignature(token);
        		        String cookieValue = token + DELIMITER + signatureValue;
        				Cookie cookie = new Cookie(cookieName, cookieValue);
        				String cookiePath = this.requestResponseHolder.get().getRequest().getContextPath();
        				if(! StringUtils.hasLength(cookiePath)) {
        					cookiePath = "/";
        				}
        				cookie.setPath(cookiePath);
        				cookie.setMaxAge(-1);
        				this.requestResponseHolder.get().getResponse().addCookie(cookie);
        			} catch (Exception ex) {
    					throw new AuthenticationServiceException(ex.toString(), ex);
        			}
        		}
        	}
    	}
	}

    protected String makeTokenSignature(String token) {
        String data = token + DELIMITER + this.getKey();
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(data.getBytes())));
    }

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
