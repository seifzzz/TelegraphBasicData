package systemData.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import systemData.models.User.WLSConfig;
import systemData.repos.UserRepos.SysConfigDAO;

@Service
public class SysConfigService {

	@Autowired
    SysConfigDAO dao;
	
	
	public WLSConfig getWLSConfig() {
		return dao.getWLSConfig();
	}
	
}
