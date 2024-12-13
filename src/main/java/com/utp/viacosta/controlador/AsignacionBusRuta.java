package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.BusModelo;
import com.utp.viacosta.modelo.RutaModelo;
import com.utp.viacosta.modelo.enums.EstadoAsignacion;
import com.utp.viacosta.servicio.AsignacionBusRutaServicio;
import com.utp.viacosta.servicio.BusServicio;
import com.utp.viacosta.servicio.RutaServicio;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.net.URL;
import java.time.LocalDate;
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
    private Button btn_guardar;

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
    @FXML
    private TextField txtPrecio;
    @FXML
    private TableColumn<AsignacionBusRutaModelo, String> columnPrecio;
    @FXML
    private Label err_hora;
    @FXML
    private Label err_bus;
    @FXML
    private Label err_fecha;
    @FXML
    private Label err_precio;
    @FXML
    private Label err_ruta;
    @FXML
    private TextField txtBuscar;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private Button btn_limpiar;
    @FXML
    private Button btn_actualizar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtHoraSalida.setPromptText("HH:mm");
        listarAsignaciones();
        fechaPredefinida();
        cargarBuses();
        cargarRutas();
        inicializarValidaciones();
        tablaBusesRutas.getSelectionModel().selectedItemProperty().addListener((obs, anteriorSeleccion, nuevaSeleccion) -> {
            if (nuevaSeleccion != null) {
                seleccionarAsignacion();
                btn_actualizar.setVisible(true);
                btn_limpiar.setVisible(true);
                btn_guardar.setVisible(false);
            }
        });
        seleccionarAsignacion();
        btn_guardar.setVisible(true);
        btn_actualizar.setVisible(false);
        btn_limpiar.setVisible(false);
    }

    private void inicializarValidaciones() {
        agregarValidacionComboBox(cmbBus, err_bus);
        agregarValidacionComboBox(cmbRuta, err_ruta);
        agregarValidacionDate(fechaHoraSalida, err_fecha);
        configureTimeField(txtHoraSalida, err_hora);
        agregarValidacionPrecio(txtPrecio, err_precio);
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> buscarAsignaciones());
        fechaInicio.valueProperty().addListener((observable, oldValue, newValue) -> buscarAsignaciones());
        fechaFin.valueProperty().addListener((observable, oldValue, newValue) -> buscarAsignaciones());
    }

private void agregarValidacionComboBox(ComboBox comboBox, Label errorLabel) {
    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue == null || newValue.toString().isEmpty()) {
            errorLabel.setText("Seleccione una opción.");
        } else {
            errorLabel.setText("");
            comboBox.setStyle("");
        }
    });
}

private void agregarValidacionDate(DatePicker fecha, Label errorLabel) {
    fecha.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue == null || newValue.toString().isEmpty()) {
            errorLabel.setText("Seleccione una fecha.");
        } else {
            errorLabel.setText("");
            fecha.setStyle("");
        }
    });
}

private void agregarValidacionPrecio(TextField precio, Label errorLabel) {
    precio.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue == null || newValue.isEmpty()) {
            errorLabel.setText("Ingrese un precio.");
        } else if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
            errorLabel.setText("Ingrese solo números y un punto decimal.");
            precio.setText(oldValue);
        } else {
            try {
                double value = Double.parseDouble(newValue);
                if (value < 0 || value > 200.00) {
                    errorLabel.setText("El precio debe ser entre 0 y 200.00.");
                } else {
                    errorLabel.setText("");
                    precio.setStyle("");
                }
            } catch (NumberFormatException e) {
                errorLabel.setText("Formato de precio incorrecto.");
                precio.setText(oldValue);
            }
        }
    });
}

