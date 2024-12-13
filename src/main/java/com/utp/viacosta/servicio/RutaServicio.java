package com.utp.viacosta.servicio;


import com.utp.viacosta.modelo.RutaModelo;

import java.util.List;
import java.util.Optional;

public interface RutaServicio {

    Optional<RutaModelo> buscarPorId(int idRuta);
    void guardarRuta(RutaModelo ruta);
    List<RutaModelo> listarRutas();
    void eliminarRuta(int idRuta);
    void actualizarRuta(RutaModelo ruta);
    boolean existsByOrigen(String origen);
    List<Object[]> obtenerRutasMasVendidas();
    public boolean rutaExiste(String origen, String destino, Integer excludeId);
}
