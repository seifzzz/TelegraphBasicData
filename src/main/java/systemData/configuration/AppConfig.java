package systemData.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import systemData.logging.LoggingAspect;
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "systemData.logging")
public class AppConfig {
    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

}
