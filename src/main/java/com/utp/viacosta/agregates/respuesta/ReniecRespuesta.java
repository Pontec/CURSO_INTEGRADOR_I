package com.utp.viacosta.agregates.respuesta;

import lombok.Data;

@Data
public class ReniecRespuesta {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;
}
