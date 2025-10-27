package com.servicios.web2.ec1.utils.dtos.response;

import com.servicios.web2.ec1.models.Usuario;
import com.servicios.web2.ec1.utils.enums.Rol;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResponse {

    private Long idUsuario;
    private String nombres;
    private String apellidos;
    private String email;
    private String username;
    private Rol rol;

    public static UsuarioResponse UsuarioToUsuarioResponse(Usuario usuarioToConvert) {
        return UsuarioResponse.builder()
                .idUsuario(usuarioToConvert.getIdUsuario())
                .nombres(usuarioToConvert.getPersona().getNombres())
                .apellidos(usuarioToConvert.getPersona().getApellidos())
                .email(usuarioToConvert.getPersona().getEmail())
                .username(usuarioToConvert.getUsername())
                .rol(usuarioToConvert.getRol())
                .build();
    }

}
