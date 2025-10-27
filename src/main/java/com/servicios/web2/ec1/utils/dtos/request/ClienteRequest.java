package com.servicios.web2.ec1.utils.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClienteRequest {

    private String nombres;
    private String apellidos;
    private String email;
    private String direccion;
    private String telefono;

}
