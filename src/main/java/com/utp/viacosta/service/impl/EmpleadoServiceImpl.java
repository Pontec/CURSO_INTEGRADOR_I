package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.EmpleadoRepository;
import com.utp.viacosta.model.EmpleadoModel;
import com.utp.viacosta.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<EmpleadoModel> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public EmpleadoModel findByCorreo(String usuario) {
        return empleadoRepository.findByCorreo(usuario);
    }

    @Override
    public EmpleadoModel save(EmpleadoModel empleado) {
        return empleadoRepository.save(empleado);
    }


    @Override
    public EmpleadoModel autenticar(String correo, String password) {
        EmpleadoModel usuario = findByCorreo(correo);
        if (usuario != null && usuario.getPassword().equals(password) && usuario.isEstado()==true) {
            return usuario;
        } throw new IllegalArgumentException("Credeciales incorrectas");
    }

}
