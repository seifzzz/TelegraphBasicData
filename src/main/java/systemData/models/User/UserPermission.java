package systemData.models.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="SC_USER_PERMISSION", schema = "FRM_WFM_SEC")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
@SuperBuilder
public class UserPermission{
	
	@Id
	private Long PERMISSION_ID;
	private Long USER_ID;
	private Long MODULE_ID;
	private String USER_NAME;
	private String PERMISSION_NAME;
	
}
