package com.servicios.web2.ec1.services.interfaces;

import com.servicios.web2.ec1.models.Categoria;
import com.servicios.web2.ec1.utils.dtos.request.CategoriaRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import java.util.List;

public interface ICategoriaService {

    Categoria obtenerCategoriaPorId(Long id);
    List<Categoria> listarCategorias();
    MensajeResponse crearCategoria(CategoriaRequest categoria);
    MensajeResponse modificarCategoria(Long id, CategoriaRequest categoria);
    MensajeResponse eliminarCategoriaPorId(Long id);

}
