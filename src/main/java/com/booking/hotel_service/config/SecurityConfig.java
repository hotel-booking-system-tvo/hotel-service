package com.booking.hotel_service.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) 
public class SecurityConfig {
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
		 http
		    .csrf(csrf -> csrf.disable())
		    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .authorizeHttpRequests(auth -> auth
		        .requestMatchers(
		            "/v3/api-docs/**",      // OpenAPI JSON
		            "/swagger-ui/**",       // Swagger UI static resources
		            "/swagger-ui.html"      // Swagger main page
		        ).permitAll()
		        .anyRequest().authenticated()
		    )
		    .oauth2ResourceServer(oauth2 -> oauth2
		        .jwt(jwt -> jwt
		            .decoder(jwtDecoder)
		            .jwtAuthenticationConverter(jwtAuthenticationConverter())
		        )
		    );
	        return http.build();
	    }

	    @Bean
	    public JwtDecoder jwtDecoder(@Value("${apps.security.secret}") String secret) {
	        SecretKeySpec key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
	        return NimbusJwtDecoder.withSecretKey(key).build();
	    }
	    
	    @Bean
	    public JwtAuthenticationConverter jwtAuthenticationConverter() {
	        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
	        converter.setAuthoritiesClaimName("roles");
	        converter.setAuthorityPrefix("ROLE_");

	        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
	        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
	        return jwtConverter;
	    }
}
