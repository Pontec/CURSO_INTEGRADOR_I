package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.TipoAsientoDAO;
import com.utp.viacosta.servicio.TipoAsientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoAsientoServicioImpl implements TipoAsientoServicio {
    @Autowired
    private TipoAsientoDAO tipoAsientoDAO;
}
