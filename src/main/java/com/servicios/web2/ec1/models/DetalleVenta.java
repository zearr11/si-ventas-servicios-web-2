package com.servicios.web2.ec1.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Getter
@Setter
@Builder
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleVenta;

    private Integer cantidad;
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "fk_id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "fk_id_venta")
    private Venta venta;

}
