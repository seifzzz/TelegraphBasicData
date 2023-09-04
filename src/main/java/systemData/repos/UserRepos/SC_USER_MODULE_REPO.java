package systemData.repos.UserRepos;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import systemData.models.User.SC_USER_MODULE;

import javax.transaction.Transactional;

public interface SC_USER_MODULE_REPO extends JpaRepository<SC_USER_MODULE, String> {
	@Transactional
	@Query(value="select USER_ID,MODULE_ID,MODULE_NAME,MODULE_NAME_AR,PATH from MTS_SECURITY.SC_USER_MODULE where lower(USER_NAME)=lower(:USER_NAME)", nativeQuery = true)
	public List getUserModulesByUSER_NAME(String USER_NAME);

}
