package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.DetalleEncomiendaDAO;
import com.utp.viacosta.servicio.DetalleEncomiendaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleEncomiendaServicioImpl implements DetalleEncomiendaServicio {
    @Autowired
    private DetalleEncomiendaDAO detalleEncomiendaDAO;
}
