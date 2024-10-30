package com.utp.viacosta.servicio;




import com.utp.viacosta.modelo.AsignacionBusRutaModelo;

import java.util.List;

public interface AsignacionBusRutaServicio {
    List<AsignacionBusRutaModelo> findAll();
    AsignacionBusRutaModelo findById(Integer id);
    AsignacionBusRutaModelo save(AsignacionBusRutaModelo asignacionBusRutaModelo);
}
