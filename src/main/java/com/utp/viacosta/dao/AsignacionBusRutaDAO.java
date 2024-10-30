package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionBusRutaDAO extends JpaRepository<AsignacionBusRutaModelo, Integer> {

}
