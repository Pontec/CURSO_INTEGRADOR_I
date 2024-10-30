package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.CompraRepository;
import com.utp.viacosta.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraServiceImpl implements CompraService {
    @Autowired
    private CompraRepository compraRepository;
}
