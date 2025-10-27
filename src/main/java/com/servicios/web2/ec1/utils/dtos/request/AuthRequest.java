package com.servicios.web2.ec1.utils.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthRequest {

    private String username;
    private String password;

}
