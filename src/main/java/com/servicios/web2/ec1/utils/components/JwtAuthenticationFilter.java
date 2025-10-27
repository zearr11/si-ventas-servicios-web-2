package com.servicios.web2.ec1.utils.components;

import com.servicios.web2.ec1.models.Usuario;
import com.servicios.web2.ec1.repositories.UsuarioRepository;
import com.servicios.web2.ec1.services.interfaces.auth.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final UsuarioRepository userRepository;

    public JwtAuthenticationFilter(IJwtService jwtService,
                                   UsuarioRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String jwt;
        String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        username = jwtService.extractSubject(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Usuario usuario = userRepository.findByUsername(username)
                    .orElse(null);

            if (usuario != null
                    && jwtService.isTokenValid(jwt, usuario)
                    && jwtService.isAccessToken(jwt)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                usuario, null,
                                List.of(
                                        new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
                                )
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
