package com.servicios.web2.ec1.services.impl;

import com.servicios.web2.ec1.models.Categoria;
import com.servicios.web2.ec1.models.Producto;
import com.servicios.web2.ec1.repositories.CategoriaRepository;
import com.servicios.web2.ec1.repositories.DetalleVentaRepository;
import com.servicios.web2.ec1.repositories.ProductoRepository;
import com.servicios.web2.ec1.services.interfaces.IProductoService;
import com.servicios.web2.ec1.utils.dtos.request.ProductoRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.ProductoResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository,
                           DetalleVentaRepository detalleVentaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    public ProductoResponse obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(ProductoResponse::ProductoToProductoResponse)
                .orElse(null);
    }

    @Override
    public List<ProductoResponse> listarProductos() {
        return productoRepository.findAll()
                .stream().map(ProductoResponse::ProductoToProductoResponse)
                .toList();
    }

    @Override
    public MensajeResponse crearProducto(ProductoRequest producto) {
        Optional<Categoria> categoria = categoriaRepository.findById(producto.getIdCategoria());

        if (categoria.isEmpty())
            return new MensajeResponse("Categoria con id " + producto.getIdCategoria() + " no existe.");

        Producto productoToSave = Producto.builder()
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .categoria(categoria.get())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .build();

        productoRepository.save(productoToSave);

        return new MensajeResponse("Producto creado satisfactoriamente.");
    }

    @Override
    public MensajeResponse modificarProducto(Long id, ProductoRequest producto) {
        Optional<Producto> productoToEdit = productoRepository.findById(id);
        Optional<Categoria> categoria = categoriaRepository.findById(producto.getIdCategoria());

        if (productoToEdit.isEmpty())
            return new MensajeResponse("Producto con id " + id + " no existe.");

        if (categoria.isEmpty())
            return new MensajeResponse("Categoria con id " + producto.getIdCategoria() + " no existe.");

        productoToEdit.get().setCategoria(categoria.get());
        productoToEdit.get().setStock(producto.getStock());
        productoToEdit.get().setPrecio(producto.getPrecio());
        productoToEdit.get().setNombre(producto.getNombre());
        productoToEdit.get().setDescripcion(producto.getDescripcion());

        productoRepository.save(productoToEdit.get());

        return new MensajeResponse("Producto modificado satisfactoriamente.");
    }

    @Override
    public MensajeResponse eliminarProductoPorId(Long id) {
        Optional<Producto> productoToDelete = productoRepository.findById(id);

        if (productoToDelete.isEmpty())
            return new MensajeResponse("Producto con id " + id + " no existe.");

        if (!detalleVentaRepository.findByProducto(productoToDelete.get()).isEmpty())
            return new MensajeResponse("Producto con id " + id + " tiene ventas asociadas. No puede eliminarse.");

        productoRepository.delete(productoToDelete.get());

        return new MensajeResponse("Producto eliminado satisfactoriamente.");
    }

}
