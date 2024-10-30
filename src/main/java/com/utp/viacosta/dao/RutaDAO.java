package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.RutaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RutaDAO extends JpaRepository<RutaModelo, Integer> {

}
