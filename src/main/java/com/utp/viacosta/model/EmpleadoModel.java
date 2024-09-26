package com.utp.viacosta.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    @Column(name = "contraseña")
    private String password;
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "id_sede")
    private SedesModel sede;

    @ManyToMany(fetch = FetchType.EAGER) //Traera tambien la entidad relaciona
    @JoinTable(name = "usuario_roles",
            joinColumns = @JoinColumn(name = "id_empleado"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<RolModel> roles = new HashSet<>();
    @Override
    public String toString() {
        return "EmpleadoModel{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
