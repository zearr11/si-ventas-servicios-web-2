package com.servicios.web2.ec1.services.impl;

import com.servicios.web2.ec1.models.Categoria;
import com.servicios.web2.ec1.repositories.CategoriaRepository;
import com.servicios.web2.ec1.repositories.ProductoRepository;
import com.servicios.web2.ec1.services.interfaces.ICategoriaService;
import com.servicios.web2.ec1.utils.dtos.request.CategoriaRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository,
                            ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public MensajeResponse crearCategoria(CategoriaRequest categoria) {

        Categoria newCategoria = Categoria.builder()
                .descripcion(categoria.getCategoria())
                .build();

        categoriaRepository.save(newCategoria);

        return new MensajeResponse("Categoria creada satisfactoriamente.");
    }

    @Override
    public MensajeResponse modificarCategoria(Long id, CategoriaRequest categoria) {
        Optional<Categoria> categoriaToEdit = categoriaRepository.findById(id);

        if (categoriaToEdit.isEmpty()) {
            return new MensajeResponse("Categoria con id " + id + " no existe.");
        }

        categoriaToEdit.get().setDescripcion(categoria.getCategoria());

        return new MensajeResponse("Categoria modificada satisfactoriamente.");
    }

    @Override
    public MensajeResponse eliminarCategoriaPorId(Long id) {
        Optional<Categoria> categoriaToDelete = categoriaRepository.findById(id);

        if (categoriaToDelete.isEmpty())
            return new MensajeResponse("Categoria con id " + id + " no existe.");

        if (!productoRepository.findByCategoria(categoriaToDelete.get()).isEmpty())
            return new MensajeResponse("Categoria con id " + id + " tiene productos asociados. No puede eliminarse.");

        categoriaRepository.delete(categoriaToDelete.get());

        return new MensajeResponse("Categoria eliminada satisfactoriamente.");
    }
}
