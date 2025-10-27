package com.servicios.web2.ec1.utils.dtos.response;

import com.servicios.web2.ec1.models.DetalleVenta;
import com.servicios.web2.ec1.models.Venta;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VentaResponse {

    private Long idVenta;
    private LocalDateTime fechaVenta;
    private ClienteResponse datosCliente;
    private UsuarioResponse datosUsuario;
    private List<DetalleVentaResponse> detalleVenta;
    private Double total;

    public static VentaResponse VentaDetalleVentaToVentaResponse(Venta ventaToConvert,
                                                                 List<DetalleVenta> detalleVentaToConvert) {
        return VentaResponse.builder()
                .idVenta(ventaToConvert.getIdVenta())
                .datosCliente(ClienteResponse.ClienteToClienteResponse(
                        ventaToConvert.getCliente()
                ))
                .datosUsuario(UsuarioResponse.UsuarioToUsuarioResponse(
                        ventaToConvert.getUsuario()
                ))
                .fechaVenta(ventaToConvert.getFecha())
                .detalleVenta(
                        detalleVentaToConvert.stream().map(
                        DetalleVentaResponse::DetalleVentaToDetalleVentaResponse)
                        .toList()
                )
                .total(ventaToConvert.getTotal())
                .build();
    }

}
