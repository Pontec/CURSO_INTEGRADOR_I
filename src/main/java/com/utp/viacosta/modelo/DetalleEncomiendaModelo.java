package com.utp.viacosta.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_encomienda")
public class DetalleEncomiendaModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    private String descripcion;
    private double peso;
    @Column(name = "metodo_pago")
    private int metodoPago;
    @Column(name = "precio_unitario")
    private int precioUnitario;
    private int subtotal;
    @Column(name = "id_bus")
    private int idBus;

    @ManyToOne
    @JoinColumn(name = "id_comprobante" , insertable = false, updatable = false)
    private ComprobanteModelo comprobante;

    @ManyToOne
    @JoinColumn(name = "id_bus" , insertable = false, updatable = false)
    private BusModelo bus;

    @ManyToOne
    @JoinColumn(name = "id_compra" , insertable = false, updatable = false)
    private CompraModelo compra;
}
