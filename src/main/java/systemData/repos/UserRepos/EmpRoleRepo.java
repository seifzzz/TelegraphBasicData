package systemData.repos.UserRepos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import systemData.models.User.WF_EMP_ROLE;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRoleRepo extends JpaRepository<WF_EMP_ROLE, String> {
	@Query(value="SELECT EMP_ROLE_ID,ORG_ROLE FROM MTS_WFM_2017.WF_EMP_ROLE WHERE EMP_ROLE_ID=?1",nativeQuery = true)
	public EmpRoleRepo FindEmpRoleByEMP_ROLE_ID(String EMP_ROLE_ID);
	
}
