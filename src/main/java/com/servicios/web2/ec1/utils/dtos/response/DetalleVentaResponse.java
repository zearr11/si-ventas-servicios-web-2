package com.servicios.web2.ec1.utils.dtos.response;

import com.servicios.web2.ec1.models.DetalleVenta;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DetalleVentaResponse {

    private String item;
    private Double precioUnitario;
    private Integer cantidad;
    private Double importe;

    public static DetalleVentaResponse DetalleVentaToDetalleVentaResponse(DetalleVenta detalleVentaToConvert) {
        return DetalleVentaResponse.builder()
                .item(
                        detalleVentaToConvert.getProducto().getNombre() + " - " +
                        detalleVentaToConvert.getProducto().getDescripcion()
                )
                .precioUnitario(detalleVentaToConvert.getProducto().getPrecio())
                .cantidad(detalleVentaToConvert.getCantidad())
                .importe(detalleVentaToConvert.getSubtotal())
                .build();
    }

}
