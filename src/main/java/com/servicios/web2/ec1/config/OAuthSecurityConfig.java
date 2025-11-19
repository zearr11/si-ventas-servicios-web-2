package com.servicios.web2.ec1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuthSecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain oauth2ResourceServerSecurity(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        http.securityMatcher("/api/oauth2/**")
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.decoder(jwtDecoder))
                );
        return http.build();
    }

}
