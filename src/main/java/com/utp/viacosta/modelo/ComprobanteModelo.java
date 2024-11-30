package com.utp.viacosta.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comprobantes")
public class ComprobanteModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Integer idComprobante;
    @Column (name = "tipo_comprobante")
    private String tipoComprobante;
    @Column (name = "numero_comprobante")
    private String numeroComprobante;
    @Column (name = "fecha_emision")
    private LocalDate fechaEmision;

    @OneToMany(mappedBy = "comprobante")
    private List<DetalleEncomiendaModelo> listDetalleEncomienda;

    @OneToMany(mappedBy = "comprobante")
    private List<DetalleBoletaModelo> listDetalleBoleta;


}
