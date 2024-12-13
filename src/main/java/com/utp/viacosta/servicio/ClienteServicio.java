package com.utp.viacosta.servicio;


import com.utp.viacosta.modelo.ClienteModelo;

import java.util.List;

public interface ClienteServicio {
    ClienteModelo guardarCliente(String nombre, String apellido, String dni, String telefono, String direccion);
    List<ClienteModelo> listaClientes();
    void actualizarCliente(ClienteModelo clienteModelo);
    ClienteModelo save(ClienteModelo clienteModelo);
    ClienteModelo findByDni(String dni);
    List<ClienteModelo> buscarClientes(String searchText); 
}
