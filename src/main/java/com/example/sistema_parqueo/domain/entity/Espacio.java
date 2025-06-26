package com.example.sistema_parqueo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "espacio", uniqueConstraints = @UniqueConstraint(columnNames = {"id_zona", "numero_espacio"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Espacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    @Column(name = "numero_espacio", length = 10, nullable = false)
    private String numeroEspacio;

    private Boolean ocupado = false;
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_vehiculo")
    private TipoVehiculo tipoVehiculo;
} 