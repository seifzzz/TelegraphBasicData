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
@Table(name = "SC_USER_MODULE", schema = "FRM_WFM_SEC")
public class SC_USER_MODULE {
	
	@Id
	private String USER_ID;
	private String USER_NAME;
	private String MODULE_ID;
	private String MODULE_NAME;
	private String MODULE_NAME_AR;
	private String PATH;
	

}
