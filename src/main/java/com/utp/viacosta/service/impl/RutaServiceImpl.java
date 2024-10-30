package com.utp.viacosta.service.impl;

import com.utp.viacosta.dao.RutaRepository;
import com.utp.viacosta.model.RutaModel;
import com.utp.viacosta.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutaServiceImpl implements RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Override
    public Optional<RutaModel> buscarPorId(int idRuta){
        return rutaRepository.findById(idRuta);
    }

    @Override
    public void guardarRuta(RutaModel rutaModel) {
        rutaRepository.save(rutaModel);
    }

    @Override
    public List<RutaModel> listarRutas() {
        return rutaRepository.findAll();
    }

    @Override
    public void eliminarRuta(int idRuta) {
        Optional<RutaModel> ruta = rutaRepository.findById(idRuta);
        if(ruta.isPresent()){
            rutaRepository.deleteById(idRuta);
        } else {
            throw new RuntimeException("No se encontro la ruta con el id: "+idRuta);
        }
    }

    @Override
    public void actualizarRuta(RutaModel ruta) {
        Optional<RutaModel> rutaModel = rutaRepository.findById(ruta.getIdRuta());
        if (rutaModel.isPresent()) {
            rutaRepository.save(ruta);
        } else {
            throw new RuntimeException("No se encontro la ruta con el id: " + ruta.getIdRuta());
        }
    }


}
