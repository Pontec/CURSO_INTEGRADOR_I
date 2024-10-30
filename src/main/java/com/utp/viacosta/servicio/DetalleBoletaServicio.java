package com.utp.viacosta.servicio;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;

import java.time.LocalDate;
import java.util.List;

public interface DetalleBoletaServicio {

    List<DetalleBoletaDTO> getAllReporteVentas();
    List<DetalleBoletaDTO> getAllDetalleBoletasForClient(String nombreCliente);
    List<DetalleBoletaDTO> getAllDetalleBoletasForEmployee(String nombreEmpleado);
    List<DetalleBoletaDTO> getAllDetalleBoletasForDate(LocalDate fechaInicio, LocalDate fechaFin);
}
