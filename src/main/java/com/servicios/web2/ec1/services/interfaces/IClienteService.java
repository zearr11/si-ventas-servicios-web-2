package com.servicios.web2.ec1.services.interfaces;

import com.servicios.web2.ec1.utils.dtos.request.ClienteRequest;
import com.servicios.web2.ec1.utils.dtos.response.ClienteResponse;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import java.util.List;

public interface IClienteService {

    ClienteResponse obtenerClientePorId(Long id);
    List<ClienteResponse> listarClientes();
    MensajeResponse crearCliente(ClienteRequest cliente);
    MensajeResponse modificarCliente(Long id, ClienteRequest cliente);
    MensajeResponse eliminarClientePorId(Long id);

}
