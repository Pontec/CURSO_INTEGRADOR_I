package com.utp.viacosta.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleados")
public class EmpleadoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    @Column(name = "contraseña")
    private String password;
    private String telefono;
    @Column(name = "id_sede")
    private int idSede;


    @ManyToOne
    @JoinColumn(name = "id_sede", insertable = false, updatable = false)
    private SedeModel sede;

    @ManyToMany(fetch = FetchType.EAGER) //Traera tambien la entidad relaciona
    @JoinTable(name = "empleado_roles",
            joinColumns = @JoinColumn(name = "id_empleado"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<RolModel> roles = new HashSet<>();

    // Método para obtener los nombres de todos los roles en una cadena
    public String getRolNombres() {
        return roles != null && !roles.isEmpty()
                ? roles.stream().map(RolModel::getRole).collect(Collectors.joining(", "))
                : "Sin Rol";
    }


}
