package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.SedeRepository;
import com.utp.viacosta.model.SedeModel;
import com.utp.viacosta.service.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SedeServiceImpl implements SedeService {

    @Autowired
    private SedeRepository sedeRepository;


    @Override
    public SedeModel guardarSede(SedeModel sedeModel) {
        return sedeRepository.save(sedeModel);

    }

    @Override
    public List<SedeModel> listaSedes() {
        return sedeRepository.findAll();
    }
}
