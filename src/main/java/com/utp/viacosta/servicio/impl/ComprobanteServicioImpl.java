package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.ComprobanteDAO;
import com.utp.viacosta.modelo.ComprobanteModelo;
import com.utp.viacosta.servicio.ComprobanteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ComprobanteServicioImpl implements ComprobanteServicio {

    @Autowired
    private ComprobanteDAO comprobanteDAO;

    @Override
    public int countByTipoComprobante(String tipoComprobante) {
        return comprobanteDAO.countByTipoComprobante(tipoComprobante);
    }

    @Override
    public ComprobanteModelo guardarComprobante(String tipoComprobante, String numeroComprobante, LocalDate fechaEmision) {
        ComprobanteModelo comprobanteModelo = ComprobanteModelo.builder()
                .tipoComprobante(tipoComprobante)
                .numeroComprobante(numeroComprobante)
                .fechaEmision(fechaEmision)
                .build();
        return comprobanteDAO.save(comprobanteModelo);
    }
}
