package com.utp.viacosta.dao;

import com.utp.viacosta.model.DetalleEncomiendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleEncomiendaRepository extends JpaRepository<DetalleEncomiendaModel, Integer> {
}
