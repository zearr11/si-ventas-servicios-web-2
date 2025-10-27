package com.servicios.web2.ec1.utils.dtos.response;

import com.servicios.web2.ec1.models.Producto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductoResponse {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Double precio;
    private Integer stock;

    public static ProductoResponse ProductoToProductoResponse(Producto productoToConvert) {
        return ProductoResponse.builder()
                .idProducto(productoToConvert.getIdProducto())
                .nombre(productoToConvert.getNombre())
                .descripcion(productoToConvert.getDescripcion())
                .categoria(productoToConvert.getCategoria().getDescripcion())
                .precio(productoToConvert.getPrecio())
                .stock(productoToConvert.getStock())
                .build();
    }

}
