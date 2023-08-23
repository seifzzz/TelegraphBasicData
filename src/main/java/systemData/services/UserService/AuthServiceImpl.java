package systemData.services.UserService;

import java.net.SocketException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import systemData.models.User.SC_USER_MODULE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import systemData.payload.request.LoginRequest;
import systemData.payload.response.LoginResponse;
import systemData.repos.UserRepos.SC_USER_MODULE_REPO;
import systemData.repos.UserRepos.SysConfigDAO;
import systemData.repos.UserRepos.UserRepo;
import systemData.security.UserDetailsServiceImpl;


@Service
@RequiredArgsConstructor
@Data
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private SysConfigService service;
	
	@Autowired
    SysConfigDAO dao;
	static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private HttpServletResponse httpResponse;
	
	@Autowired
    SC_USER_MODULE_REPO sc_USER_MODULE_REPO;
	
	@Autowired
    UserRepo userRepo;
	
	private String J_S_ID;

	String ip;
	String jwt ;
	String user_name;
	@Override
	public LoginResponse authenticateUser(LoginRequest loginRequest) throws SocketException {
		
		request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
	            .getRequest();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		user_name=loginRequest.getUsername();

		UserDetails user = (UserDetails) authentication.getPrincipal();
		System.out.println("user=  "+user);
		
		String displayName=userRepo.getDisplayNameByUSER_NAME(user_name);
		System.out.println("DisplayName=  "+ displayName);

		user.getAuthorities();
		System.out.println("authorities in Login= " + user.getAuthorities());
		
		@SuppressWarnings("unchecked")
		List<SC_USER_MODULE> MODULES=sc_USER_MODULE_REPO.getUserModulesByUSER_NAME(user_name);
				
		SecurityContextHolder.getContext().setAuthentication(authentication);

		logger.info("Successful login for user: {}", user_name);
		return new LoginResponse(displayName, user.getAuthorities(),MODULES);
	}
	
	
	@Override
	public void Logout()
	{		
		request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
	            .getRequest();
		request.getSession().removeAttribute(user_name);
		System.out.println("Session_user_name_In_Logout= "+ user_name);
		
		request.getSession().invalidate();
		System.out.println("Session= " + request.getSession());	
		request.getSession(false);
		System.out.println("INVALIDATE= " + request.getSession(false));
		
		logger.info("Successful logout for user: {}", user_name);
	}

	@Override
	public String retUserName() {
		    request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
	            .getRequest();
			Cookie[] cookies = request.getCookies();
			System.out.println("Cookies in retUserName= " + cookies);
		    if(cookies !=null){
		    for(Cookie cookie : cookies){
		    	System.out.println("cookie= " + cookie);
		    	System.out.println("cookie getName=" + cookie.getName());
		    	System.out.println("cookie getValue=" + cookie.getValue());
		    	System.out.println("cookie JSESSIONID check=" + cookie.getName().equals("JSESSIONID"));
		    	if(cookie.getName().equals("JSESSIONID")){
		    		J_S_ID=cookie.getValue();
					System.out.println("JSESSIONID in retUserName= "+ J_S_ID);
					break;
				 }
		      }
		    } 
			String WL_IP=dao.getWLSConfig().getMAINSCREEN_IP();
			RestTemplate rt = new RestTemplate();
			String URL = WL_IP+"/GIS_Security_Auth/rest/v0/CurrentUser";
			System.out.println("URL= " + URL);
			//HttpHeaders headers= new HttpHeaders();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.set("Host",request.getServerName()+ ":" +request.getServerPort());
			headers.set("Cookie","JSESSIONID="+ J_S_ID);
			headers.set("Content-Type", "application/json");
			HttpEntity<?> httpEntity= new HttpEntity<>(headers);
			ResponseEntity<?> Response=rt.exchange(URL, HttpMethod.GET, httpEntity , String.class);
			System.out.println("Response= " + Response.getBody().toString());
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			JsonNode json = null;
			String currentUser = null;
			try {
				json = objectMapper.readTree(Response.getBody().toString());
				currentUser= json.get("items").get(0).get("UserName").asText();
				System.out.println("currentUser= " + currentUser);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return currentUser;
		}

}
