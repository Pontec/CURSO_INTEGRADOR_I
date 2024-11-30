package com.utp.viacosta.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "detalle_boleta")
public class DetalleBoletaModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    private String descripcion;
    @Column(name = "fecha_viaje")
    private LocalDate fechaViaje;
    @Column(name = "hora_viaje")
    private LocalTime horaViaje;
    @Column(name = "metodo_pago")
    private String metodoPago;
    @Column(name = "precio_total")
    private double precioTotal;
    @Column(name = "id_comprobante")
    private int idComprobante;
    @Column(name = "id_asiento")
    private int idAsiento;
    @Column(name = "id_asignacion")
    private int idAsignacion;
    @Column(name = "id_compra")
    private int idCompra;

    @ManyToOne
    @JoinColumn(name = "id_comprobante", insertable = false, updatable = false)
    private ComprobanteModelo comprobante;

    @ManyToOne
    @JoinColumn(name = "id_asiento" , insertable = false, updatable = false)
    private AsientoModelo asiento;

    @ManyToOne
    @JoinColumn(name = "id_compra", insertable = false, updatable = false)
    private CompraModelo compra;

    @ManyToOne
    @JoinColumn(name = "id_asignacion", insertable = false, updatable = false)
    private AsignacionBusRutaModelo asignacionBusRuta;

}
