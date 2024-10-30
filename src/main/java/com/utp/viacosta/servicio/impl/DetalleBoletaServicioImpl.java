package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;
import com.utp.viacosta.dao.DetalleBoletaDAO;
import com.utp.viacosta.modelo.DetalleBoletaModelo;
import com.utp.viacosta.servicio.DetalleBoletaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleBoletaServicioImpl implements DetalleBoletaServicio {

    @Autowired
    private DetalleBoletaDAO detalleBoletaDAO;

    @Override
    public List<DetalleBoletaDTO> getAllReporteVentas() {
        List<DetalleBoletaModelo> detalle = detalleBoletaDAO.findAll();
        return detalle.stream()
                .map(this::mapDTO).collect(Collectors.toList());
    }

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForClient(String cliente) {
        List<DetalleBoletaModelo> detalle = detalleBoletaDAO.findAll();
        return detalle.stream()
                .filter(model -> model.getCompra().getCliente().getNombre().toLowerCase().trim().contains(cliente.toLowerCase().trim()) || model.getCompra().getCliente().getApellido().toLowerCase().trim().contains(cliente.toLowerCase().trim()))
                .map(this::mapDTO).collect(Collectors.toList());
    }

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForEmployee(String empleado) {
        List<DetalleBoletaModelo> detalle = detalleBoletaDAO.findAll();
        return detalle.stream()
                .filter(model -> model.getCompra().getEmpleado().getNombre().toLowerCase().trim().contains(empleado.toLowerCase().trim()) || model.getCompra().getEmpleado().getApellido().toLowerCase().trim().contains(empleado.toLowerCase().trim()))
                .map(this::mapDTO).collect(Collectors.toList());
    }

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForDate(LocalDate fechaInicio, LocalDate fechaFin) {
        List<DetalleBoletaModelo> detalle = detalleBoletaDAO.findAll();
        return detalle.stream()
                .filter(model -> !model.getFechaViaje().isBefore(fechaInicio) && !model.getFechaViaje().isAfter(fechaFin))
                .map(this::mapDTO).collect(Collectors.toList());

    }

    private DetalleBoletaDTO mapDTO(DetalleBoletaModelo model) {
        DetalleBoletaDTO dto = new DetalleBoletaDTO();
        dto.setCliente(model.getCompra().getCliente().getNombre() + " " + model.getCompra().getCliente().getApellido());
        dto.setRuta(model.getAsignacionBusRuta().getRutaAsignada().getOrigen() + " - " + model.getAsignacionBusRuta().getRutaAsignada().getDestino());
        dto.setFechaSalida(model.getAsignacionBusRuta().getFechaSalida());
        dto.setHoraSalida(model.getAsignacionBusRuta().getHoraSalida());
        dto.setAsiento(model.getAsiento().getNumeroAsiento());
        dto.setResponsable(model.getCompra().getEmpleado().getNombre() + " " + model.getCompra().getEmpleado().getApellido());
        dto.setPrecioTotal(model.getPrecioUnitario() + model.getAsiento().getTipoAsiento().getCargoExtra());
        return dto;
    }

}
