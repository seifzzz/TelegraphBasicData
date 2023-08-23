package systemData.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CHG_Password_Request {
		
		@JsonProperty("userName")
		private String userName;
		
		@JsonProperty("oldPassword")
		private String oldPassword;
		
		@JsonProperty("newPassword")
		private String newPassword;

}
