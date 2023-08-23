package systemData.models.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEB_LOGIC_CONF", schema = "FRM_WFM_SEC")
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
	
	
	public String getPROTOCOL() {
		return PROTOCOL;
	}
	public void setPROTOCOL(String pROTOCOL) {
		PROTOCOL = pROTOCOL;
	}
	public String getSERVER_IP() {
		return SERVER_IP;
	}
	public void setSERVER_IP(String sERVER_IP) {
		SERVER_IP = sERVER_IP;
	}
	public String getSERVER_PORT() {
		return SERVER_PORT;
	}
	public void setSERVER_PORT(String sERVER_PORT) {
		SERVER_PORT = sERVER_PORT;
	}
	public String getSERVER_USERNAME() {
		return SERVER_USERNAME;
	}
	public void setSERVER_USERNAME(String sERVER_USERNAME) {
		SERVER_USERNAME = sERVER_USERNAME;
	}
	public String getSERVER_PASSWORD() {
		return SERVER_PASSWORD;
	}
	public void setSERVER_PASSWORD(String sERVER_PASSWORD) {
		SERVER_PASSWORD = sERVER_PASSWORD;
	}
	public String getMAINSCREEN_IP() {
		return MAINSCREEN_IP;
	}
	public void setMAINSCREEN_IP(String mAINSCREEN_IP) {
		MAINSCREEN_IP = mAINSCREEN_IP;
	}
	public String getMAINSCREEN_PORT() {
		return MAINSCREEN_PORT;
	}
	public void setMAINSCREEN_PORT(String mAINSCREEN_PORT) {
		MAINSCREEN_PORT = mAINSCREEN_PORT;
	}
	public String getIS_SSL() {
		return IS_SSL;
	}
	public void setIS_SSL(String iS_SSL) {
		IS_SSL = iS_SSL;
	}
	
}
