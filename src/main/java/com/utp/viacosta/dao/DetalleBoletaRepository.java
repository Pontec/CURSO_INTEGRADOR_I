package com.utp.viacosta.dao;

import com.utp.viacosta.model.DetalleBoletaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleBoletaRepository extends JpaRepository<DetalleBoletaModel, Integer>, JpaSpecificationExecutor<DetalleBoletaModel> {

//    List<DetalleBoletaModel> findByCompra_Cliente_NombreStartingWith(String nombre);

}
