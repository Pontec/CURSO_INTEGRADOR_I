package com.utp.viacosta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_boleta")
public class DetalleBoletaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    private String descripcion;
    @Column(name = "fecha_viaje")
    private Date fechaViaje;
    @Column(name = "hora_viaje")
    private Time horaViaje;
    @Column(name = "metodo_pago")
    private String metodoPago;
    @Column(name = "precio_unitario")
    private double precioUnitario;
    private double subtotal;
    @Column(name = "id_comprobante")
    private int idComprobante;
    @Column(name = "id_asiento")
    private int idAsiento;
    @Column(name = "id_compra")
    private int idCompra;

    @ManyToOne
    @JoinColumn(name = "id_comprobante", insertable = false, updatable = false)
    private ComprobanteModel comprobante;

    @ManyToOne
    @JoinColumn(name = "id_asiento" , insertable = false, updatable = false)
    private AsientoModel asiento;

    @ManyToOne
    @JoinColumn(name = "id_compra", insertable = false, updatable = false)
    private CompraModel compra;

}
