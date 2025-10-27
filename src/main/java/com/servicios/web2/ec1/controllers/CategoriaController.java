package com.servicios.web2.ec1.controllers;

import com.servicios.web2.ec1.models.Categoria;
import com.servicios.web2.ec1.services.interfaces.ICategoriaService;
import com.servicios.web2.ec1.utils.dtos.request.CategoriaRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${app.prefix}/categorias")
public class CategoriaController {

    private final ICategoriaService categoriaService;

    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // http://localhost:8080/api/v1/categorias/?
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.obtenerCategoriaPorId(id));
    }

    // http://localhost:8080/api/v1/categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.listarCategorias());
    }

    // http://localhost:8080/api/v1/categorias
    @PostMapping
    public ResponseEntity<MensajeResponse> crearCategoria(@RequestBody CategoriaRequest categoria) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.crearCategoria(categoria));
    }

    // http://localhost:8080/api/v1/categorias/?
    @PutMapping("/{id}")
    public ResponseEntity<MensajeResponse> actualizarCategoria(@PathVariable Long id,
                                                               @RequestBody CategoriaRequest categoria) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.modificarCategoria(id, categoria));
    }

    // http://localhost:8080/api/v1/categorias/?
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarCategoria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.eliminarCategoriaPorId(id));
    }

}
