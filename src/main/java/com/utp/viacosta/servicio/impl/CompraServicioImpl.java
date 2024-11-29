package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.CompraDAO;
import com.utp.viacosta.modelo.CompraModelo;
import com.utp.viacosta.servicio.CompraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraServicioImpl implements CompraServicio {
    @Autowired
    private CompraDAO compraDAO;


    @Override
    public CompraModelo saveCompra(int idCliente, int idEmpleado) {
        CompraModelo compra = new CompraModelo();
        compra.setIdCliente(idCliente);
        compra.setIdEmpleado(idEmpleado);
        compraDAO.save(compra);
        return compra;
    }
}
