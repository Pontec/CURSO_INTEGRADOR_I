package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.TipoAsientoDAO;
import com.utp.viacosta.modelo.TipoAsientoModelo;
import com.utp.viacosta.servicio.TipoAsientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAsientoServicioImpl implements TipoAsientoServicio {

    @Autowired
    private TipoAsientoDAO tipoAsientoDAO;

    @Override
    public List<TipoAsientoModelo> findAll() {
        return tipoAsientoDAO.findAll();
    }
}
