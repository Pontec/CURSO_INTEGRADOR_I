package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.DetalleBoletaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleBoletaDAO extends JpaRepository<DetalleBoletaModelo, Integer>, JpaSpecificationExecutor<DetalleBoletaModelo> {

//    List<DetalleBoletaModelo> findByCompra_Cliente_NombreStartingWith(String nombre);

}
