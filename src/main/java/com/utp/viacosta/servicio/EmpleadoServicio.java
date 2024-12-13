package com.utp.viacosta.servicio;

import com.utp.viacosta.modelo.EmpleadoModelo;

import java.util.List;

public interface EmpleadoServicio {
    List<EmpleadoModelo> findAll();
    EmpleadoModelo findByCorreo(String usuario);
    EmpleadoModelo findByDni(String dni);
    EmpleadoModelo save(EmpleadoModelo empleado);

    EmpleadoModelo autenticar(String correo, String password);
    List<EmpleadoModelo> buscarEmpleados(String searchText);

}
