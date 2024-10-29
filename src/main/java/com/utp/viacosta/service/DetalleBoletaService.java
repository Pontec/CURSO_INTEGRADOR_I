package com.utp.viacosta.service;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;

import java.time.LocalDate;
import java.util.List;

public interface DetalleBoletaService {

    List<DetalleBoletaDTO> getAllDetalleBoletas();
    List<DetalleBoletaDTO> getAllDetalleBoletasForClient(String nombreCliente);
    List<DetalleBoletaDTO> getAllDetalleBoletasForEmployee(String nombreEmpleado);
    List<DetalleBoletaDTO> getAllDetalleBoletasForDate(LocalDate fechaInicio, LocalDate fechaFin);
}
