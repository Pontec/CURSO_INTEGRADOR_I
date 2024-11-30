package com.utp.viacosta.servicio;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;
import com.utp.viacosta.modelo.DetalleBoletaModelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DetalleBoletaServicio {

    DetalleBoletaModelo save(String descripcion, LocalDate fechaViaje, LocalTime horaViaje, String metodoPago, Double precioTotal, Integer idComprobante, Integer idAsiento, Integer idAsignacion, Integer idCompra);
    DetalleBoletaModelo detalle = new DetalleBoletaModelo();
    List<DetalleBoletaDTO> getAllReporteVentas();
    List<DetalleBoletaDTO> getAllDetalleBoletasForClient(String nombreCliente);
    List<DetalleBoletaDTO> getAllDetalleBoletasForEmployee(String nombreEmpleado);
    List<DetalleBoletaDTO> getAllDetalleBoletasForDate(LocalDate fechaInicio, LocalDate fechaFin);
}
