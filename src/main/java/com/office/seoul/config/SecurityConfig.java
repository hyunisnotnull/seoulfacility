package com.office.seoul.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		log.info("passwordEncoder()");
		
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .anyRequest().permitAll() // 모든 요청을 허용
        )
        .csrf(csrf -> csrf.disable());
	    
	    http
    	.formLogin(login -> login
    			.loginPage("/member/member_login_form")
    			.loginProcessingUrl("/member/member_login_confirm")
				.usernameParameter("u_m_id")
				.passwordParameter("u_m_pw")
				.successHandler((request, response, authentication) -> {
					log.info("[MEMBER LOGIN SUCCESS]");
					
					RequestCache requestCache = new HttpSessionRequestCache();
					SavedRequest savedRequest = requestCache.getRequest(request, response);
					String targetURI = "/";
					if (savedRequest != null) {
						targetURI = savedRequest.getRedirectUrl();
						requestCache.removeRequest(request, response);
						
					}
					
					response.sendRedirect(targetURI);
					
				})
				.failureHandler((request, response, exception) -> {
					log.info("[MEMBER LOGIN FAIL]");
					log.info("Exception: {}", exception);
					
					response.sendRedirect("/member/member_login_form");
					
				}));

	    return http.build();
	}
}
