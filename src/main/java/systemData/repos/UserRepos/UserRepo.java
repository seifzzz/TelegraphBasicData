package systemData.repos.UserRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import systemData.models.User.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	@Query(value = "select USER_ID,USER_NAME,EMAIL_ADDRESS,DISPLAY_NAME,LAST_MODIFIED_BY,ORG_UNIT_ID,WORKER_ID from FRM_WFM_SEC.sc_users where lower( user_name)=lower(:USERNAME)", nativeQuery = true)
	User findByUSERNAME(String USERNAME);
		  

	Boolean existsByUSERNAME(String USERNAME);
	
	@Query(value = "select org_role from MTS_WFM_2017.wf_emp_role where emp_role_id=:Worker_id",nativeQuery = true)
	String getEmpOrg(String Worker_id);
	
	@Query(value = "select DISPLAY_NAME from FRM_WFM_SEC.sc_users where lower( user_name)=lower(:USERNAME)", nativeQuery = true)
	String getDisplayNameByUSER_NAME(String USERNAME);
	
}
