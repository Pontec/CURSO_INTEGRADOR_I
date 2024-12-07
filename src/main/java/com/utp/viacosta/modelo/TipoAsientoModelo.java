package com.utp.viacosta.modelo;

import com.utp.viacosta.modelo.enums.TipoAsiento;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipo_asiento")
public class TipoAsientoModelo {
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
    private List<AsientoModelo> asientos;

    @Override
    public String toString() {
        return "" + nombre;
    }

}
