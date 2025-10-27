package com.servicios.web2.ec1.controllers;

import com.servicios.web2.ec1.services.interfaces.IClienteService;
import com.servicios.web2.ec1.utils.dtos.request.ClienteRequest;
import com.servicios.web2.ec1.utils.dtos.response.ClienteResponse;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${app.prefix}/clientes")
public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // http://localhost:8080/api/v1/clientes/?
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerClientePorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteService.obtenerClientePorId(id));
    }

    // http://localhost:8080/api/v1/clientes
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> obtenerClientes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteService.listarClientes());
    }

    // http://localhost:8080/api/v1/clientes
    @PostMapping
    public ResponseEntity<MensajeResponse> crearCliente(@RequestBody ClienteRequest cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.crearCliente(cliente));
    }

    // http://localhost:8080/api/v1/clientes/?
    @PutMapping("/{id}")
    public ResponseEntity<MensajeResponse> actualizarCliente(@PathVariable Long id,
                                                             @RequestBody ClienteRequest cliente) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteService.modificarCliente(id, cliente));
    }

    // http://localhost:8080/api/v1/clientes/?
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarCliente(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteService.eliminarClientePorId(id));
    }

}
