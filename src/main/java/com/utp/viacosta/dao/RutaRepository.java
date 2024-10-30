package com.utp.viacosta.dao;

import com.utp.viacosta.model.RutaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RutaRepository extends JpaRepository<RutaModel, Integer> {

}
