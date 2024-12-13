package com.utp.viacosta.servicio;




import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.enums.EstadoAsignacion;

import java.time.LocalDate;
import java.util.List;

public interface AsignacionBusRutaServicio {
    List<AsignacionBusRutaModelo> findAll();
    AsignacionBusRutaModelo findById(Integer id);
    AsignacionBusRutaModelo save(AsignacionBusRutaModelo asignacionBusRutaModelo);

    List<AsignacionBusRutaModelo> findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(String origen,
            String destino, LocalDate fecha);

    List<AsignacionBusRutaModelo> buscarAsignacionConEstado(
            String origen,
            String destino,
            LocalDate fechaSalida,
            EstadoAsignacion estado);

    List<AsignacionBusRutaModelo> buscarAsignaciones(String searchText, LocalDate fechaInicio, LocalDate fechaFin);
    List<AsignacionBusRutaModelo> buscarAsignacionesDelDia(LocalDate fechaSalida, EstadoAsignacion estado);
    
}
