package systemData.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import systemData.payload.request.LoginRequest;
import systemData.repos.UserRepos.UserPermissionRepo;
import systemData.repos.UserRepos.UserRepo;
import systemData.services.UserService.SysConfigService;

@Component
public class WLSAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private SysConfigService service;
	
	WLSJmxInterface wlsAuth = new WLSJmxInterface();

	private String userNAME;
	
	@Value("${module.id}")
	private Long MODULE_ID;
	
	private UserRepo userRepository;
	private UserPermissionRepo userPermRepo;
	
	private LoginRequest loginRequest;
	
	public WLSAuthenticationProvider(UserRepo userRepository, UserPermissionRepo userPermRepo) {
		this.userRepository = userRepository;
		this.userPermRepo = userPermRepo;
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		System.out.println("name in WLSAuthenticationProvider = " + name);
               	
        	if(name!= null) {
        		wlsAuth.postConstruct(service.getWLSConfig());
    			systemData.models.User.User user = this.userRepository.findByUSERNAME(name);
    			user.setPERMISSIONS(userPermRepo.getUserPermissionsByUSER_NAME(name, MODULE_ID));
    			String password = authentication.getCredentials().toString();
    			UserDetails principal = new User(name, password,user.getAuthorities());
            	return new UsernamePasswordAuthenticationToken(principal, password,principal.getAuthorities());
    		} 
    		else {
    			userNAME=loginRequest.getUsername();
    			systemData.models.User.User user = this.userRepository.findByUSERNAME(userNAME);
    			user.setPERMISSIONS(userPermRepo.getUserPermissionsByUSER_NAME(userNAME, MODULE_ID));
    			UserDetails principal = new User(name, authentication.getCredentials().toString(), user.getAuthorities());
            	return new UsernamePasswordAuthenticationToken(principal, null,principal.getAuthorities());
    		}
        } 

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
