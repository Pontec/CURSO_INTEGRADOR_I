package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.AsientoDAO;
import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.enums.Estado;
import com.utp.viacosta.servicio.AsientoEstadoFechaServicio;
import com.utp.viacosta.servicio.AsientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsientoServicioImpl implements AsientoServicio {

    @Autowired
    private AsientoDAO asientoDAO;
    @Autowired
    private AsientoEstadoFechaServicio asientoEstadoFechaServicio;

    @Override
    public List<AsientoModelo> findAll() {
        return null;
    }
    @Override
    public AsientoModelo save(AsientoModelo asiento) {
        return asientoDAO.save(asiento);
    }

    @Override
    public List<AsientoModelo> getAsientosPorBus(int idBus) {
        return asientoDAO.findByIdBus(idBus);
    }

    @Override
    public void deleteById(Integer id) {
        asientoDAO.deleteById(id);
    }

    @Override
    public List<AsientoModelo> obtenerAsientosDisponibles(
            Integer idBus,
            LocalDate fecha,
            LocalTime hora
    ) {
        List<AsientoModelo> todosLosAsientos = asientoDAO.findByIdBus(idBus);

        return todosLosAsientos.stream()
                .filter(asiento -> !asientoEstadoFechaServicio.estaAsientoOcupado(
                        asiento.getIdAsiento(), fecha, hora
                ))
                .collect(Collectors.toList());
    }



    @Override
    public void actualizarAsiento(AsientoModelo  asiento) {
        AsientoModelo asientoExistente = asientoDAO.findByIdAsiento(asiento.getIdAsiento());

        if (asientoExistente != null) {
            // Si existe, actualizamos sus datos
            asientoExistente.setTipoAsiento(asiento.getTipoAsiento());
            asientoExistente.setPrecio(asiento.getPrecio());

            // Guardamos el asiento actualizado en la base de datos
            asientoDAO.save(asientoExistente);
        } else {
            throw new RuntimeException("El asiento con el ID " + asiento.getIdAsiento() + " no existe.");
        }
    }

    @Override
    public AsientoModelo getAsientoPorId(Integer id) {
        Optional<AsientoModelo> asiento = asientoDAO.findById(id);
        return asiento.orElse(null);
    }

}
