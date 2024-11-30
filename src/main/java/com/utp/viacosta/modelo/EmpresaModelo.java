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
@Table(name = "empresa")
public class EmpresaModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Integer id;
    @Column(name = "razon_social")
    private String razonSocial;
    private String ruc;
    private String ciudad;
    private String departamento;
    private String pais;
    private String telefono;

    @OneToMany(mappedBy = "empresa")
    private List<SedeModelo> listSedes;

}
