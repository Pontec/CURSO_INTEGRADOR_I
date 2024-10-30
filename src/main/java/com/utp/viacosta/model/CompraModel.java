package com.utp.viacosta.model;

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
@Table(name = "compras")
public class CompraModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Integer idCompra;
    @Column(name = "id_cliente")
    private int idCliente;
    @Column(name = "id_empleado")
    private int idEmpleado;
    private String tipoCompra;
    private LocalDate fecha;
    private LocalTime hora;

    @OneToMany(mappedBy = "compra")
    private List<DetalleBoletaModel> detalleBoletas;

    @OneToMany(mappedBy = "compra")
    private List<DetalleEncomiendaModel> detalleEncomienda;


    @ManyToOne
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private ClienteModel cliente;

    @ManyToOne
    @JoinColumn(name = "id_empleado", insertable = false, updatable = false)
    private EmpleadoModel empleado;

}
