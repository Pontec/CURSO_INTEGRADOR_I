package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.AsientoRepository;
import com.utp.viacosta.model.AsientoModel;
import com.utp.viacosta.service.AsientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsientoServiceImpl implements AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;

    @Override
    public List<AsientoModel> findAll() {
        return null;
    }
    @Override
    public AsientoModel save(AsientoModel asiento) {
        return asientoRepository.save(asiento);
    }

    @Override
    public List<AsientoModel> getAsientosPorBus(int idBus) {
        return asientoRepository.findByIdBus(idBus);
    }

    @Override
    public void deleteById(Integer id) {
        asientoRepository.deleteById(id);
    }
}
