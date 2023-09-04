package systemData.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws  IOException {

		System.out.println("JSESSIONID =  "+request.getRequestedSessionId());	
		System.out.println("JSESSIONID Validation=  "+request.isRequestedSessionIdValid());	
		
		try {
		    
			String uri=request.getRequestURI();
			System.out.println("URI= " + uri);
			System.out.println("refererName= " + refererName);
			String requestReferer=request.getHeader("Referer");
//			requestReferer = "/test";
			System.out.println("requestReferer= " + requestReferer);



			if(Objects.equals(requestReferer, "") || requestReferer==null || requestReferer.equals("null"))
				requestReferer = null;
			
			if(uri.endsWith(refererName+"/")) {
				System.out.println("sso apps passed filter chain"); 
				filterChain.doFilter(request, response);
			}
				
			else if(requestReferer==null && uri.contains(refererName)) {
				System.out.println("app passed filter chain"); 
				filterChain.doFilter(request, response);
			}
			else if(requestReferer!=null && requestReferer.contains(refererName) && (uri.contains("/v3/api-docs") ||uri.contains("/swagger-ui") ||  uri.contains("/api/auth") ||  uri.contains("/api2/auth") || uri.contains("/assets") || uri.contains(".js") || uri.contains(".html") || uri.contains(".css"))) {
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
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "You are not authorized");
		}
		
	}

}
