package com.utp.viacosta.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asignacion_buses_rutas")
public class AsignacionBusRutaModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Integer idAsignacion;
    @Column(name = "id_bus")
    private int idBus;
    @Column(name = "id_ruta")
    private int idRuta;
    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;
    @Column(name = "hora_salida")
    private LocalTime horaSalida;
    private Double precio;
    @ManyToOne
    @JoinColumn(name = "id_bus", insertable = false, updatable = false)
    private BusModelo busAsignado;
    @ManyToOne
    @JoinColumn(name = "id_ruta", insertable = false, updatable = false)
    private RutaModelo rutaAsignada;
    @OneToMany(mappedBy = "asignacionBusRuta")
    private List<DetalleBoletaModelo> detalleBoletaModelo;
    @OneToMany(mappedBy = "asignacionBusRuta")
    private List<AsientoEstadoFechaModelo> estadosFecha;

}
