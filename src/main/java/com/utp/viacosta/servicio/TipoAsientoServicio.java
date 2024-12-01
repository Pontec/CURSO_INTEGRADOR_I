package com.utp.viacosta.servicio;


import com.utp.viacosta.modelo.TipoAsientoModelo;

import java.util.List;

public interface TipoAsientoServicio {
    List<TipoAsientoModelo> findAll();
}
