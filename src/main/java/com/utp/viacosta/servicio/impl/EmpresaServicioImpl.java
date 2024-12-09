package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.EmpresaDAO;
import com.utp.viacosta.modelo.EmpresaModelo;
import com.utp.viacosta.servicio.EmpresaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpresaServicioImpl implements EmpresaServicio {

    @Autowired
    EmpresaDAO empresaDAO;

    @Override
    public List<EmpresaModelo> findAll() {
        return empresaDAO.findAll();
    }

    @Override
    public EmpresaModelo save(EmpresaModelo empresa) {
        return empresaDAO.save(empresa);
    }
}
