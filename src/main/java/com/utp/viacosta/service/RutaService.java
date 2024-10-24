package com.utp.viacosta.service;


import com.utp.viacosta.model.RutaModel;

import java.util.List;

public interface RutaService {
    void guardarRuta(RutaModel ruta);
    List<RutaModel> listarRutas();
    void eliminarRuta(int idRuta);
    void actualizarRuta(RutaModel ruta);
}
