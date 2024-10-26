package com.utp.viacosta.service.impl;


import com.utp.viacosta.dao.BusRepository;
import com.utp.viacosta.model.BusModel;
import com.utp.viacosta.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepository busRepository;

    @Override
    public List<BusModel> findAll() {
        return busRepository.findAll();
    }

    @Override
    public BusModel findById(Integer id) {
        return busRepository.findById(id).orElseThrow(() -> new RuntimeException("El bus no existe"));
    }

    @Override
    public BusModel save(BusModel bus) {
        return busRepository.save(bus);
    }

    @Override
    public void deleteById(Integer id) {
        busRepository.deleteById(id);
    }

    @Override
    public BusModel update(BusModel bus) {
        busRepository.findById(bus.getIdBus()).orElseThrow(() -> new RuntimeException("El bus no existe"));
        return busRepository.save(bus);
    }
}
