package net.kem.mimecast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest()
				.permitAll()
				.and()
				.csrf()
				.disable()
				;
		http.headers().frameOptions().disable(); // used to enable frames in h2 console at http://localhost:18081/h2-console/
		return http.build();
	}
}