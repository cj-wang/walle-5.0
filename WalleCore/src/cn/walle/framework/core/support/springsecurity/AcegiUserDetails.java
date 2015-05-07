package cn.walle.framework.core.support.springsecurity;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Sub interface of org.springframework.security.userdetails.UserDetails.
 * Alse hold grantedUrls and forbiddenUrls.
 * @author cj
 *
 */
public interface AcegiUserDetails extends UserDetails {
	
	String getUserUuid();
	
	String getUserId();
	
	String getFullname();
	
	String getTenantId();
	
	List<String> getGrantedUrls();
	
	List<String> getForbiddenUrls();

}
