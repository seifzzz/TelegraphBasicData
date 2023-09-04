package systemData.repos.UserRepos;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import systemData.models.User.UserPermission;

public interface UserPermissionRepo extends JpaRepository<UserPermission, Long>{

	@Query(value="SELECT * FROM MTS_SECURITY.SC_USER_PERMISSION WHERE lower(USER_NAME) = lower(?1) and MODULE_ID = ?2", nativeQuery = true)
	public Set<UserPermission> getUserPermissionsByUSER_NAME(String USER_NAME, Long MODULE_ID);

	@Query(value="SELECT * FROM MTS_SECURITY.SC_USER_PERMISSION WHERE lower(USER_NAME) = lower(:USER_NAME) and MODULE_ID = :MODULE_ID and permission_name=:PERMISSION_NAME", nativeQuery = true)
	public List<UserPermission> checkUserPermission(String USER_NAME, Long MODULE_ID, String PERMISSION_NAME);


}
