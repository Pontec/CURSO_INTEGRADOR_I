package com.utp.viacosta.servicio;

import com.utp.viacosta.modelo.CompraModelo;

public interface CompraServicio {
    CompraModelo saveCompra(int idAsiento, int idEmpleado);
}
