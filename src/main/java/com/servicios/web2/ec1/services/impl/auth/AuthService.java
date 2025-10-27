package com.servicios.web2.ec1.services.impl.auth;

import com.servicios.web2.ec1.models.Usuario;
import com.servicios.web2.ec1.repositories.UsuarioRepository;
import com.servicios.web2.ec1.services.interfaces.auth.IAuthService;
import com.servicios.web2.ec1.services.interfaces.auth.IJwtService;
import com.servicios.web2.ec1.utils.dtos.request.AuthRequest;
import com.servicios.web2.ec1.utils.dtos.response.AuthResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private static final String CREDENTIALS_ERROR = "Credenciales inválidas.";

    private final UsuarioRepository usuarioRepo;
    private final IJwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepo,
                       IJwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Usuario usuario = usuarioRepo
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException(CREDENTIALS_ERROR));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException(CREDENTIALS_ERROR);
        }

        return new AuthResponse(jwtService.generateAccessToken(usuario));
    }

    @Override
    public Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        return (Usuario) authentication.getPrincipal();
    }

}
