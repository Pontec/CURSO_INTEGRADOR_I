package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.ComprobanteRepository;
import com.utp.viacosta.service.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteServiceImpl implements ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

}
