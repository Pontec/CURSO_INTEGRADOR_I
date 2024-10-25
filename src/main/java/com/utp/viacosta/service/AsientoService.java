package com.utp.viacosta.service;


import com.utp.viacosta.model.AsientoModel;

import java.util.List;

public interface AsientoService {
    List<AsientoModel> findAll();
    AsientoModel save(AsientoModel asiento);
    List<AsientoModel> getAsientosPorBus(int idBus);
    void deleteById(Integer id);
}
