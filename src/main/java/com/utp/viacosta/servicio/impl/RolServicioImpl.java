package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.RolDAO;
import com.utp.viacosta.modelo.RolModelo;
import com.utp.viacosta.servicio.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicioImpl implements RolServicio {

    @Autowired
    private RolDAO rolDAO;

    @Override

    public List<RolModelo> findAll() {
        return rolDAO.findAll();
    }
}
