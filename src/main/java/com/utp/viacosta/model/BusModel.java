package com.utp.viacosta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buses")
public class BusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bus")
    private Integer idBus;
    private String marca;
    private String placa;
    private String modelo;
    @Column(name = "capacidad_asientos")
    private int capacidadAsientos;
    @Column(name = "capacidad_carga")
    private double capacidadCarga;

    @OneToMany(mappedBy = "bus")
    private List<AsientoModel> asientos;

    @OneToMany(mappedBy = "busAsignado")
    private List<AsignacionBusRutaModel> asignaciones;

    @OneToMany(mappedBy = "bus")
    private List<DetalleEncomiendaModel> detalleEncomiendas;
}
