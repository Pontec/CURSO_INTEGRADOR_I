package com.utp.viacosta.dao;

import com.utp.viacosta.model.AsignacionBusRutaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionBusRutaRepository extends JpaRepository<AsignacionBusRutaModel, Integer> {

}
