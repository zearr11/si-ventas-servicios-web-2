package com.servicios.web2.ec1.services.impl;

import com.servicios.web2.ec1.models.CustomUserDetails;
import com.servicios.web2.ec1.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository userRepo;

    public CustomUserDetailsService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Usuario no encontrado"
                        )
                );
    }

}
