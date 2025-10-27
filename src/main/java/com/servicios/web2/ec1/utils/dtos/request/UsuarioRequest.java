package com.servicios.web2.ec1.utils.dtos.request;

import com.servicios.web2.ec1.utils.enums.Rol;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRequest {

    private String username;
    private String password;

    private String nombres;
    private String apellidos;
    private String email;
    private Rol rol;

}
