package com.utp.viacosta.servicio;

import com.utp.viacosta.modelo.EmpleadoModelo;

import java.util.List;

public interface EmpleadoServicio {
    List<EmpleadoModelo> findAll();
    EmpleadoModelo findByCorreo(String usuario);
    EmpleadoModelo save(EmpleadoModelo empleado);
    EmpleadoModelo autenticar(String correo, String password);
}
