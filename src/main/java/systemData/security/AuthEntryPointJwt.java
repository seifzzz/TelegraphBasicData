package systemData.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	int Time_Fail;
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		logger.error("Unauthorized error: {}", authException.getMessage());
		
		if(Time_Fail==1) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You Are Logged Out because of Timeout");
		}
		
		else if(Time_Fail==2) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
		}
		else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized !");
		}
		
	}

}
