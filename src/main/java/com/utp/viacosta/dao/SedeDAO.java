package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.SedeModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SedeDAO extends JpaRepository<SedeModelo, Integer> {

}
