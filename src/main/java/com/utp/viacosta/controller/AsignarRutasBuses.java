package com.utp.viacosta.controller;

import com.utp.viacosta.model.AsignacionBusRutaModel;
import com.utp.viacosta.model.BusModel;
import com.utp.viacosta.model.RutaModel;
import com.utp.viacosta.service.AsignacionBusRutaService;
import com.utp.viacosta.service.BusService;
import com.utp.viacosta.service.RutaService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AsignacionBusRutaService asignacionBusRutaService;
    @Autowired
    private BusService busService;
    @Autowired
    private RutaService rutaService;


    @FXML
    private Button btn_actulizar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private Button btn_guardar;

    @FXML
    private ComboBox<BusModel> cmbBus;

    @FXML
    private ComboBox<RutaModel> cmbRuta;

    @FXML
    private TableColumn<AsignacionBusRutaModel, String> columnId;
    @FXML
    private TableColumn<AsignacionBusRutaModel, String> columnRuta;
    @FXML
    private TableColumn<AsignacionBusRutaModel, String> columnBus;
    @FXML
    private TableColumn<AsignacionBusRutaModel, String> columnFechaSalida;
    @FXML
    private TableColumn<AsignacionBusRutaModel, String> columnHoraSalida;
    @FXML
    private TableView<AsignacionBusRutaModel> tablaBusesRutas;

    @FXML
    private DatePicker fechaHoraSalida;

    @FXML
    private TextField txtHoraSalida;

    @Deprecated
    void actFechaHoraSalida(ActionEvent event) {

    }

    @Deprecated
    void actfechaHoraLlegada(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarAsignaciones();
        cargarBuses();
        cargarRutas();
    }

    @FXML
    void actGuardar(ActionEvent event) {

    }

    //cargando los buses en el combobox
    public void cargarBuses(){
        cmbBus.getItems().setAll(busService.findAll());
    }

    //cargando las rutas en el combobox
    public void cargarRutas(){
        cmbRuta.getItems().setAll(rutaService.listarRutas());
    }

    //llenando la tabla con los datos de la base de datos
    public void listarAsignaciones(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idAsignacion"));
        columnRuta.setCellValueFactory(new PropertyValueFactory<>("idRuta"));
        columnBus.setCellValueFactory(new PropertyValueFactory<>("idBus"));
        columnFechaSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        columnHoraSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        tablaBusesRutas.getItems().setAll(asignacionBusRutaService.findAll());
    }



}
