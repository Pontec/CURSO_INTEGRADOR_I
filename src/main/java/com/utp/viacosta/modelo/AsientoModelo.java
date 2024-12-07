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
@Table(name = "asientos")
public class AsientoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asiento")
    private Integer idAsiento;

    @Column(name = "id_bus")
    private int idBus;

    @Column(name = "numero_asiento")
    private int numeroAsiento;

    private double precio;

    @Column(name = "id_tipo_asiento")
    private int idTipoAsiento;

    @ManyToOne
    @JoinColumn(name = "id_tipo_asiento" , insertable = false, updatable = false)
    private TipoAsientoModelo tipoAsiento;

    @ManyToOne
    @JoinColumn(name = "id_bus" , insertable = false, updatable = false)
    private BusModelo bus;

    @OneToMany(mappedBy = "asiento")
    private List<DetalleBoletaModelo> detalleBoletas;

    @OneToMany(mappedBy = "asiento")
    private List<AsientoEstadoFechaModelo> estadosFecha;


}
