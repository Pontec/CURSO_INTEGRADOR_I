package com.utp.viacosta.controller;

import com.utp.viacosta.model.SedeModel;
import com.utp.viacosta.service.ClienteService;
import com.utp.viacosta.service.SedeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SedeControlador implements Initializable {

    @Autowired
    private SedeService sedeService;

    @FXML
    private TextField confTelefono;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnLogo;

    @FXML
    private TextField confCiudad;

    @FXML
    private TextField cofDescripcion;

    @FXML
    private TextField confDepartamento;

    @FXML
    private TextField confDireccion;

    @FXML
    private TextField confNombre;

    @FXML
    private TextField confPais;

    @FXML
    private TextField confRuc;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    @FXML
    void btnActualizar(ActionEvent event) {
        SedeModel sedeModel = new SedeModel();
        sedeModel.setNombreSedes(confNombre.getText());
        sedeModel.setRuc(confRuc.getText());
        sedeModel.setDireccion(confDireccion.getText());
        sedeModel.setTelefono(confTelefono.getText());
        sedeModel.setDepartamento(confDepartamento.getText());
        sedeModel.setCiudad(confCiudad.getText());
        sedeModel.setPais(confPais.getText());


        sedeService.guardarSede(sedeModel);
        clear();git
    }
    //metodo de limpiar
    @FXML
    public void clear(){
        confNombre.setText("");
        confRuc.setText("");
        confDireccion.setText("");
        confTelefono.setText("");
        confDepartamento.setText("");
        confCiudad.setText("");
        confPais.setText("");

    }
}

