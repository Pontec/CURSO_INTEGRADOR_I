package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.BusModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BusDAO extends JpaRepository<BusModelo, Integer> {
    BusModelo findByPlaca(String placa);
}
