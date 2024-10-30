package com.utp.viacosta.controller;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;
import com.utp.viacosta.model.DetalleBoletaModel;
import com.utp.viacosta.service.AsignacionBusRutaService;
import com.utp.viacosta.service.DetalleBoletaService;
import com.utp.viacosta.util.FxmlCargarUtil;
import com.utp.viacosta.util.FxmlReportes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ReportesController implements Initializable {

    @FXML
    private ComboBox<String> cmbFiltro;
    @FXML
    private DatePicker dateInicio;
    @FXML
    private DatePicker dateFin;
    @FXML
    private TableColumn columNombre, columRuta, columAsiento, columPrecio, columHora, columResponsable, columFecha;
    @FXML
    private TableView tableReporteVentas;
    @FXML
    private TextField txtCliente, txtEmpleado;
    @FXML
    private Button btnFiltrar;

    ObservableList<String> listaFiltro = FXCollections.observableArrayList("Rango de Fechas", "Cliente", "Responsable");
    List<DetalleBoletaDTO> tablaReportes;

    @Autowired
    private DetalleBoletaService boletaService;
    @Autowired
    private AsignacionBusRutaService asignacionBusRutaService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarReportes();
        listarFiltros();
    }

    @FXML
    public void listarFiltros() {
        FxmlCargarUtil.cargarComboBox(listaFiltro, cmbFiltro);
    }

    @FXML
    public void filterForDate(ActionEvent actionEvent) {
        if (dateInicio.getValue() == null || dateFin.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar una fecha de inicio y una fecha de fin");
            alert.showAndWait();
            return;
        }
        System.out.println("Filtrando por fecha");
        System.out.println(dateInicio.getValue());
        System.out.println(dateFin.getValue());
        tablaReportes = boletaService.getAllDetalleBoletasForDate(dateInicio.getValue(), dateFin.getValue());
        tableReporteVentas.getItems().setAll(tablaReportes);
        setReportes(tablaReportes);
    }


    @FXML
    public void mostrarCampos() {
        dateInicio.setVisible(false);
        dateFin.setVisible(false);
        dateInicio.setEditable(false);
        dateFin.setEditable(false);
        txtCliente.setVisible(false);
        txtEmpleado.setVisible(false);
        btnFiltrar.setVisible(false);

        switch (cmbFiltro.getValue()) {
            case "Rango de Fechas":
                dateInicio.setVisible(true);
                dateFin.setVisible(true);
                btnFiltrar.setVisible(true);
                break;
            case "Cliente":
                txtCliente.setVisible(true);
                break;
            case "Responsable":
                txtEmpleado.setVisible(true);
                break;
        }
    }

    @FXML
    private void exportarExcel(ActionEvent event) {
        new FxmlReportes().generarReportes(obtenerReportes());
    }


    @FXML
    public void handleKeyReleasedEmployee(Event event) {
        actualizarTabla(txtEmpleado.getText(), true);
    }

    @FXML
    public void handleKeyReleasedClient(Event event) {
        actualizarTabla(txtCliente.getText(), false);
    }

    private void listarReportes() {
        columNombre.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        columRuta.setCellValueFactory(new PropertyValueFactory<>("ruta"));
        columFecha.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        columHora.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        columAsiento.setCellValueFactory(new PropertyValueFactory<>("asiento"));
        columResponsable.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        columPrecio.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        tableReporteVentas.getItems().setAll(boletaService.getAllReporteVentas());
    }

    private void actualizarTabla(String text, boolean isEmployee) {
        tablaReportes = isEmployee ? boletaService.getAllDetalleBoletasForEmployee(text) : boletaService.getAllDetalleBoletasForClient(text);
        tableReporteVentas.getItems().setAll(tablaReportes);
        setReportes(tablaReportes);
    }

    private List<DetalleBoletaDTO> obtenerReportes() {
        return tablaReportes;
    }

    private void setReportes(List<DetalleBoletaDTO> table) {
        tablaReportes = table;
    }


}
