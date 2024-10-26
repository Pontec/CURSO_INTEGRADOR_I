package com.utp.viacosta.model;

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
@Table(name = "tipo_asiento")
public class TipoAsientoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_asiento")
    private Integer idAsiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoAsiento nombre;

    private String descripcion;
    @Column(name = "cargo_adicional")
    private double cargoExtra;

    @OneToMany(mappedBy = "tipoAsiento")
    private List<AsientoModel> asientos;

    public enum TipoAsiento {
        VIP, ECONOMICO
    }
}
