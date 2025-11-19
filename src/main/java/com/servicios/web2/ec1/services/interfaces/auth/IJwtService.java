package com.servicios.web2.ec1.services.interfaces.auth;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String generateAccessToken(UserDetails usuario);
    String extractAlgFromHeader(String token);
    String extractSubject(String token);
    boolean isTokenValid(String token, UserDetails usuario);
    boolean isAccessToken(String token);
    Claims extractAllClaims(String token);
    boolean isTokenExpired(String token);

}
