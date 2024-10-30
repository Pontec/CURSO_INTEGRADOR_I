package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;
import com.utp.viacosta.servicio.AsignacionBusRutaServicio;
import com.utp.viacosta.servicio.DetalleBoletaServicio;
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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ReporteControlador implements Initializable {

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
    private DetalleBoletaServicio boletaService;
    @Autowired
    private AsignacionBusRutaServicio asignacionBusRutaService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarReportes();
        listarFiltros();
        configurarDatePickers();
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
        establecerReportes(tablaReportes);
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
        if (!obtenerReportes().isEmpty()){
            new FxmlReportes().generarReportes(obtenerReportes());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No hay datos para exportar");
            alert.showAndWait();
        }

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
        establecerReportes(boletaService.getAllReporteVentas());
    }

    private void actualizarTabla(String text, boolean isEmployee) {
        tablaReportes = isEmployee ? boletaService.getAllDetalleBoletasForEmployee(text) : boletaService.getAllDetalleBoletasForClient(text);
        tableReporteVentas.getItems().setAll(tablaReportes);
        establecerReportes(tablaReportes);
    }

    private List<DetalleBoletaDTO> obtenerReportes() {
        return tablaReportes;
    }

    private void establecerReportes(List<DetalleBoletaDTO> table) {
        tablaReportes = table;
    }

    private void configurarDatePickers() {
        dateInicio.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizarDatePickerFin(newValue);
        });
        actualizarDatePickerFin(dateInicio.getValue());
    }

    private void actualizarDatePickerFin(LocalDate fechaInicio) {
        dateFin.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(fechaInicio)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #cccccc;");
                }
            }
        });
    }


    @FXML
    public void limpiarReportes(ActionEvent actionEvent) {
        txtCliente.setText("");
        txtEmpleado.setText("");
        dateInicio.setValue(null);
        dateFin.setValue(null);
        listarReportes();
    }
}
