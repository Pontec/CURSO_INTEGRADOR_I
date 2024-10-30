package com.utp.viacosta.servicio;


import com.utp.viacosta.modelo.ClienteModelo;

import java.util.List;

public interface ClienteServicio {
    void guardarCliente(ClienteModelo clienteModelo);
    List<ClienteModelo> listaClientes();
    void actualizarCliente(ClienteModelo clienteModelo);
}
