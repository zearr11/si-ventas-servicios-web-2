package com.servicios.web2.ec1.controllers;

import com.servicios.web2.ec1.services.interfaces.IVentaService;
import com.servicios.web2.ec1.utils.dtos.request.VentaRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.VentaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${app.prefix}/ventas")
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    // http://localhost:8080/api/v1/ventas/?
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerVentasPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.obtenerVentaPorId(id));
    }

    // http://localhost:8080/api/v1/ventas
    @GetMapping
    public ResponseEntity<List<VentaResponse>> obtenerVentas() {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.listarVentas());
    }

    // http://localhost:8080/api/v1/ventas
    @PostMapping
    public ResponseEntity<MensajeResponse> crearVenta(@RequestBody VentaRequest venta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.crearVenta(venta));
    }

    // http://localhost:8080/api/v1/ventas/?
    @PutMapping("/{id}")
    public ResponseEntity<MensajeResponse> actualizarVenta(@PathVariable Long id,
                                                           @RequestBody VentaRequest venta) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.modificarVenta(id, venta));
    }

    // http://localhost:8080/api/v1/ventas/?
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarVenta(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.eliminarVentaPorId(id));
    }

}
