package com.utp.viacosta.servicio.impl;


import com.utp.viacosta.dao.BusDAO;
import com.utp.viacosta.modelo.BusModelo;
import com.utp.viacosta.servicio.BusServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusServicioImpl implements BusServicio {
    @Autowired
    private BusDAO busDAO;

    @Override
    public Optional<BusModelo> findById(Integer id) {
        return busDAO.findById(id);
    }

    @Override
    public List<BusModelo> findAll() {
        return busDAO.findAll();
    }

    @Override
    public BusModelo save(BusModelo bus) {
        return busDAO.save(bus);
    }

    @Override
    public void deleteById(Integer id) {
        busDAO.deleteById(id);
    }

    @Override
    public BusModelo update(BusModelo bus) {
        busDAO.findById(bus.getIdBus()).orElseThrow(() -> new RuntimeException("El bus no existe"));
        return busDAO.save(bus);
    }

    @Override
    public BusModelo findByPlaca(String placa) {
        return busDAO.findByPlaca(placa);
    }

    @Override
    public List<BusModelo> buscarBuses(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return busDAO.findAll();
        }
        return busDAO.findBySearchText(searchText.trim());
    }
}
