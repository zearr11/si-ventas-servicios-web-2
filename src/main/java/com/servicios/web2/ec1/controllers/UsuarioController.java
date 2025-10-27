package com.servicios.web2.ec1.controllers;

import com.servicios.web2.ec1.services.interfaces.IUsuarioService;
import com.servicios.web2.ec1.utils.dtos.request.UsuarioRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${app.prefix}/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // http://localhost:8080/api/v1/usuarios/?
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.obtenerUsuarioPorId(id));
    }

    // http://localhost:8080/api/v1/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> obtenerUsuarios() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.listarUsuarios());
    }

    // http://localhost:8080/api/v1/usuarios
    @PostMapping
    public ResponseEntity<MensajeResponse> crearUsuario(@RequestBody UsuarioRequest usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(usuario));
    }

    // http://localhost:8080/api/v1/usuarios/?
    @PutMapping("/{id}")
    public ResponseEntity<MensajeResponse> actualizarUsuario(@PathVariable Long id,
                                                             @RequestBody UsuarioRequest usuario) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.modificarUsuario(id, usuario));
    }

    // http://localhost:8080/api/v1/usuarios/?
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.eliminarUsuarioPorId(id));
    }

}
