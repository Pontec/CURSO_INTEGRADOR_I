package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.DetalleEncomiendaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleEncomiendaDAO extends JpaRepository<DetalleEncomiendaModelo, Integer> {
}
