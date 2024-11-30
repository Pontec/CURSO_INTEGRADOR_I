package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.AsientoDAO;
import com.utp.viacosta.dao.AsientoEstadoFechaDAO;
import com.utp.viacosta.modelo.AsientoEstadoFechaModelo;
import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.enums.Estado;
import com.utp.viacosta.servicio.AsientoEstadoFechaServicio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AsientoEstadoFechaServicioImpl implements AsientoEstadoFechaServicio {
    private final AsientoEstadoFechaDAO asientoEstadoFechaDAO;
    private final AsientoDAO asientoDAO;

    @Override
    @Transactional
    public boolean estaAsientoOcupado(Integer idAsiento, LocalDate fecha, LocalTime hora) {
        return asientoEstadoFechaDAO.existsByAsiento_IdAsientoAndFechaAndHoraAndEstado(
                idAsiento, fecha, hora, Estado.OCUPADO
        );
    }
    @Override
    @Transactional
    public AsientoEstadoFechaModelo marcarAsientoOcupado(
            AsientoModelo asiento,
            LocalDate fecha,
            LocalTime hora,
            AsignacionBusRutaModelo asignacion
    ) {
        // Primero buscamos si ya existe un estado para esta fecha y hora
        Optional<AsientoEstadoFechaModelo> estadoExistente = asientoEstadoFechaDAO
                .findByAsiento_IdAsientoAndFechaAndHora(asiento.getIdAsiento(), fecha, hora);

        AsientoEstadoFechaModelo estadoAsiento;
        if (estadoExistente.isPresent()) {
            estadoAsiento = estadoExistente.get();
            estadoAsiento.setEstado(Estado.OCUPADO);
            estadoAsiento.setAsiento(asiento);
            estadoAsiento.setAsignacionBusRuta(asignacion);
        } else {
            estadoAsiento = new AsientoEstadoFechaModelo();
            estadoAsiento.setAsiento(asiento);
            estadoAsiento.setFecha(fecha);
            estadoAsiento.setHora(hora);
            estadoAsiento.setEstado(Estado.OCUPADO);
            estadoAsiento.setAsignacionBusRuta(asignacion);
        }

        return asientoEstadoFechaDAO.save(estadoAsiento);
    }
}
