package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.EmpleadoDAO;
import com.utp.viacosta.modelo.EmpleadoModelo;
import com.utp.viacosta.servicio.EmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServicioImpl implements EmpleadoServicio {

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Override
    public List<EmpleadoModelo> findAll() {
        return empleadoDAO.findAll();
    }

    @Override
    public EmpleadoModelo findByCorreo(String usuario) {
        return empleadoDAO.findByCorreo(usuario);
    }

    @Override
    public EmpleadoModelo findByDni(String dni) {
        return empleadoDAO.findByDni(dni);
    }

    @Override
    public EmpleadoModelo save(EmpleadoModelo empleado) {
        return empleadoDAO.save(empleado);
    }


    @Override
    public EmpleadoModelo autenticar(String correo, String password) {
        EmpleadoModelo usuario = findByCorreo(correo);
        if (usuario != null && usuario.getPassword().equals(password) && usuario.isEstado()==true) {
            return usuario;
        } throw new IllegalArgumentException("Credeciales incorrectas");
    }

}
