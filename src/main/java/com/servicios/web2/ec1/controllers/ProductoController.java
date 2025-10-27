package com.servicios.web2.ec1.controllers;

import com.servicios.web2.ec1.services.interfaces.IProductoService;
import com.servicios.web2.ec1.utils.dtos.request.ProductoRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.ProductoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${app.prefix}/productos")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    // http://localhost:8080/api/v1/productos/?
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerProductoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productoService.obtenerProductoPorId(id));
    }

    // http://localhost:8080/api/v1/productos
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> obtenerProductos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productoService.listarProductos());
    }

    // http://localhost:8080/api/v1/productos
    @PostMapping
    public ResponseEntity<MensajeResponse> crearProducto(@RequestBody ProductoRequest producto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crearProducto(producto));
    }

    // http://localhost:8080/api/v1/productos/?
    @PutMapping("/{id}")
    public ResponseEntity<MensajeResponse> actualizarProducto(@PathVariable Long id,
                                                              @RequestBody ProductoRequest producto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productoService.modificarProducto(id, producto));
    }

    // http://localhost:8080/api/v1/productos/?
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarProducto(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productoService.eliminarProductoPorId(id));
    }

}
