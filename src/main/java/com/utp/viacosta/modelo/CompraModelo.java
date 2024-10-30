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
@Table(name = "compras")
public class CompraModelo {
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
    private List<DetalleBoletaModelo> detalleBoletas;

    @OneToMany(mappedBy = "compra")
    private List<DetalleEncomiendaModelo> detalleEncomienda;


    @ManyToOne
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private ClienteModelo cliente;

    @ManyToOne
    @JoinColumn(name = "id_empleado", insertable = false, updatable = false)
    private EmpleadoModelo empleado;

}
