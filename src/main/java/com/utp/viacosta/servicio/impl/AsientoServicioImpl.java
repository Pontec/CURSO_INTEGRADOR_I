package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.AsientoDAO;
import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.servicio.AsientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsientoServicioImpl implements AsientoServicio {

    @Autowired
    private AsientoDAO asientoDAO;

    @Override
    public List<AsientoModelo> findAll() {
        return null;
    }
    @Override
    public AsientoModelo save(AsientoModelo asiento) {
        return asientoDAO.save(asiento);
    }

    @Override
    public List<AsientoModelo> getAsientosPorBus(int idBus) {
        return asientoDAO.findByIdBus(idBus);
    }

    @Override
    public void deleteById(Integer id) {
        asientoDAO.deleteById(id);
    }
}
