package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.SedeModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeDAO extends JpaRepository<SedeModelo, Integer> {

}
