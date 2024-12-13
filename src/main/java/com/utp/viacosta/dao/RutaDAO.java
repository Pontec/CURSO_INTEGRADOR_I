package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.RutaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RutaDAO extends JpaRepository<RutaModelo, Integer> {
    boolean existsByOrigen(String origen);

    @Query("SELECT r.destino, COUNT(db) as ventas FROM DetalleBoletaModelo db JOIN db.asignacionBusRuta abr JOIN abr.rutaAsignada r GROUP BY r.destino ORDER BY ventas DESC")
    List<Object[]> findTopRutas();

    @Query("SELECT r FROM RutaModelo r WHERE " +
           "LOWER(r.origen) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(r.destino) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<RutaModelo> findBySearchText(@Param("searchText") String searchText);

}
