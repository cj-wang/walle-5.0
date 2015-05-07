package cn.walle.framework.core.support.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AcegiDefaultUserDetailsService implements UserDetailsService {
	
	@Autowired(required = false)
	private AcegiUserDetailsService acegiUserDetailsService;

	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		if (this.acegiUserDetailsService == null) {
			AcegiDefaultUserDetails userDetails = new AcegiDefaultUserDetails();
			userDetails.setUserId(username);
			userDetails.setUsername(username);
			userDetails.setFullname(username);
			userDetails.setPassword("1");
			return userDetails;
		} else {
			return this.acegiUserDetailsService.loadUserByUsername(username);
		}
	}


}
