package com.utp.viacosta.servicio;


import com.utp.viacosta.modelo.AsientoModelo;

import java.util.List;

public interface AsientoServicio {
    List<AsientoModelo> findAll();
    AsientoModelo save(AsientoModelo asiento);
    List<AsientoModelo> getAsientosPorBus(int idBus);
    void deleteById(Integer id);
    void actualizarAsiento(AsientoModelo  asiento);
    AsientoModelo getAsientoPorId(Integer id);
}
