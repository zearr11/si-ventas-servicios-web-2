package com.servicios.web2.ec1.services.impl.auth;

import com.servicios.web2.ec1.models.Usuario;
import com.servicios.web2.ec1.services.interfaces.auth.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService implements IJwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private static final int ACCESS_TOKEN_DURATION = 86400000; // 1 DIA

    @Override
    public String generateAccessToken(Usuario usuario) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(usuario.getUsername())
                .claim("role", "ROLE_" + usuario.getRol().name())
                .claim("token_type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + ACCESS_TOKEN_DURATION
                ))
                .compact();
    }

    @Override
    public String extractSubject(String token) {
        return extractAllClaims(token)
                .getSubject(); // username
    }

    @Override
    public boolean isTokenValid(String token, Usuario usuario) {
        String username = extractSubject(token);
        return (username.equals(usuario.getUsername())
                && !isTokenExpired(token));
        // Si el username coincide y el token no ha expirado
    }

    @Override
    public boolean isAccessToken(String token) {
        return "access".equals(extractAllClaims(token)
                .get("token_type", String.class));
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        // Retorna un objeto Claims que contiene la información del token
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
        // Devuelve true si el token ha expirado
    }
}
