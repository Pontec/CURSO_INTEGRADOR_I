package com.utp.viacosta.service;

import com.utp.viacosta.model.EmpleadoModel;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<EmpleadoModel> findAll();
    EmpleadoModel findByCorreo(String usuario);
    EmpleadoModel save(EmpleadoModel empleado);
    void deleteById(Integer id);
    EmpleadoModel autenticar(String correo, String password);

}
