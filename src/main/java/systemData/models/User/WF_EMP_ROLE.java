package systemData.models.User;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WF_EMP_ROLE", schema = "MTS_SECURITY")

public class WF_EMP_ROLE {
	
	@Id
	private String EMP_ROLE_ID;
	private String SKILL_LEVEL;
	private String PARTY_ROLE_ID;

	private String PARTY_ID;
	private String ORG_ROLE;
	private String CURRENT_LATITUDE;
	private String CURRENT_LONGITUDE;
	private String LOCATION_GEOMETRY;
	private String POOL_FLAG;

	
	

}
