package com.servicios.web2.ec1.services.impl;

import com.servicios.web2.ec1.models.Persona;
import com.servicios.web2.ec1.models.Usuario;
import com.servicios.web2.ec1.repositories.PersonaRepository;
import com.servicios.web2.ec1.repositories.UsuarioRepository;
import com.servicios.web2.ec1.repositories.VentaRepository;
import com.servicios.web2.ec1.services.interfaces.IUsuarioService;
import com.servicios.web2.ec1.utils.dtos.request.UsuarioRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.UsuarioResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final VentaRepository ventaRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PersonaRepository personaRepository,
                          VentaRepository ventaRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.ventaRepository = ventaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponse obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioResponse::UsuarioToUsuarioResponse)
                .orElse(null);
    }

    @Override
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream().map(UsuarioResponse::UsuarioToUsuarioResponse)
                .toList();
    }

    @Transactional
    @Override
    public MensajeResponse crearUsuario(UsuarioRequest usuario) {
        Persona personaToSave = Persona.builder()
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .build();

        personaToSave = personaRepository.save(personaToSave);

        Usuario usuarioToSave = Usuario.builder()
                .username(usuario.getUsername())
                .password(passwordEncoder.encode(usuario.getPassword()))
                .persona(personaToSave)
                .rol(usuario.getRol())
                .build();

        usuarioRepository.save(usuarioToSave);

        return new MensajeResponse("Usuario creado satisfactoriamente.");
    }

    @Override
    public MensajeResponse modificarUsuario(Long id, UsuarioRequest usuario) {
        Optional<Usuario> usuarioToEdit = usuarioRepository.findById(id);

        if (usuarioToEdit.isEmpty()) {
            return new MensajeResponse("Usuario con id " + id + " no existe.");
        }

        usuarioToEdit.get().setUsername(usuario.getUsername());
        usuarioToEdit.get().setPassword(passwordEncoder.encode(usuario.getPassword()));

        usuarioToEdit.get().getPersona().setNombres(usuario.getNombres());
        usuarioToEdit.get().getPersona().setApellidos(usuario.getApellidos());
        usuarioToEdit.get().getPersona().setEmail(usuario.getEmail());
        usuarioToEdit.get().setRol(usuario.getRol());

        usuarioRepository.save(usuarioToEdit.get());

        return new MensajeResponse("Usuario modificado satisfactoriamente.");
    }

    @Override
    public MensajeResponse eliminarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioToDelete = usuarioRepository.findById(id);

        if (usuarioToDelete.isEmpty())
            return new MensajeResponse("Usuario con id " + id + " no existe.");

        if (!ventaRepository.findByUsuario(usuarioToDelete.get()).isEmpty())
            return new MensajeResponse("Usuario con id " + id + " tiene ventas asociadas. No puede eliminarse.");

        usuarioRepository.delete(usuarioToDelete.get());

        return new MensajeResponse("Usuario eliminado satisfactoriamente.");
    }

}
