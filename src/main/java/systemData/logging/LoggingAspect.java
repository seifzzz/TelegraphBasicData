package systemData.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    @Autowired
    private ObjectMapper mapper;

    @Before("execution(* systemData.*.*.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        logger.info("Before Aspect - {} is called with arguments: {}"
                ,  joinPoint, joinPoint.getArgs());
    }


    @After("execution(* systemData.*.*.*.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        logger.info("After Aspect - {} is called with arguments: {}"
                ,joinPoint, joinPoint.getArgs());
    }

    @Around("execution(* systemData.Controller.*.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Entering method: {}", methodName);

        long startTime = System.currentTimeMillis();

        // Proceed with the actual method execution
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("Error in method: {}", methodName, throwable);
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("Exiting method: {} - Execution time: {} milliseconds", methodName, (endTime - startTime));
        }

        return result;
    }
}



