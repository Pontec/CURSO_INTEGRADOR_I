package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.Rolrepository;
import com.utp.viacosta.model.RolModel;
import com.utp.viacosta.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private Rolrepository rolrepository;

    @Override

    public List<RolModel> findAll() {
        return rolrepository.findAll();
    }
}
