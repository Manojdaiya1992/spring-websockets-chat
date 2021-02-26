package com.manoj.ChatAppBackend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
    private CustomUserDetails userDetailsService;
    
    public WebSecurity(CustomUserDetails userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/signin","/ws/**").permitAll()
                .antMatchers(HttpMethod.POST,"/user/register/account").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .headers()
                // allow same origin to frame our site to support iframe SockJS
                .frameOptions().sameOrigin().and()
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				//System.out.println(rawPassword);
				return true;
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				//System.out.println("Raw Passowrd "+rawPassword);
				return rawPassword.toString();
			}
		});
    }
    
    private static final String[] AUTH_WHITELIST = {
			"/*.html",
			"/profile/**",
			"/favicon.ico",
			"/**.png",
    		"/**/*.html",
    		"/**/*.jsp",
    		"/**/*.css",
    		"/**/*.js",
    		 "/**/*.png",
             "/**/*.jpg",
             "/*.jpg",
             "/*.png",
             "/**/*.png",
             "/**/*.jpeg",
             "/**/*.JPG",
             "/**/*.pdf",
             "/**/*.mp4",
             "/**/*.3gp",
             "/**/*.wmv",
             "/**/*.flv",
             "/**/*.avi",
             "/**/*.mov",
			"/swagger-ui/index.html",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**"
    };
	
	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
		web.ignoring().antMatchers(AUTH_WHITELIST);
	}

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}