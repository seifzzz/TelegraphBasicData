package systemData.Controller.AuthControllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;
import systemData.payload.request.CHG_Password_Request;
import systemData.payload.request.LoginRequest;
import systemData.payload.response.APIResponse;
import systemData.payload.response.Response;
import systemData.repos.UserRepos.SysConfigDAO;
import systemData.security.UserDetailsServiceImpl;
import systemData.security.WLSJmxInterface;
import systemData.services.UserService.AuthService;
import systemData.services.UserService.AuthServiceImpl;
import systemData.services.UserService.GlobalService;
import systemData.services.UserService.SysConfigService;

import java.net.SocketException;

import javax.servlet.http.HttpServletResponse;



@CrossOrigin("*")
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final AuthServiceImpl authServiceImpl;
	private static final Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	private SysConfigService service;
	@Autowired
	SysConfigDAO dao;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse httpResponse;
	@Autowired
	private final AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Value("${referer.name}")
	private String refererName;

	WLSJmxInterface wlsAuth = new WLSJmxInterface();

	private String J_S_ID;

	@SuppressWarnings("static-access")
	@PostMapping("signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws SocketException {
		logger.info("Login Request for user: {} ", loginRequest.getUsername());
		try {
			APIResponse response = new APIResponse();
			ResponseEntity<String> WLResponse;

			if(loginRequest.getUsername() == null || loginRequest.getUsername().equals("")
					|| loginRequest.getPassword() == null || loginRequest.getPassword().equals(""))
			{
				String userNAME=null;
				request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
						.getRequest();
				Cookie[] cookies = request.getCookies();
				if(cookies !=null){
					for(Cookie cookie : cookies){
						System.out.println("cookie= " + cookie);
						System.out.println("cookie getName=" + cookie.getName());
						System.out.println("cookie getValue=" + cookie.getValue());
						System.out.println("cookie JSESSIONID check=" + cookie.getName().equals("JSESSIONID"));
						if(cookie.getName().equals("JSESSIONID")){
							J_S_ID=cookie.getValue();
							System.out.println("JSESSIONID in signin= "+ J_S_ID);
							break;
						}
					}
					MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
					headers.set("Host",request.getServerName()+ ":" +request.getServerPort());
					headers.set("Cookie","JSESSIONID="+ J_S_ID);
					headers.set("Content-Type", "application/json");
					userNAME=authServiceImpl.retUserName();
					System.out.println("userNAME= " + userNAME);
				}
				if(userNAME == null)
				{
					return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
							"Invalid username or password","Unauthorized cookies"));
				}

				return  ResponseEntity.ok(new Response(HttpStatus.OK.value(),
						"user is logged in",authServiceImpl.createOne(userNAME)));
			}
			else
			{
				if(wlsAuth.isUserLocked(loginRequest.getUsername())==true) {
					return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
							"User is locked","Unauthorized"));
				}
				else {
					wlsAuth.postConstruct(service.getWLSConfig());
					String WL_IP=dao.getWLSConfig().getMAINSCREEN_IP();
					RestTemplate restTemplate=new RestTemplate();
					String decryptedPassword= GlobalService.decrypt(loginRequest.getPassword());
					loginRequest.setPassword(decryptedPassword);
					String WLURL= WL_IP+ refererName +"/j_security_check?j_username=" + loginRequest.getUsername() + "&j_password=" + loginRequest.getPassword().trim();
					HttpEntity<String> s = null;
					WLResponse=restTemplate.postForEntity(WLURL,s, String.class);
					if(WLResponse.getStatusCodeValue()==303) {



						System.out.println("GWA EL IF, REDIRECT= " + WLResponse.getStatusCode());
						String jsessionid = WLResponse.getHeaders().getFirst("Set-Cookie");
						jsessionid = jsessionid.split(";")[0].split("=")[1];
						System.out.println("jsessionid="+jsessionid);
						Cookie jsessionidCookie = new Cookie("JSESSIONID", jsessionid);
						jsessionidCookie.setPath("/");
						httpResponse.addCookie(jsessionidCookie);

						return  ResponseEntity.ok(new Response(HttpStatus.OK.value(),
								"user is logged in",authServiceImpl.createOne(loginRequest.getUsername())));
					}
					else {
						return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
								"Missing username or password","Unauthorized"));
					}
				}
			}
		}
		catch(Exception e)
		{
			if(wlsAuth.isUserLocked(loginRequest.getUsername())) {
				return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
						"User is locked","Unauthorized"));
			}
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
					"Missing username or password","Unauthorized"));

		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@GetMapping("logout")
	public ResponseEntity<?> Logout()
	{
		APIResponse response = new APIResponse();
		request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
				.getRequest();
		System.out.println("JSESSIONID in Logout=  "+request.getRequestedSessionId());
		System.out.println("JSESSIONID Valid in Logout=  "+request.isRequestedSessionIdValid());
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("authorities");
		request.getSession(false);
		request.getSession().invalidate();
		System.out.println("JSESSIONID Valid after Logout=  "+request.isRequestedSessionIdValid());
		Cookie JSESSIONIDcookie = new Cookie("JSESSIONID", null);
		JSESSIONIDcookie.setMaxAge(0);
		JSESSIONIDcookie.setPath("/");
		httpResponse.addCookie(JSESSIONIDcookie);

		System.out.println("Cookies Deleted");

		return  ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Logged out Successfully","success"));
	}

	@PostMapping("setParam")
	ResponseEntity<?> setSessionParam(@RequestBody LoginRequest loginRequest){
		request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
				.getRequest();
		System.out.println("JSESSIONID in Session=  "+request.getRequestedSessionId());
		System.out.println("JSESSIONID Valid in Session=  "+request.isRequestedSessionIdValid());
		APIResponse response = new APIResponse();

		try {
			if(loginRequest.getUsername() == null || loginRequest.getUsername() == "") {
				String userNAME=null;
				Cookie[] cookies = request.getCookies();
				if(cookies !=null){
					for(Cookie cookie : cookies){
						System.out.println("cookie= " + cookie);
						System.out.println("cookie getName=" + cookie.getName());
						System.out.println("cookie getValue=" + cookie.getValue());
						System.out.println("cookie JSESSIONID check=" + cookie.getName().equals("JSESSIONID"));
						if(cookie.getName().equals("JSESSIONID")){
							J_S_ID=cookie.getValue();
							System.out.println("JSESSIONID in Session= "+ J_S_ID);
							break;
						}
					}
					MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
					headers.set("Host",request.getServerName()+ ":" +request.getServerPort());
					headers.set("Cookie","JSESSIONID="+ J_S_ID);
					headers.set("Content-Type", "application/json");
					userNAME=authServiceImpl.retUserName();
					System.out.println("userNAME= " + userNAME);
					if(userNAME==null || userNAME.equals(null))
					{
						return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
								"Missing username or password","Unauthorized"));
					}
					loginRequest.setUsername(userNAME);
				}
			}

			String decryptedPassword= GlobalService.decrypt(loginRequest.getPassword());
			loginRequest.setPassword(decryptedPassword);
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword().trim()));

			UserDetails user = (UserDetails) authentication.getPrincipal();

			String username=user.getUsername();
			request.getSession().setAttribute("userName", username);

			request.getSession().setAttribute("authorities", user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);

			return  ResponseEntity.ok(new Response(HttpStatus.OK.value(),
					username,"Success"));
		}
		catch(Exception e)
		{
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
					"Missing username or password","Unauthorized"));
		}
	}

	@PostMapping("/changePassword")
	ResponseEntity<?> changePassword(@RequestBody CHG_Password_Request chg_Password_Request){
		APIResponse response = new APIResponse();
		try {
			String decOldPassword=GlobalService.decrypt(chg_Password_Request.getOldPassword());
			String decNewPassword=GlobalService.decrypt(chg_Password_Request.getNewPassword());

			chg_Password_Request.setOldPassword(decOldPassword);
			chg_Password_Request.setNewPassword(decNewPassword);
			request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
					.getRequest();
			String username=request.getSession().getAttribute("userName").toString();
			System.out.println("username= " + username);

			if(username==null || username=="") {
				return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
						"Missing username or password","Unauthorized"));
			}
			else if(wlsAuth.changeUserPassword(username, chg_Password_Request.getOldPassword().trim(), chg_Password_Request.getNewPassword().trim())) {
				return  ResponseEntity.ok(new Response(HttpStatus.OK.value(),
						"password changed","success"));
			}
			else {
				return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
						"wrong password","Unauthorized"));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(HttpStatus.UNAUTHORIZED.value(),
					"Missing username or password","Unauthorized"));
		}
	}

}