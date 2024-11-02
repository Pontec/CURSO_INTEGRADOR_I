package com.utp.viacosta.servicio.impl;


import com.utp.viacosta.dao.AsignacionBusRutaDAO;
import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.servicio.AsignacionBusRutaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsignacionBusRutaServicioImpl implements AsignacionBusRutaServicio {
    @Autowired
    private AsignacionBusRutaDAO asignacionBusRutaDAO;

    @Override
    public List<AsignacionBusRutaModelo> findAll() {
        return asignacionBusRutaDAO.findAll();
    }

    @Override
    public AsignacionBusRutaModelo findById(Integer id) {
        Optional<AsignacionBusRutaModelo> asignacionBusRutaModel = asignacionBusRutaDAO.findById(id);
        return asignacionBusRutaModel.get();
    }

    @Override
    public AsignacionBusRutaModelo save(AsignacionBusRutaModelo asignacionBusRutaModelo) {
        return asignacionBusRutaDAO.save(asignacionBusRutaModelo);
    }

    @Override
    public List<AsignacionBusRutaModelo> findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(String origen, String destino, LocalDate fecha) {
        return asignacionBusRutaDAO.findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(origen, destino, fecha);
    }
}
