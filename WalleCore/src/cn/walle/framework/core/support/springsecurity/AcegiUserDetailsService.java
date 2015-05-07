package cn.walle.framework.core.support.springsecurity;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * There must be ONE Spring bean implements this interface,
 * The implementation bean will be autowired by the Spring Security AuthenticationProvider
 * to get UserDetails by the user name.
 * @author cj
 *
 */
public interface AcegiUserDetailsService {

	/**
	 * {@inheritDoc}
	 */
	public AcegiUserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException;

}
