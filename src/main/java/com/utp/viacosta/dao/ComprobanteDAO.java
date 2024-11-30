package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.ComprobanteModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprobanteDAO extends JpaRepository<ComprobanteModelo, Integer> {
    int countByTipoComprobante(String tipoComprobante);
}