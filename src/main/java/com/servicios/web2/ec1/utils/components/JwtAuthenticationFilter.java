package com.servicios.web2.ec1.utils.components;

import com.servicios.web2.ec1.services.impl.CustomUserDetailsService;
import com.servicios.web2.ec1.services.interfaces.auth.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(IJwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/oauth2/")
                || path.startsWith("/connect/")
                || path.startsWith("/.well-known/")
                || path.startsWith("/oauth/")
                || path.startsWith("/oauth2-prueba");
    }

    private String getJwtAlg(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length < 2) return null;
            String headerJson = new String(java.util.Base64.getUrlDecoder().decode(parts[0]));
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            java.util.Map<String, Object> map = mapper.readValue(headerJson, java.util.Map.class);
            return map.getOrDefault("alg", "").toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        // 1) detectar algoritmo sin validar firma
        String alg = getJwtAlg(jwt);
        // DEBUG: opcional, loggea el alg
        System.out.println("JwtAuthenticationFilter: token alg:");
        logger.debug(alg);

        // Si no es HS256 (o el algoritmo que usas para tus JWT internos), no intentar validarlo aquí
        if (alg == null || !alg.equalsIgnoreCase("HS256")) {
            // dejamos que Spring Resource Server u otro validador se encargue
            filterChain.doFilter(request, response);
            return;
        }

        // Si es HS256, validamos con tu JwtService como antes (pero con manejo de excepciones)
        try {
            String username = jwtService.extractSubject(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails usuario = userDetailsService.loadUserByUsername(username);

                if (usuario != null && jwtService.isTokenValid(jwt, usuario) && jwtService.isAccessToken(jwt)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            // No es el formato esperado; lo ignoramos (dejamos que otro validador lo maneje)
            System.out.println("JwtAuthenticationFilter: token no compatible, ignorando:");
            logger.debug(e.getMessage());
        } catch (Exception e) {
            // Si hay otro problema, no rompas el chain; elimina la autenticación y sigue
            System.out.println("JwtAuthenticationFilter: error validando token HS256:");
            logger.warn(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}

