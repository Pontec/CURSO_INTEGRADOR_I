package com.utp.viacosta.service;




import com.utp.viacosta.model.AsignacionBusRutaModel;

import java.util.List;

public interface AsignacionBusRutaService {
    List<AsignacionBusRutaModel> findAll();
    AsignacionBusRutaModel findById(Integer id);
    AsignacionBusRutaModel save(AsignacionBusRutaModel asignacionBusRutaModel);
}
