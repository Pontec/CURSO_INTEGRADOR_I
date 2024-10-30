package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.BusModelo;
import com.utp.viacosta.modelo.RutaModelo;
import com.utp.viacosta.servicio.AsignacionBusRutaServicio;
import com.utp.viacosta.servicio.BusServicio;
import com.utp.viacosta.servicio.RutaServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class AsignacionBusRuta implements Initializable {

    @Autowired
    private AsignacionBusRutaServicio asignacionBusRutaService;
    @Autowired
    private BusServicio busServicio;
    @Autowired
    private RutaServicio rutaServicio;


    @FXML
    private Button btn_actulizar, btn_eliminar, btn_guardar;

    @FXML
    private ComboBox<BusModelo> cmbBus;
    @FXML
    private ComboBox<RutaModelo> cmbRuta;
    @FXML
    private DatePicker fechaHoraSalida;
    @FXML
    private TextField txtHoraSalida;

    @FXML
    private TableColumn<AsignacionBusRutaModelo, String> columnId;
    @FXML
    private TableColumn<AsignacionBusRutaModelo, String> columnRuta;
    @FXML
    private TableColumn<AsignacionBusRutaModelo, String> columnBus;
    @FXML
    private TableColumn<AsignacionBusRutaModelo, String> columnFechaSalida;
    @FXML
    private TableColumn<AsignacionBusRutaModelo, String> columnHoraSalida;
    @FXML
    private TableView<AsignacionBusRutaModelo> tablaBusesRutas;

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
        AsignacionBusRutaModelo asignacionBusRutaModelo = new AsignacionBusRutaModelo();
        asignacionBusRutaModelo.setIdBus(cmbBus.getValue().getIdBus());
        asignacionBusRutaModelo.setIdRuta(cmbRuta.getValue().getIdRuta());
        asignacionBusRutaModelo.setFechaSalida(fechaHoraSalida.getValue());
        asignacionBusRutaModelo.setHoraSalida(LocalTime.parse(txtHoraSalida.getText()));

        asignacionBusRutaService.save(asignacionBusRutaModelo);
        limpiarCampos();
        listarAsignaciones();
    }

    //cargando los buses en el combobox
    public void cargarBuses(){
        cmbBus.getItems().setAll(busServicio.findAll());
    }

    //cargando las rutas en el combobox
    public void cargarRutas(){
        cmbRuta.getItems().setAll(rutaServicio.listarRutas());
    }

    //idBus, IdRuta
    public void bus() {
        List<AsignacionBusRutaModelo> asignacionBusRutaModelo = asignacionBusRutaService.findAll();
        asignacionBusRutaModelo.stream().map(AsignacionBusRutaModelo::getIdBus).forEach(System.out::println);

    }

    //llenando la tabla con los datos de la base de datos
    public void listarAsignaciones(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idAsignacion"));
        columnRuta.setCellValueFactory(new PropertyValueFactory<>("rutaAsignada"));
        columnBus.setCellValueFactory(new PropertyValueFactory<>("busAsignado"));
        columnFechaSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        columnHoraSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        tablaBusesRutas.getItems().setAll(asignacionBusRutaService.findAll());
    }

    //Metodo para actualizar
    @FXML
    public void actActualizar(ActionEvent event) {
        AsignacionBusRutaModelo asignacionBusRutaModelo = tablaBusesRutas.getSelectionModel().getSelectedItem();
        asignacionBusRutaModelo.setIdBus(cmbBus.getValue().getIdBus());
        asignacionBusRutaModelo.setIdRuta(cmbRuta.getValue().getIdRuta());
        asignacionBusRutaModelo.setFechaSalida(fechaHoraSalida.getValue());
        asignacionBusRutaModelo.setHoraSalida(LocalTime.parse(txtHoraSalida.getText()));

        asignacionBusRutaService.save(asignacionBusRutaModelo);
        limpiarCampos();
        listarAsignaciones();
    }


    //Metodos de Apoyo

    //Metodo para limipar campos
    public void limpiarCampos(){
        cmbBus.setValue(null);
        cmbRuta.setValue(null);
        fechaHoraSalida.setValue(null);
        txtHoraSalida.clear();
    }

}
