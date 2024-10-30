package com.utp.viacosta.dao;

import com.utp.viacosta.model.BusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BusRepository extends JpaRepository<BusModel, Integer> {

}
