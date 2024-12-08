package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.EmpresaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaDAO extends JpaRepository<EmpresaModelo, Integer> {
}
