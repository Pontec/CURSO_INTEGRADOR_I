package com.utp.viacosta.service;


import com.utp.viacosta.model.ClienteModel;

import java.util.List;

public interface ClienteService {
    void guardarCliente(ClienteModel clienteModel);
    List<ClienteModel> listaClientes();
    void actualizarCliente(ClienteModel clienteModel);
}
