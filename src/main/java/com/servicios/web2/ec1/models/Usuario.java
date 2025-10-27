package com.servicios.web2.ec1.models;

import com.servicios.web2.ec1.utils.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Getter
@Setter
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, unique = true) // nuevo
    private String username;

    @Column(nullable = false) // nuevo
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_persona")
    private Persona persona;

}
