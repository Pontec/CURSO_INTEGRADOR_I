package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.CompraModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraDAO extends JpaRepository<CompraModelo, Integer> {
}
