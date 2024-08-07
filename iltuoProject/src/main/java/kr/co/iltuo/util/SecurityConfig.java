package kr.co.iltuo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.requestMatchers("/", "/menu/**", "/join/**", "/productList**", "/productDetail**", "/productDetail/**")
				.permitAll()
				.requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "https://fonts.googleapis.com/**", "https://netdna.bootstrapcdn.com/**")
				.permitAll().requestMatchers("/admin/**", "/admain", "/categoryManagement**", "/optionDetail**").hasRole("ADMIN")
				.requestMatchers("/myProfile**", "/updateUserInformation**", "/cart**", "/order**", "/orderOne**", "/paymentOne**", "/orderList**", "/orderDetail")
				.hasAnyRole("USER", "ADMIN")
				.anyRequest()
				.hasAnyRole("USER", "ADMIN"))
			.csrf(csrf -> csrf.disable())
			.formLogin(formLogin -> formLogin
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.usernameParameter("userID").passwordParameter("password")
				.successHandler(successHandler())
				.permitAll())
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll())
			.sessionManagement(sessionManagement -> sessionManagement
				.invalidSessionUrl("/login") 
				.maximumSessions(1)
				.expiredUrl("/"));
		return http.build();
	}
	
	@Bean
	AuthenticationSuccessHandler successHandler() {
		 return (request, response, authentication) -> {
			 RequestCache requestCache = new HttpSessionRequestCache();
			 SavedRequest savedRequest = requestCache.getRequest(request, response);
			 if (savedRequest != null) {
				 String targetUrl = savedRequest.getRedirectUrl();
				 log.info("targetUrl : " + targetUrl);
				 response.sendRedirect(targetUrl);
			 } else {
				 String refererUrl = (String) request.getSession().getAttribute("prevPage");
				 log.info("refererUrl : " + refererUrl);
				 if (refererUrl != null && !refererUrl.contains("/login")) {
				 	response.sendRedirect(refererUrl);
				 } else {
				 	response.sendRedirect("/");
				 }
			 }
			 log.info("savedRequest: " + savedRequest);
		 };
	}
	
	@Bean
    AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/login?error");
        };
    }
}
