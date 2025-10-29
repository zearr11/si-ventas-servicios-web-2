package com.servicios.web2.ec1.services.impl.auth;

import com.servicios.web2.ec1.models.CustomUserDetails;
import com.servicios.web2.ec1.models.Usuario;
import com.servicios.web2.ec1.repositories.UsuarioRepository;
import com.servicios.web2.ec1.services.impl.CustomUserDetailsService;
import com.servicios.web2.ec1.services.interfaces.auth.IAuthService;
import com.servicios.web2.ec1.services.interfaces.auth.IJwtService;
import com.servicios.web2.ec1.utils.dtos.request.AuthRequest;
import com.servicios.web2.ec1.utils.dtos.response.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private static final String CREDENTIALS_ERROR = "Credenciales inválidas.";

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final IJwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       CustomUserDetailsService userDetailsService,
                       IJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        return new AuthResponse(jwtService.generateAccessToken(userDetails));
    }

    @Override
    public Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return user.getUsuario();
    }

    /*
    Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        return (Usuario) authentication.getPrincipal();
    */

}
