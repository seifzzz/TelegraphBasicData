package systemData.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.Attributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.MappableAttributesRetriever;
import org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.j2ee.WebXmlMappableAttributesRetriever;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String[] AUTH_WHITELIST = {
	            "/api/auth/**",
	            "/swagger-ui/**",
	            "/v2/api-docs/**",
	            "/v3/api-docs/**",
			    "/v3/api-docs**",
			    "/v3/api-docs/swagger-config/**",
	            "/swagger-resources/**",
	            "/v2/api-docs",
	            "/swagger-resources/**",
	            "/swagger-ui.html/**",
	            "/webjars/**",
	            "/api/Mobile/**",
	            "/public/**",
	            "/login",
	            "/",
	            "*.js",
			     "*.json",
	            "*.css",
	            "*.ttf",
	            "/*.woff",
	            "/assets/**",
	            "/*.html",
	            "/tgh2",
	            "/j_security_check/**",
	            "/getUserName",
	            "/tgh2",
	            "/*.png",
	            "/*.ico",
	            "/*.svg",
	            "/*.xml",
	            "/*.eot",
	            "/setParam",
	            "/signin"
	            
	 };
	
	@Value("${allowed.origin}")
    private String allowedOrigin;
	
	private final systemData.security.AuthEntryPointJwt unauthorizedHandler;
	private final WLSAuthenticationProvider wlsAuthenticationProvider;

	  @Autowired
	  MappableAttributesRetriever webXmlRolesParser;

	  @Autowired
	  Attributes2GrantedAuthoritiesMapper roles2GrantedAuthoritiesMapper;

	  @SuppressWarnings("rawtypes")
	  @Autowired
	  AuthenticationUserDetailsService getUserDetailsService;

	  @Autowired
	  AuthenticationManager authenticationManager;

	  @Bean
	  public MappableAttributesRetriever webXmlRolesParser() {
	    return new WebXmlMappableAttributesRetriever();
	  }

	  @Bean
	  public Attributes2GrantedAuthoritiesMapper roles2GrantedAuthoritiesMapper() {
	    SimpleAttributes2GrantedAuthoritiesMapper var = new SimpleAttributes2GrantedAuthoritiesMapper();
	    var.setAttributePrefix("");
	    return var;
	  }

	    @Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
		
		@Override
		public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.authenticationProvider(wlsAuthenticationProvider);
		}
		
		@Bean
		public systemData.security.AuthTokenFilter authenticationJwtTokenFilter() {
			return new AuthTokenFilter();
		}
			  
	@SuppressWarnings("rawtypes")
	@Bean
	  public AuthenticationUserDetailsService getUserDetailsService() {
	    return new PreAuthorizationUserDetailsService() ;
	  }	

	  @Override
	  protected void configure(HttpSecurity http) throws Exception {

	    http
	    .cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//	        .and()
//	        .authorizeRequests()
//	        .antMatchers(AUTH_WHITELIST).permitAll()
//	        .anyRequest().authenticated();
	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	  } 

//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
//        .authorizeRequests().antMatchers("/**").permitAll();
//    }
	
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(allowedOrigin);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); 
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	 @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.addAllowedOrigin("*");
	        configuration.addAllowedHeader("*");
	        configuration.addAllowedMethod("*");
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

}
