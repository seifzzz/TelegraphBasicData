package systemData.security;

import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class RateLimitingInterceptor implements HandlerInterceptor{
    
	private final  RateLimiter rateLimiter;

    public RateLimitingInterceptor(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }
    
	public String getIPaddress(HttpServletRequest request)  throws SocketException {
		  String ip = HttpUtils.getRequestIP(request);
		  System.out.println("Client IP Address: " + ip);
		  return ip;
}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String ipAddress=getIPaddress(request);
        System.out.println("ipAddress="+ipAddress);
        if (rateLimiter.isRateLimited(ipAddress)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests.");
            return false;
        }
        return true;
    }
}
