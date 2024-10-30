package com.utp.viacosta.dao;

import com.utp.viacosta.model.SedeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeRepository extends JpaRepository<SedeModel, Integer> {

}
