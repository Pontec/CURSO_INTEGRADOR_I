package com.utp.viacosta.servicio;

import com.utp.viacosta.modelo.EmpresaModelo;

import java.util.List;

public interface EmpresaServicio {
    List<EmpresaModelo> findAll();
    EmpresaModelo save(EmpresaModelo empresa);
    boolean existeRuc(String ruc);
}
