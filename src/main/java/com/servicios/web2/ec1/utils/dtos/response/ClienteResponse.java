package com.servicios.web2.ec1.utils.dtos.response;

import com.servicios.web2.ec1.models.Cliente;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClienteResponse {

    private Long idCliente;
    private String nombres;
    private String apellidos;
    private String email;
    private String direccion;
    private String telefono;

    public static ClienteResponse ClienteToClienteResponse(Cliente clienteToConvert) {
        return ClienteResponse.builder()
                .idCliente(clienteToConvert.getIdCliente())
                .nombres(clienteToConvert.getPersona().getNombres())
                .apellidos(clienteToConvert.getPersona().getApellidos())
                .email(clienteToConvert.getPersona().getEmail())
                .direccion(clienteToConvert.getDireccion())
                .telefono(clienteToConvert.getTelefono())
                .build();
    }

}
