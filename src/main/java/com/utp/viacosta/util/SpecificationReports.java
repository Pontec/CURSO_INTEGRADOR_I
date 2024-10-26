package com.utp.viacosta.util;

import com.utp.viacosta.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpecificationReports implements Specification<DetalleBoletaModel> {

    private String nombreCliente;
    private String nombreEmpleado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public SpecificationReports(String nombreCliente, String nombreEmpleado, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombreCliente = nombreCliente;
        this.nombreEmpleado = nombreEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public SpecificationReports() {
    }

    @Override
    public Predicate toPredicate(Root<DetalleBoletaModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Join<DetalleBoletaModel, CompraModel> boletaCompraJoin = root.join("compra");
        Join<CompraModel, ClienteModel> compraClienteJoin = boletaCompraJoin.join("cliente");
        Join<CompraModel, EmpleadoModel> compraEmpleadoJoin = boletaCompraJoin.join("empleado");
        Join<DetalleBoletaModel, AsientoModel> boletaAsientoJoin = root.join("asiento");
        Join<AsientoModel, BusModel> asientoBusJoin = boletaAsientoJoin.join("bus");
        Join<BusModel, AsignacionBusRutaModel> busAsignacionJoin = asientoBusJoin.join("asignaciones");
        Join<AsignacionBusRutaModel, RutaModel> asigRutaJoin = busAsignacionJoin.join("rutaAsignada");
        Join<AsientoModel, TipoAsientoModel> asientoTipoJoin = boletaAsientoJoin.join("tipoAsiento");

        List<Predicate> predicateList = new ArrayList<>();

        if (StringUtils.hasText(nombreCliente)) {
            predicateList.add(criteriaBuilder.like(criteriaBuilder.concat(compraClienteJoin.get("nombre"), criteriaBuilder.concat(" ", compraClienteJoin.get("apellido"))), nombreCliente + "%"));
        }
        if (StringUtils.hasText(nombreEmpleado)) {
            predicateList.add(criteriaBuilder.like(criteriaBuilder.concat(compraEmpleadoJoin.get("nombre"), criteriaBuilder.concat(" ", compraEmpleadoJoin.get("apellido"))), nombreEmpleado + "%"));
        }
        if (fechaInicio != null && fechaFin != null) {
            predicateList.add(criteriaBuilder.between(root.get("fechaViaje"), fechaInicio, fechaFin));
        } else if (fechaInicio != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaViaje"), fechaInicio));
        } else if (fechaFin != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaViaje"), fechaFin));
        }

        query.multiselect(
                criteriaBuilder.concat(compraClienteJoin.get("nombre"), criteriaBuilder.concat(" ", compraClienteJoin.get("apellido"))).alias("cliente"),
                criteriaBuilder.concat(asigRutaJoin.get("origen"), criteriaBuilder.concat(" - ", asigRutaJoin.get("destino"))).alias("ruta"),
                root.get("fechaViaje").alias("fechaSalida"),
                root.get("horaViaje").alias("horaSalida"),
                boletaAsientoJoin.get("numeroAsiento").alias("asiento"),
                criteriaBuilder.concat(compraEmpleadoJoin.get("nombre"), criteriaBuilder.concat(" ", compraEmpleadoJoin.get("apellido"))).alias("responsable"),
                criteriaBuilder.sum(boletaAsientoJoin.get("precio"), asientoTipoJoin.get("cargoExtra")).alias("precioTotal")
        );

        query.orderBy(criteriaBuilder.asc(root.get("fechaViaje")));

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
