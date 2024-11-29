package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.ComprobanteDAO;
import com.utp.viacosta.servicio.ComprobanteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteServicioImpl implements ComprobanteServicio {

    @Autowired
    private ComprobanteDAO comprobanteDAO;

    @Override
    public int countByTipoComprobante(String tipoComprobante) {
        return comprobanteDAO.countByTipoComprobante(tipoComprobante);
    }
}
