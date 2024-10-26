package com.utp.viacosta.service;

import com.utp.viacosta.dto.DetalleBoletaDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DetalleBoletaService {

    List<DetalleBoletaDTO> getAllDetalleBoletas();
    List<DetalleBoletaDTO> getAllDetalleBoletasForClient(String nombreCliente);
    List<DetalleBoletaDTO> getAllDetalleBoletasForEmployee(String nombreEmpleado);
    List<DetalleBoletaDTO> getAllDetalleBoletasForDate(LocalDate fechaInicio, LocalDate fechaFin);
}
