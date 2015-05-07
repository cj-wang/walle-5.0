package cn.walle.framework.core.support.springsecurity;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Implementation of AccessDecisionVoter to check if a user is granted to access a url.
 * @author cj
 *
 */
public class AcegiAccessDecisionVoter extends RoleVoter {

    protected final Log log = LogFactory.getLog(getClass());

    private PathMatcher pathMatcher = new AntPathMatcher();
    
    private boolean enabled;
    
    private List<String> omitUrls;
	
	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> config) {
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String requestUrl = filterInvocation.getRequestUrl();
//        int firstQuestionMarkIndex = requestUrl.indexOf("?");
//        if (firstQuestionMarkIndex != -1) {
//        	requestUrl = requestUrl.substring(0, firstQuestionMarkIndex);
//        }

		Object principal = authentication.getPrincipal();
		if (principal instanceof AcegiUserDetails) {
			if (! enabled) {
				return ACCESS_GRANTED;
			}

			if (omitUrls != null) {
				for (String omitUrl : omitUrls) {
					if (pathMatcher.match(omitUrl, requestUrl)) {
						return ACCESS_GRANTED;
					}
				}
			}
			
			AcegiUserDetails userDetails = (AcegiUserDetails) principal;
			List<String> grantedUrls = userDetails.getGrantedUrls();
			List<String> forbiddenUrls = userDetails.getForbiddenUrls();
			
			if (forbiddenUrls != null) {
				for (String forbiddenUrl : forbiddenUrls) {
					if (pathMatcher.match(forbiddenUrl, requestUrl)) {
						if (log.isDebugEnabled()) {
							log.debug("User " + userDetails.getUsername() + " request url " + requestUrl + " forbidden by pattern " + forbiddenUrl);
						}
						return ACCESS_DENIED;
					}
				}
			}
			
			if (grantedUrls != null) {
				for (String grantedUrl : grantedUrls) {
					if (pathMatcher.match(grantedUrl, requestUrl)) {
						if (log.isDebugEnabled()) {
							log.debug("User " + userDetails.getUsername() + " request url " + requestUrl + " granted by pattern " + grantedUrl);
						}
						return ACCESS_GRANTED;
					}
				}
			}
			
			if (log.isDebugEnabled()) {
				log.debug("User " + userDetails.getUsername() + " request url " + requestUrl + " not granted");
			}
			return ACCESS_DENIED;
		} else {
			return super.vote(authentication, object, config);
		}
	}

	/**
	 * Set if this voter enabled. If not, returns ACCESS_GRANTED for any url.
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getOmitUrls() {
		return omitUrls;
	}

	public void setOmitUrls(List<String> omitUrls) {
		this.omitUrls = omitUrls;
	}

}
