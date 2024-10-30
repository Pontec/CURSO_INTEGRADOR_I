package com.utp.viacosta.dao;

import com.utp.viacosta.model.TipoAsientoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoAsientoRepository extends JpaRepository<TipoAsientoModel, Integer> {

}
