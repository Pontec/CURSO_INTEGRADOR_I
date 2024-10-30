package com.utp.viacosta.service;




import com.utp.viacosta.model.AsignacionBusRutaModel;

import java.time.LocalDate;
import java.util.List;

public interface AsignacionBusRutaService {
    List<AsignacionBusRutaModel> findAll();
    List<AsignacionBusRutaModel> findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(String origen, String destino, LocalDate fechaSalida);
    AsignacionBusRutaModel findById(Integer id);
    AsignacionBusRutaModel save(AsignacionBusRutaModel asignacionBusRutaModel);
}
