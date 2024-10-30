package com.utp.viacosta.agregates.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DetalleBoletaDTO {
    private String cliente;
    private String ruta;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private Integer asiento;
    private String responsable;
    private Double precioTotal;

    public List<Object> obtenerDatos(){
        return List.of(cliente, ruta, fechaSalida, horaSalida, asiento, responsable, precioTotal);
    };

}
