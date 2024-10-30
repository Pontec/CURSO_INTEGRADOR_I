package com.utp.viacosta.service;


import com.utp.viacosta.model.SedeModel;

import java.util.List;

public interface SedeService {
    SedeModel guardarSede(SedeModel sedeModel);
    List<SedeModel> listaSedes();


}
