package systemData.security;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import systemData.models.User.User;
import systemData.repos.UserRepos.UserPermissionRepo;
import systemData.repos.UserRepos.UserRepo;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
    UserRepo userRepo;
	
	@Value("${module.id}")
	private Long module_id;
	
	@Autowired
    UserPermissionRepo userPermRepo;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("username in UserDetailsServiceImpl=" +username);
		
		User user = userRepo.findByUSERNAME(username);
		
		if(user != null) {
			user.setEMP_ORG(userRepo.getEmpOrg(user.getWORKER_ID()));
			user.setPERMISSIONS(userPermRepo.getUserPermissionsByUSER_NAME(username, module_id));
			System.out.println("user in UserDetailsServiceImpl=" +user);
		}
		else
			throw new UsernameNotFoundException("User: "+ username +" not found!");
		
		return user;
	}

}
