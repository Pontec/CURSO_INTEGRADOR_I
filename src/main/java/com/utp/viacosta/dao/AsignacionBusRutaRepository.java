package com.utp.viacosta.dao;

import com.utp.viacosta.model.AsignacionBusRutaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsignacionBusRutaRepository extends JpaRepository<AsignacionBusRutaModel, Integer> {

    List<AsignacionBusRutaModel> findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(
            String origen,
            String destino,
            LocalDate fechaSalida);

}
