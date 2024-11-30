package com.utp.viacosta.servicio;

import com.utp.viacosta.modelo.AsientoEstadoFechaModelo;
import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.enums.Estado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AsientoEstadoFechaServicio {

    boolean estaAsientoOcupado(Integer idAsiento, LocalDate fecha, LocalTime hora);

    AsientoEstadoFechaModelo marcarAsientoOcupado(
            AsientoModelo asiento,
            LocalDate fecha,
            LocalTime hora,
            AsignacionBusRutaModelo asignacion
    );
}
