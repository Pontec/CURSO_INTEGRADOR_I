package com.utp.viacosta.servicio;

import com.utp.viacosta.modelo.ComprobanteModelo;

import java.time.LocalDate;

public interface ComprobanteServicio {
    int countByTipoComprobante(String tipoComprobante);
    ComprobanteModelo guardarComprobante(String tipoComprobante, String numeroComprobante, LocalDate fechaEmision);
}
