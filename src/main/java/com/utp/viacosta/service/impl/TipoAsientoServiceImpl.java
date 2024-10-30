package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.TipoAsientoRepository;
import com.utp.viacosta.service.TipoAsientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoAsientoServiceImpl implements TipoAsientoService {
    @Autowired
    private TipoAsientoRepository tipoAsientoRepository;
}
