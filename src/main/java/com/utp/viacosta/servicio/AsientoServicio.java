package com.utp.viacosta.servicio;


import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.enums.Estado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AsientoServicio {
    List<AsientoModelo> findAll();
    AsientoModelo save(AsientoModelo asiento);
    List<AsientoModelo> getAsientosPorBus(int idBus);
    void deleteById(Integer id);
    List<AsientoModelo> obtenerAsientosDisponibles(Integer idBus,LocalDate fecha,LocalTime hora);
}
