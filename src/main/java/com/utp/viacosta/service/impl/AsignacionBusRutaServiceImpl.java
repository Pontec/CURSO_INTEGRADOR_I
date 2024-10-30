package com.utp.viacosta.service.impl;


import com.utp.viacosta.controller.AsignarRutasBuses;
import com.utp.viacosta.dao.AsignacionBusRutaRepository;
import com.utp.viacosta.model.AsignacionBusRutaModel;
import com.utp.viacosta.service.AsignacionBusRutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsignacionBusRutaServiceImpl implements AsignacionBusRutaService {
    @Autowired
    private AsignacionBusRutaRepository asignacionBusRutaRepository;

    @Override
    public List<AsignacionBusRutaModel> findAll() {
        return asignacionBusRutaRepository.findAll();
    }

    @Override
    public List<AsignacionBusRutaModel> findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(String origen, String destino, LocalDate fechaSalida) {
        return  asignacionBusRutaRepository.findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(origen, destino, fechaSalida);
    }

    @Override
    public AsignacionBusRutaModel findById(Integer id) {
        Optional<AsignacionBusRutaModel> asignacionBusRutaModel = asignacionBusRutaRepository.findById(id);
        return asignacionBusRutaModel.get();
    }

    @Override
    public AsignacionBusRutaModel save(AsignacionBusRutaModel asignacionBusRutaModel) {
        return asignacionBusRutaRepository.save(asignacionBusRutaModel);
    }
}
