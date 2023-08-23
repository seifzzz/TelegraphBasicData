package systemData.services.UserService;

import java.net.SocketException;
import systemData.payload.request.LoginRequest;
import systemData.payload.response.LoginResponse;

public interface AuthService {

	public LoginResponse authenticateUser(LoginRequest loginRequest)throws SocketException;
	public void Logout();
	public String retUserName();

	
}
