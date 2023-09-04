package systemData.models.User;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "WEB_LOGIC_CONF", schema = "MTS_SECURITY")
public class WLSConfig{

	
	@Id
	private String SERVER_IP;
	private String SERVER_PORT;
	private String SERVER_USERNAME;
	private String SERVER_PASSWORD;
	private String MAINSCREEN_IP;
	private String MAINSCREEN_PORT;
	private String IS_SSL;
	private String PROTOCOL;


	public void setPROTOCOL(String pROTOCOL) {
		PROTOCOL = pROTOCOL;
	}

	public void setSERVER_IP(String sERVER_IP) {
		SERVER_IP = sERVER_IP;
	}

	public void setSERVER_PORT(String sERVER_PORT) {
		SERVER_PORT = sERVER_PORT;
	}

	public void setSERVER_USERNAME(String sERVER_USERNAME) {
		SERVER_USERNAME = sERVER_USERNAME;
	}

	public void setSERVER_PASSWORD(String sERVER_PASSWORD) {
		SERVER_PASSWORD = sERVER_PASSWORD;
	}

	public void setMAINSCREEN_IP(String mAINSCREEN_IP) {
		MAINSCREEN_IP = mAINSCREEN_IP;
	}

	public void setMAINSCREEN_PORT(String mAINSCREEN_PORT) {
		MAINSCREEN_PORT = mAINSCREEN_PORT;
	}

	public void setIS_SSL(String iS_SSL) {
		IS_SSL = iS_SSL;
	}
	
}
