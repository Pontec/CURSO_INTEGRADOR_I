package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.ClienteModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteDAO extends JpaRepository<ClienteModelo, Integer> {
}
