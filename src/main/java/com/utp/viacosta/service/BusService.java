package com.utp.viacosta.service;


import com.utp.viacosta.model.BusModel;

import java.util.List;

public interface BusService {
    List<BusModel> findAll();
    BusModel findById(Integer id);
    BusModel save(BusModel bus);
    void deleteById(Integer id);
    BusModel update(BusModel bus);
}
