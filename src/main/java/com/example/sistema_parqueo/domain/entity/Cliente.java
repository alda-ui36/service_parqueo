package com.example.sistema_parqueo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombres;
    private String apePaterno;
    private String apeMaterno;
    @Column(length = 8, unique = true)
    private String dni;
    @Column(length = 9)
    private String telefono;
    private String direccion;
    private String correo;
    private Boolean estado;
}