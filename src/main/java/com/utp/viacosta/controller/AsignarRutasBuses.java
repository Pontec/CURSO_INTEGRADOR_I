package com.utp.viacosta.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;


import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@Controller
public class AsignarRutasBuses implements Initializable {

    @FXML
    private Button btn_actulizar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private Button btn_guardar;

    @FXML
    private TableColumn<?, ?> columnApellido;

    @FXML
    private TableColumn<?, ?> columnDni;

    @FXML
    private TableColumn<?, ?> columnDni1;

    @FXML
    private TableColumn<?, ?> columnId;

    @FXML
    private TableColumn<?, ?> columnNombre;

    @FXML
    private DatePicker fechaHoraLlegada;

    @FXML
    private DatePicker fechaHoraSalida;

    @FXML
    private TableView<?> tabla_clientes;

    @FXML
    private TextField txtHoraLlegada;

    @FXML
    private TextField txtHoraSalida;

    @FXML
    void actFechaHoraSalida(ActionEvent event) {

    }

    @FXML
    void actfechaHoraLlegada(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void actGuardar(ActionEvent event) {

    }


}
