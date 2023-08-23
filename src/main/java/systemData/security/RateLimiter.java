package systemData.security;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RateLimiter {

    private final Map<String, Long> requestCounts = new ConcurrentHashMap<>();
    
    @Value("${numOfReq}")
    private int numOfReq;
    
    @Value("${timeForReq}")
    private long timeForReq;

    public boolean isRateLimited(String key) {
        Long currentCount = requestCounts.get(key);
        if (currentCount == null) {
            requestCounts.put(key, 1L);
        } else {
            requestCounts.put(key, currentCount + 1);
        }

        return currentCount != null && currentCount >= numOfReq;
    }

    @Scheduled(fixedDelayString = "${timeForReq}")
    public void clearRequestCounts() {
    	
    	System.out.println("requestCounts="+requestCounts + " at " + LocalDateTime.now());
        requestCounts.clear();
    }
}

