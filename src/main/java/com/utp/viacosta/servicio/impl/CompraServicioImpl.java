package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.CompraDAO;
import com.utp.viacosta.servicio.CompraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraServicioImpl implements CompraServicio {
    @Autowired
    private CompraDAO compraDAO;
}
