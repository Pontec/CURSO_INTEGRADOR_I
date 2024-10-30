package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.RolModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolDAO extends JpaRepository<RolModelo, Integer> {

}
