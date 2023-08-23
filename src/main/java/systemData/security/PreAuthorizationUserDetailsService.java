package systemData.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;

@SuppressWarnings("rawtypes")
public class PreAuthorizationUserDetailsService implements AuthenticationUserDetailsService{

	  @Override
	  public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException
	  {
	    PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails tokenDetails =
	        (PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails) token.getDetails();

	    return new User(
	        (String) token.getPrincipal(),
	        "unused", true, true, true, true,
	        tokenDetails.getGrantedAuthorities());
	  }


}
