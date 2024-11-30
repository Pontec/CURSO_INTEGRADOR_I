package com.utp.viacosta.modelo;

import com.utp.viacosta.modelo.enums.Estado;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "asiento_estado_fecha")
public class AsientoEstadoFechaModelo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_estado_asiento")
    private Integer idEstadoAsiento;
    @ManyToOne
    @JoinColumn(name = "id_asiento")
    private AsientoModelo asiento;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private LocalTime hora;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;
    @ManyToOne
    @JoinColumn(name = "id_asignacion_bus_ruta")
    private AsignacionBusRutaModelo asignacionBusRuta;
}
