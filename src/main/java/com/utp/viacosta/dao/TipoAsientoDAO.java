package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.TipoAsientoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoAsientoDAO extends JpaRepository<TipoAsientoModelo, Integer> {

}
