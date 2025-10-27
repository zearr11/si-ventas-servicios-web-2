package com.servicios.web2.ec1.services.interfaces;

import com.servicios.web2.ec1.utils.dtos.request.ProductoRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.ProductoResponse;
import java.util.List;

public interface IProductoService {

    ProductoResponse obtenerProductoPorId(Long id);
    List<ProductoResponse> listarProductos();
    MensajeResponse crearProducto(ProductoRequest producto);
    MensajeResponse modificarProducto(Long id, ProductoRequest producto);
    MensajeResponse eliminarProductoPorId(Long id);

}
