package com.utp.viacosta.dao;

import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsignacionBusRutaDAO extends JpaRepository<AsignacionBusRutaModelo, Integer> {

    List<AsignacionBusRutaModelo> findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(
            String origen,
            String destino,
            LocalDate fechaSalida);
            
    @Query("SELECT a FROM AsignacionBusRutaModelo a WHERE " +
           "(:searchText IS NULL OR " +
           "CAST(a.rutaAsignada.id AS string) LIKE CONCAT('%', :searchText, '%') OR " +
           "CAST(a.busAsignado.id AS string) LIKE CONCAT('%', :searchText, '%') OR " +
           "LOWER(a.rutaAsignada.origen) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(a.rutaAsignada.destino) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(a.busAsignado.placa) LIKE LOWER(CONCAT('%', :searchText, '%'))) " +
           "AND (:fechaInicio IS NULL OR a.fechaSalida >= :fechaInicio) " +
           "AND (:fechaFin IS NULL OR a.fechaSalida <= :fechaFin)")
    List<AsignacionBusRutaModelo> findBySearchTextAndFechas(
            @Param("searchText") String searchText,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);   

}
