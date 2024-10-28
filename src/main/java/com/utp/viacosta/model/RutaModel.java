package com.utp.viacosta.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rutas")
public class RutaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Integer idRuta;
    private String origen;
    private String destino;
    private String duracion;
    @OneToMany(mappedBy = "rutaAsignada")
    private List<AsignacionBusRutaModel> asignaciones;

    public String toString() {
        return origen + " - " + destino;
    }
}
