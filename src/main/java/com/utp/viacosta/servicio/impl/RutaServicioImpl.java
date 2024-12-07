package com.utp.viacosta.servicio.impl;

import com.utp.viacosta.dao.RutaDAO;
import com.utp.viacosta.modelo.RutaModelo;
import com.utp.viacosta.servicio.RutaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutaServicioImpl implements RutaServicio {

    @Autowired
    private RutaDAO rutaDAO;

    @Override
    public Optional<RutaModelo> buscarPorId(int idRuta){
        return rutaDAO.findById(idRuta);
    }

    @Override
    public void guardarRuta(RutaModelo rutaModelo) {
        rutaDAO.save(rutaModelo);
    }

    @Override
    public List<RutaModelo> listarRutas() {
        return rutaDAO.findAll();
    }

    @Override
    public void eliminarRuta(int idRuta) {
        Optional<RutaModelo> ruta = rutaDAO.findById(idRuta);
        if(ruta.isPresent()){
            rutaDAO.deleteById(idRuta);
        } else {
            throw new RuntimeException("No se encontro la ruta con el id: "+idRuta);
        }
    }

    @Override
    public void actualizarRuta(RutaModelo ruta) {
        Optional<RutaModelo> rutaModel = rutaDAO.findById(ruta.getIdRuta());
        if (rutaModel.isPresent()) {
            rutaDAO.save(ruta);
        } else {
            throw new RuntimeException("No se encontro la ruta con el id: " + ruta.getIdRuta());
        }
    }

    @Override
    public boolean existsByOrigen(String origen) {
        return rutaDAO.existsByOrigen(origen);
    }

    @Override
    public List<Object[]> obtenerRutasMasVendidas() {
        return rutaDAO.findTopRutas();
    }


}
