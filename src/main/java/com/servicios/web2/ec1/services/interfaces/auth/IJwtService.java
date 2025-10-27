package com.servicios.web2.ec1.services.interfaces.auth;

import com.servicios.web2.ec1.models.Usuario;
import io.jsonwebtoken.Claims;

public interface IJwtService {

    String generateAccessToken(Usuario usuario);
    String extractSubject(String token);
    boolean isTokenValid(String token, Usuario usuario);
    boolean isAccessToken(String token);
    Claims extractAllClaims(String token);
    boolean isTokenExpired(String token);

}
