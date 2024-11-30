package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.AsientoEstadoFechaModelo;
import com.utp.viacosta.modelo.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AsientoEstadoFechaDAO extends JpaRepository<AsientoEstadoFechaModelo,Integer> {
    Optional<AsientoEstadoFechaModelo> findByAsiento_IdAsientoAndFechaAndHora(
            Integer idAsiento,
            LocalDate fecha,
            LocalTime hora
    );

    boolean existsByAsiento_IdAsientoAndFechaAndHoraAndEstado(
            Integer idAsiento,
            LocalDate fecha,
            LocalTime hora,
            Estado estado
    );
}
