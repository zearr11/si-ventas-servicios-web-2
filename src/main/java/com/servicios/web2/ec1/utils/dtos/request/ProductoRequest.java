package com.servicios.web2.ec1.utils.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductoRequest {

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Long idCategoria;

}
