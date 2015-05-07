package cn.walle.framework.core.support.springsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class SessionContext {

	public static AcegiUserDetails getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof AcegiUserDetails) {
			return (AcegiUserDetails) principal;
		} else {
			return null;
		}
	}
	
}
