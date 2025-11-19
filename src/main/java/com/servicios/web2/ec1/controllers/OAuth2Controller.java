package com.servicios.web2.ec1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2")
public class OAuth2Controller {

    @GetMapping("/oauth2-prueba")
    public ResponseEntity<Map<String, String>> pruebaOAuth() {
        return ResponseEntity.ok(
                Map.of("mensaje", "Prueba de endpoint con OAuth2 OK.")
        );
    }

}
