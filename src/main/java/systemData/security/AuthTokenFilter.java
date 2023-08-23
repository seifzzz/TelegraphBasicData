package systemData.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


public class AuthTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Value("${referer.name}")
    private String refererName;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("JSESSIONID =  "+request.getRequestedSessionId());	
		System.out.println("JSESSIONID Validation=  "+request.isRequestedSessionIdValid());	
		
		try {
		    
			String uri=request.getRequestURI();
			System.out.println("URI= " + uri);
			System.out.println("refererName= " + refererName);
			String requestReferer = null;
			requestReferer=request.getHeader("Referer");
			System.out.println("requestReferer= " + requestReferer);

			if(requestReferer=="" || requestReferer==null || requestReferer.equals("null"))
				requestReferer = null;
			
			if(uri.endsWith(refererName+"/")) {
				System.out.println("sso apps passed filter chain"); 
				filterChain.doFilter(request, response);
			}
				
			else if(requestReferer==null && uri.contains(refererName)) {
				System.out.println("app passed filter chain"); 
				filterChain.doFilter(request, response);
			}
			else if(requestReferer!=null && requestReferer.contains(refererName) && (uri.contains("/api/auth") || uri.contains("/assets") || uri.contains(".js") || uri.contains(".html") || uri.contains(".css"))) {
				System.out.println("passed filter chain"); 
				filterChain.doFilter(request, response);
			}
			else if(requestReferer!=null && requestReferer.contains(refererName)) {
				String username=request.getSession().getAttribute("userName").toString();
				System.out.println("username= " + username);
				UserDetails user = userDetailsService.loadUserByUsername(username);
				System.out.println("user=  "+user);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						user, null, user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				filterChain.doFilter(request, response);

			}
			else
				response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid referer");

		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Cannot set user authentication: {}", e);
		}
		
	}

}
