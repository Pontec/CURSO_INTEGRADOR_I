package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.EmpleadoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoDAO extends JpaRepository<EmpleadoModelo, Integer> {

    EmpleadoModelo findByCorreo(String usuario);


}
