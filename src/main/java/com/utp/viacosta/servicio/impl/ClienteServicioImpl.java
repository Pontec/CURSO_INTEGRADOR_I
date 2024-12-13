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
    public ClienteModelo guardarCliente(String nombre, String apellido, String dni, String telefono, String direccion) {
        ClienteModelo cliente = ClienteModelo.builder()
                .nombre(nombre)
                .apellido(apellido)
                .dni(dni)
                .telefono(telefono)
                .direccion(direccion)
                .build();
        clienteDAO.save(cliente);
        return cliente;
    }

    @Override
    public List<ClienteModelo> listaClientes() {
        return clienteDAO.findAll();
    }

    @Override
    public void actualizarCliente(ClienteModelo clienteModelo) {
        clienteDAO.save(clienteModelo);
    }

    @Override
    public ClienteModelo save(ClienteModelo clienteModelo) {
        return clienteDAO.save(clienteModelo);
    }

    @Override
    public ClienteModelo findByDni(String dni) {
        return clienteDAO.findByDni(dni);
    }

    @Override
    public List<ClienteModelo> buscarClientes(String searchText) {
    if (searchText == null || searchText.trim().isEmpty()) {
        return clienteDAO.findAll();
    }
        return clienteDAO.findBySearchText(searchText.trim());
    }
}
