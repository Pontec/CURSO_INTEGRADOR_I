package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.DetalleEncomiendaRepository;
import com.utp.viacosta.service.DetalleEncomiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleEncomiendaServiceImpl implements DetalleEncomiendaService {
    @Autowired
    private DetalleEncomiendaRepository detalleEncomiendaRepository;
}
