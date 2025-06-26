package com.example.sistema_parqueo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "zona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private Integer totalEspacios;
} 