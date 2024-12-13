package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.BusModelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BusDAO extends JpaRepository<BusModelo, Integer> {
    BusModelo findByPlaca(String placa);

    @Query("SELECT b FROM BusModelo b WHERE " +
           "LOWER(b.placa) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(b.marca) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<BusModelo> findBySearchText(@Param("searchText") String searchText);
}
