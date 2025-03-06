package com.secondmarkethand.seguridad;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

	private final UserDetailsService userDetailsService;

	public SeguridadConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(List.of(authProvider));
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/webjars/**", "/css/**", "/h2-console/**", "/public/**", "/auth/**",
								"/files/**")
						.permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/auth/login")
						.defaultSuccessUrl("/public/index", true)
						.loginProcessingUrl("/auth/login-post")
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/auth/logout")
						.logoutSuccessUrl("/public/index"))
				.csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si es necesario
				.headers(headers -> headers.frameOptions(frame -> frame.disable())); // Permitir H2-Console

		return http.build();
	}
}
