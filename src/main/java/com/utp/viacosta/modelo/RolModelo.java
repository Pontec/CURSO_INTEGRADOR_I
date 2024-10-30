package com.utp.viacosta.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RolModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;
    @Column(name = "nombre_rol", nullable = false, unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<EmpleadoModelo> empleados = new HashSet<>();

    @Override
    public String toString() {
        return role;
    }
}
