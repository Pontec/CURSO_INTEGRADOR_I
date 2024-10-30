package com.utp.viacosta.dao;

import com.utp.viacosta.model.RolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Rolrepository extends JpaRepository<RolModel, Integer> {

}
