package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.AsientoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AsientoDAO extends JpaRepository<AsientoModelo, Integer> {
    List<AsientoModelo> findByIdBus(int idBus);
    AsientoModelo findByIdAsiento(int idAsiento);

}
