package com.utp.viacosta.service;


import com.utp.viacosta.model.BusModel;

import java.util.List;
import java.util.Optional;

public interface BusService {
    Optional<BusModel> findById(Integer id);
    List<BusModel> findAll();
    BusModel save(BusModel bus);
    void deleteById(Integer id);
    BusModel update(BusModel bus);
}
