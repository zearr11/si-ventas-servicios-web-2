package com.servicios.web2.ec1.services.interfaces;

import com.servicios.web2.ec1.utils.dtos.request.UsuarioRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.UsuarioResponse;
import java.util.List;

public interface IUsuarioService {

    UsuarioResponse obtenerUsuarioPorId(Long id);
    List<UsuarioResponse> listarUsuarios();
    MensajeResponse crearUsuario(UsuarioRequest usuario);
    MensajeResponse modificarUsuario(Long id, UsuarioRequest usuario);
    MensajeResponse eliminarUsuarioPorId(Long id);

}
