package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.ClienteDAO;
import com.utp.viacosta.modelo.ClienteModelo;
import com.utp.viacosta.servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicioImpl implements ClienteServicio {

    @Autowired
    private ClienteDAO clienteDAO;

    @Override
    public void guardarCliente(ClienteModelo clienteModelo) {
        clienteDAO.save(clienteModelo);
    }

    @Override
    public List<ClienteModelo> listaClientes() {
        return clienteDAO.findAll();
    }

    @Override
    public void actualizarCliente(ClienteModelo clienteModelo) {
        clienteDAO.save(clienteModelo);
    }
}
