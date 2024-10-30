package com.utp.viacosta.servicio;


import com.utp.viacosta.modelo.BusModelo;

import java.util.List;
import java.util.Optional;

public interface BusServicio {
    Optional<BusModelo> findById(Integer id);
    List<BusModelo> findAll();
    BusModelo save(BusModelo bus);
    void deleteById(Integer id);
    BusModelo update(BusModelo bus);
}
