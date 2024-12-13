package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.SedeDAO;
import com.utp.viacosta.modelo.SedeModelo;
import com.utp.viacosta.servicio.SedeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SedeServicioImpl implements SedeServicio {

    @Autowired
    private SedeDAO sedeDAO;


    @Override
    public SedeModelo guardarSede(SedeModelo sedeModelo) {
        return sedeDAO.save(sedeModelo);

    }

    @Override
    public List<SedeModelo> listaSedes() {
        return sedeDAO.findAll();
    }

}
