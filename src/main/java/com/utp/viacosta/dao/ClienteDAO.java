package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.ClienteModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteDAO extends JpaRepository<ClienteModelo, Integer> {
    ClienteModelo findByDni(String dni);

    @Query("SELECT c FROM ClienteModelo c WHERE " +
           "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(c.apellido) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "c.dni LIKE CONCAT('%', :searchText, '%')")
    List<ClienteModelo> findBySearchText(@Param("searchText") String searchText);

}
