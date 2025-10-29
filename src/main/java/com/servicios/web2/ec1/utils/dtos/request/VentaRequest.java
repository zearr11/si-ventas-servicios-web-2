package com.servicios.web2.ec1.utils.dtos.request;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VentaRequest {

    private Long idCliente;
    private List<DetalleVentaRequest> detalleVenta;

}
