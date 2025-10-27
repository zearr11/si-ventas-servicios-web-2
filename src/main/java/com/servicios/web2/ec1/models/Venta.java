package com.servicios.web2.ec1.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Getter
@Setter
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    private LocalDateTime fecha;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "fk_id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;

}
