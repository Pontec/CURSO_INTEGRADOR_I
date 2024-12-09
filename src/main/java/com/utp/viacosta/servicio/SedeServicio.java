package com.utp.viacosta.servicio;


import com.utp.viacosta.modelo.SedeModelo;

import java.util.List;

public interface SedeServicio {
    SedeModelo guardarSede(SedeModelo sedeModelo);
    List<SedeModelo> listaSedes();

}
