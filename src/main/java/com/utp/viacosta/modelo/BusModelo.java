package com.utp.viacosta.modelo;

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
public class BusModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bus")
    private Integer idBus;
    private String marca;
    private String placa;
    private String modelo;
    //Andree: Agregue niveles de piso
    @Column(name = "primer_piso")
    private int primerPiso;
    @Column(name = "segundo_piso")
    private int segundoPiso;
    @Column(name = "capacidad_asientos")
    private int capacidadAsientos;
    @Column(name = "capacidad_carga")
    private double capacidadCarga;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsientoModelo> asientos;

    @OneToMany(mappedBy = "busAsignado")
    private List<AsignacionBusRutaModelo> asignaciones;

    @OneToMany(mappedBy = "bus")
    private List<DetalleEncomiendaModelo> detalleEncomiendas;

    public String toString() {
        return marca + " " + modelo + " " + placa;
    }

}
