package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.EmpleadoModelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoDAO extends JpaRepository<EmpleadoModelo, Integer> {

    EmpleadoModelo findByCorreo(String usuario);

    EmpleadoModelo findByDni(String dni);
    

    @Query("SELECT e FROM EmpleadoModelo e WHERE " +
           "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(e.apellido) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "e.dni LIKE CONCAT('%', :searchText, '%')")
    List<EmpleadoModelo> findBySearchText(@Param("searchText") String searchText);
}
