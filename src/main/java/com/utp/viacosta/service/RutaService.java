package com.utp.viacosta.service;


import com.utp.viacosta.model.RutaModel;

import java.util.List;
import java.util.Optional;

public interface RutaService {

    Optional<RutaModel> buscarPorId(int idRuta);
    void guardarRuta(RutaModel ruta);
    List<RutaModel> listarRutas();
    void eliminarRuta(int idRuta);
    void actualizarRuta(RutaModel ruta);
}