private void configureTimeField(TextField textField, Label err_hora) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue == null || newValue.isEmpty()) {
            err_hora.setText("Ingrese una hora.");
            return;
        }
        // Limitar a 5 caracteres (HH:mm)
        if (newValue.length() > 5) {
            textField.setText(oldValue);
            err_hora.setText("Formato incorrecto.");
            return;
        }
        // Agregar ":" automáticamente en la posición correcta
        if (newValue.length() == 2 && !newValue.contains(":")) {
            textField.setText(newValue + ":");
        }
        // Asegurarse de que solo haya números y ":" en el formato correcto
        if (!newValue.matches("\\d{0,2}:?\\d{0,2}")) {
            textField.setText(oldValue);
            err_hora.setText("Formato incorrecto.");
        } else {
            err_hora.setText("");
            textField.setStyle("");
        }
    });
}

    private boolean esFormularioValido() {
        boolean valido = true;

        if (cmbBus.getValue() == null) {
            err_bus.setText("Seleccione un bus.");
            valido = false;
        }

        if (cmbRuta.getValue() == null) {
            err_ruta.setText("Seleccione una ruta.");
            valido = false;
        }

        if (fechaHoraSalida.getValue() == null) {
            err_fecha.setText("Seleccione una fecha.");
            valido = false;
        }

        String horaSalida = txtHoraSalida.getText();
        if (horaSalida == null || horaSalida.isEmpty() || !horaSalida.matches("\\d{2}:\\d{2}")) {
            err_hora.setText("Ingrese una hora válida (HH:mm).");
            valido = false;
        }

        String precioText = txtPrecio.getText();
        if (precioText == null || precioText.isEmpty() || !precioText.matches("\\d*(\\.\\d{0,2})?")) {
            err_precio.setText("Ingrese un precio válido.");
            valido = false;
        } else {
            double precio = Double.parseDouble(precioText);
            if (precio < 0 || precio > 200) {
                err_precio.setText("El precio debe estar entre 0 y 200.");
                valido = false;
            }
        }

        return valido;
    }


    @FXML
    void actGuardar(ActionEvent event) {
        if (esFormularioValido()) {
            AsignacionBusRutaModelo asignacionBusRutaModelo = new AsignacionBusRutaModelo();
            asignacionBusRutaModelo.setIdBus(cmbBus.getValue().getIdBus());
            asignacionBusRutaModelo.setIdRuta(cmbRuta.getValue().getIdRuta());
            asignacionBusRutaModelo.setFechaSalida(fechaHoraSalida.getValue());
            asignacionBusRutaModelo.setHoraSalida(LocalTime.parse(txtHoraSalida.getText()));
            asignacionBusRutaModelo.setPrecio(Double.parseDouble(txtPrecio.getText()));
            asignacionBusRutaModelo.setEstado(EstadoAsignacion.PROGRAMADO);

            asignacionBusRutaService.save(asignacionBusRutaModelo);
            limpiarCampos();
            listarAsignaciones();
        }
    }

    @FXML
    public void actActualizar(ActionEvent event) {
        if (esFormularioValido()) {
            AsignacionBusRutaModelo asignacionBusRutaModelo = tablaBusesRutas.getSelectionModel().getSelectedItem();
            asignacionBusRutaModelo.setIdBus(cmbBus.getValue().getIdBus());
            asignacionBusRutaModelo.setIdRuta(cmbRuta.getValue().getIdRuta());
            asignacionBusRutaModelo.setFechaSalida(fechaHoraSalida.getValue());
            asignacionBusRutaModelo.setHoraSalida(LocalTime.parse(txtHoraSalida.getText()));
            asignacionBusRutaModelo.setPrecio(Double.parseDouble(txtPrecio.getText()));

            asignacionBusRutaService.save(asignacionBusRutaModelo);
            limpiarCampos();
            listarAsignaciones();
        }
    }

    //cargando los buses en el combobox
    public void cargarBuses() {
        cmbBus.getItems().setAll(busServicio.findAll());
    }

    //cargando las rutas en el combobox
    public void cargarRutas() {
        cmbRuta.getItems().setAll(rutaServicio.listarRutas());
    }

    //idBus, IdRuta
    public void bus() {
        List<AsignacionBusRutaModelo> asignacionBusRutaModelo = asignacionBusRutaService.findAll();
        asignacionBusRutaModelo.stream().map(AsignacionBusRutaModelo::getIdBus).forEach(System.out::println);

    }

    //llenando la tabla con los datos de la base de datos
    public void listarAsignaciones() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idAsignacion"));
        columnRuta.setCellValueFactory(new PropertyValueFactory<>("rutaAsignada"));
        columnBus.setCellValueFactory(new PropertyValueFactory<>("busAsignado"));
        columnFechaSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        columnHoraSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tablaBusesRutas.getItems().setAll(asignacionBusRutaService.findAll());
    }

    public void seleccionarAsignacion() {
        AsignacionBusRutaModelo asignacionBusRutaModelo = tablaBusesRutas.getSelectionModel().getSelectedItem();
        if (asignacionBusRutaModelo != null) {
            cmbBus.setValue(busServicio.findById(asignacionBusRutaModelo.getIdBus()).get());
            cmbRuta.setValue(rutaServicio.buscarPorId(asignacionBusRutaModelo.getIdRuta()).get());
            fechaHoraSalida.setValue(asignacionBusRutaModelo.getFechaSalida());
            txtHoraSalida.setText(asignacionBusRutaModelo.getHoraSalida().toString());
            txtPrecio.setText(String.valueOf(asignacionBusRutaModelo.getPrecio()));
        }
    }

    public void limpiarCampos() {
        cmbBus.setValue(null);
        cmbRuta.setValue(null);
        fechaHoraSalida.setValue(null);
        txtHoraSalida.clear();
        txtPrecio.clear();

        err_bus.setText("");
        err_ruta.setText("");
        err_fecha.setText("");
        err_hora.setText("");
        err_precio.setText("");

        cmbBus.setStyle("");
        cmbRuta.setStyle("");
        fechaHoraSalida.setStyle("");
        txtHoraSalida.setStyle("");
        txtPrecio.setStyle("");

        btn_guardar.setVisible(true);
        btn_actualizar.setVisible(false);
        btn_limpiar.setVisible(false);
    }


    private void buscarAsignaciones() {
        String searchText = txtBuscar.getText();
        LocalDate fechaIni = fechaInicio.getValue();
        LocalDate fechaFinal = fechaFin.getValue();
        List<AsignacionBusRutaModelo> resultados = asignacionBusRutaService.buscarAsignaciones(
                searchText, fechaIni, fechaFinal);
        tablaBusesRutas.getItems().setAll(resultados);
    }

    private void fechaPredefinida() {
        if (fechaHoraSalida != null) {
            fechaHoraSalida.setValue(LocalDate.now());
            fechaHoraSalida.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                        setStyle("-fx-background-color: #cccccc;");
                    }
                }
            });
        }
    }

    @FXML
    private void limpiarFiltros() {
        txtBuscar.clear();
        fechaInicio.setValue(null);
        fechaFin.setValue(null);
        listarAsignaciones();

    }

    @FXML
    public void actLimpiar(ActionEvent actionEvent) {
        limpiarCampos();
    }

    @FXML
    public void btnRefresh(Event event) {
        txtBuscar.clear();
        fechaInicio.setValue(null);
        fechaFin.setValue(null);
        listarAsignaciones();
    }
}
