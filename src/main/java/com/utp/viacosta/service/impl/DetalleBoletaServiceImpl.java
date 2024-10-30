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

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletas() {
        SpecificationReports specification = new SpecificationReports();
        return ejecutarConsulta(specification);
    }


    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForClient(String nombreCliente) {
        SpecificationReports specification = new SpecificationReports(nombreCliente, null, null, null);
        return ejecutarConsulta(specification);
    }

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForEmployee(String nombreEmpleado) {
        SpecificationReports specification = new SpecificationReports(null, nombreEmpleado, null, null);
        return ejecutarConsulta(specification);
    }

    @Override
    public List<DetalleBoletaDTO> getAllDetalleBoletasForDate(LocalDate fechaInicio, LocalDate fechaFin) {
        SpecificationReports specification = new SpecificationReports(null, null, fechaInicio, fechaFin);
        List<DetalleBoletaModel> detalle = detalleBoletaRepository.findAll();
        return ejecutarConsulta(specification);

    }

    private List<DetalleBoletaDTO> ejecutarConsulta(SpecificationReports specification) {
        // Crear la consulta desde el EntityManager usando la especificación
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<DetalleBoletaModel> root = query.from(DetalleBoletaModel.class);

        Predicate predicate = specification.toPredicate(root, query, criteriaBuilder);
        query.where(predicate);

        // Ejecución de la consulta personalizada
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(query);
        List<Tuple> resultados = typedQuery.getResultList();

        // Mapear los resultados a DetalleBoletaDTO
        return resultados.stream()
                .map(tuple -> {
                    DetalleBoletaDTO dto = new DetalleBoletaDTO();
                    dto.setCliente(tuple.get(0, String.class)); // Cliente
                    dto.setRuta(tuple.get(1, String.class)); // Ruta
                    dto.setFechaSalida(tuple.get(2, LocalDate.class));// Fecha de salida
                    dto.setHoraSalida(tuple.get(3, LocalTime.class)); // Hora de salida
                    dto.setAsiento(tuple.get(4, Integer.class)); // Asiento
                    dto.setResponsable(tuple.get(5, String.class)); // Responsable
                    dto.setPrecioTotal(tuple.get(6, Double.class)); // Precio total
                    return dto;
                }).collect(Collectors.toList());
    }


    /*
     * La tupla obtiene los resultados sin saber que tipo hasta que lo especificamos con tuple.get(index, Class)
     */
}
