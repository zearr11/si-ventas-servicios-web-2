package com.servicios.web2.ec1.utils.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DetalleVentaRequest {

    private Long idProducto;
    private Integer cantidad;

}
