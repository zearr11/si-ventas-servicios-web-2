package com.servicios.web2.ec1.controllers;

import com.servicios.web2.ec1.services.interfaces.auth.IAuthService;
import com.servicios.web2.ec1.utils.dtos.request.AuthRequest;
import com.servicios.web2.ec1.utils.dtos.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.prefix}/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    }

}
