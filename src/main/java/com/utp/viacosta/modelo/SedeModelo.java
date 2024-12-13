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
@Table(name = "sedes")
public class SedeModelo {
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private Integer id;
    @Column(name = "nombre_sede")
    private String nombreSedes;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String pais;
    private String telefono;
    
    @OneToMany(mappedBy = "sede")
    private List<EmpleadoModelo> listEmpleados;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private EmpresaModelo empresa;

    @Override
    public String toString() {
        return ciudad;
    }

}
