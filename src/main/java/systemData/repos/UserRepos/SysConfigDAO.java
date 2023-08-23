package systemData.repos.UserRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import systemData.models.User.WLSConfig;


@Repository
public interface SysConfigDAO extends JpaRepository<WLSConfig, String>{

	@Query(value="select * from FRM_WFM_SEC.web_logic_conf" , nativeQuery = true)
	public WLSConfig getWLSConfig();

}
