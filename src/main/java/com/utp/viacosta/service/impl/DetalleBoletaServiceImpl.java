package com.utp.viacosta.service.impl;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;
import com.utp.viacosta.dao.DetalleBoletaRepository;
import com.utp.viacosta.model.DetalleBoletaModel;
import com.utp.viacosta.service.DetalleBoletaService;
import com.utp.viacosta.util.SpecificationReports;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleBoletaServiceImpl implements DetalleBoletaService {

    @Autowired
    private DetalleBoletaRepository detalleBoletaRepository;

    @Override
    public List<DetalleBoletaDTO> getAllReporteVentas() {
        List<DetalleBoletaModel> detalle = detalleBoletaRepository.findAll();
        return detalle.stream()
                .map(this::mapDTO).collect(Collectors.toList());
    }

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForClient(String cliente) {
        List<DetalleBoletaModel> detalle = detalleBoletaRepository.findAll();
        return detalle.stream()
                .filter(model -> model.getCompra().getCliente().getNombre().toLowerCase().trim().contains(cliente.toLowerCase().trim()) || model.getCompra().getCliente().getApellido().toLowerCase().trim().contains(cliente.toLowerCase().trim()))
                .map(this::mapDTO).collect(Collectors.toList());
    }

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForEmployee(String empleado) {
        List<DetalleBoletaModel> detalle = detalleBoletaRepository.findAll();
        return detalle.stream()
                .filter(model -> model.getCompra().getEmpleado().getNombre().toLowerCase().trim().contains(empleado.toLowerCase().trim()) || model.getCompra().getEmpleado().getApellido().toLowerCase().trim().contains(empleado.toLowerCase().trim()))
                .map(this::mapDTO).collect(Collectors.toList());
    }

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForDate(LocalDate fechaInicio, LocalDate fechaFin) {
        List<DetalleBoletaModel> detalle = detalleBoletaRepository.findAll();
        return detalle.stream()
                .filter(model -> !model.getFechaViaje().isBefore(fechaInicio) && !model.getFechaViaje().isAfter(fechaFin))
                .map(this::mapDTO).collect(Collectors.toList());

    }

    private DetalleBoletaDTO mapDTO(DetalleBoletaModel model) {
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
