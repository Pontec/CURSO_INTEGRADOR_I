package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsignacionBusRutaDAO extends JpaRepository<AsignacionBusRutaModelo, Integer> {

    List<AsignacionBusRutaModelo> findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(
            String origen,
            String destino,
            LocalDate fechaSalida);

}
