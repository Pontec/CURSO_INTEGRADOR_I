package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.ClienteRepository;
import com.utp.viacosta.model.ClienteModel;
import com.utp.viacosta.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void guardarCliente(ClienteModel clienteModel) {
        clienteRepository.save(clienteModel);
    }

    @Override
    public List<ClienteModel> listaClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public void actualizarCliente(ClienteModel clienteModel) {
        clienteRepository.save(clienteModel);
    }
}
