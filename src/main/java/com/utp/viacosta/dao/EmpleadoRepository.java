package com.utp.viacosta.dao;

import com.utp.viacosta.model.EmpleadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoModel, Integer> {

    EmpleadoModel findByCorreo(String usuario);
}
