package com.servicios.web2.ec1.config;

import com.servicios.web2.ec1.services.impl.CustomUserDetailsService;
import com.servicios.web2.ec1.utils.components.JwtAuthenticationFilter;
import com.servicios.web2.ec1.utils.enums.Rol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;
    private final CustomUserDetailsService userDetailsService;

    @Value("${app.prefix}")
    private String appPrefix;

    public SecurityConfig(JwtAuthenticationFilter authenticationFilter,
                          CustomUserDetailsService userDetailsService) {
        this.authenticationFilter = authenticationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Acceso JWT
                        .requestMatchers(appPrefix + "/auth").permitAll()

                        // Categorías
                        .requestMatchers(HttpMethod.GET, appPrefix + "/categorias/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.VENDEDOR.name(), Rol.ALMACENERO.name())
                        .requestMatchers(HttpMethod.POST, appPrefix + "/categorias/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.ALMACENERO.name())
                        .requestMatchers(HttpMethod.PUT, appPrefix + "/categorias/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.ALMACENERO.name())
                        .requestMatchers(HttpMethod.DELETE, appPrefix + "/categorias/**").hasRole(Rol.ADMIN.name())

                        // Clientes
                        .requestMatchers(HttpMethod.GET, appPrefix + "/clientes/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.VENDEDOR.name())
                        .requestMatchers(HttpMethod.POST, appPrefix + "/clientes/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.VENDEDOR.name())
                        .requestMatchers(HttpMethod.PUT, appPrefix + "/clientes/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.VENDEDOR.name())
                        .requestMatchers(HttpMethod.DELETE, appPrefix + "/clientes/**").hasRole(Rol.ADMIN.name())

                        // Productos
                        .requestMatchers(HttpMethod.GET, appPrefix + "/productos/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.VENDEDOR.name(), Rol.ALMACENERO.name())
                        .requestMatchers(HttpMethod.POST, appPrefix + "/productos/**").hasAnyRole(Rol.ADMIN.name(), Rol.ALMACENERO.name())
                        .requestMatchers(HttpMethod.PUT, appPrefix + "/productos/**").hasAnyRole(Rol.ADMIN.name(), Rol.ALMACENERO.name())
                        .requestMatchers(HttpMethod.DELETE, appPrefix + "/productos/**").hasRole(Rol.ADMIN.name())

                        // Usuarios
                        .requestMatchers(appPrefix + "/usuarios/**").hasRole(Rol.ADMIN.name())

                        // Ventas
                        .requestMatchers(HttpMethod.GET, appPrefix + "/ventas/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.VENDEDOR.name(), Rol.ALMACENERO.name())
                        .requestMatchers(HttpMethod.POST, appPrefix + "/ventas/**").hasAnyRole(Rol.ADMIN.name(), Rol.SUPERVISOR.name(), Rol.VENDEDOR.name())

                        // Cualquier otro requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth").permitAll()
                        .requestMatchers("/api/v1/ventas").hasRole(Rol.CAJERO.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/productos")
                        .hasRole(Rol.REPONEDOR.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/productos")
                        .hasRole(Rol.REPONEDOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/productos")
                        .hasRole(Rol.REPONEDOR.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
    */

}
