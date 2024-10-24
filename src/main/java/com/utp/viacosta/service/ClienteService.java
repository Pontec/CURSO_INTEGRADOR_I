package com.utp.viacosta.service;


import com.utp.viacosta.model.ClienteModel;

import java.util.List;

public interface ClienteService {
    void guardarCliente(ClienteModel clienteModel);
    List<ClienteModel> listaClientes();
    void eliminarCliente(Integer id);
    void actualizarCliente(ClienteModel clienteModel);
}
