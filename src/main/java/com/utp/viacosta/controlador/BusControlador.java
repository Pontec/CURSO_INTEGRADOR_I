package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsientoEstadoFechaModelo;
import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.BusModelo;
import com.utp.viacosta.modelo.enums.Estado;
import com.utp.viacosta.modelo.enums.TipoAsiento;
import com.utp.viacosta.servicio.AsientoServicio;
import com.utp.viacosta.servicio.BusServicio;
import com.utp.viacosta.servicio.TipoAsientoServicio;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class BusControlador implements Initializable {

    @Autowired
    private BusServicio busServicio;
    @Autowired
    private TipoAsientoServicio tipoAsientoServicio;
    @Autowired
    private AsientoServicio asientoServicio;


    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_guardar;

    @FXML
    private TableColumn<BusModelo, String> columnPlaca;
    @FXML
    private TableColumn<BusModelo, String> columnMarca;
    @FXML
    private TableColumn<BusModelo, String> columnModelo;
    @FXML
    private TableColumn<BusModelo, String> columnAsientoTotal;
    @FXML
    private TableColumn<BusModelo, String> columnId;
    @FXML
    private TableColumn<BusModelo, String> columnCarga;
    @FXML
    private TableView<BusModelo> tablaBuses;

    @FXML
    private TextField txt_placa;
    @FXML
    private TextField txt_marca;
    @FXML
    private TextField txt_modelo;
    @FXML
    private TextField txt_carga_maxima;
    @FXML
    private TextField txtPiso2;
    @FXML
    private TextField txtPiso1;
    @FXML
    private Label error_placa;
    @FXML
    private Label error_primer;
    @FXML
    private Label error_modelo;
    @FXML
    private Label error_cap;
    @FXML
    private Label error_seg;
    @FXML
    private Label error_marca;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listarBuses();
        inicializarValidaciones();
        tablaBuses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarBus();
            }
        });
    }

    public void inicializarValidaciones() {
        agregarValidacionNoVacio(txt_placa, error_placa);
        agregarValidacionGuion(txt_placa, error_placa);
        agregarValidacionNoVacio(txt_marca, error_marca);
        agregarValidacionNoVacio(txt_modelo, error_modelo);
        agregarValidacionAsientos(txtPiso1, error_primer, 16);
        agregarValidacionAsientos(txtPiso2, error_seg, 40);
        agregarValidacionDecimales(txt_carga_maxima, error_cap, 70.00);
    }

    private void validarCampos(BusModelo currentBus) {
        boolean isValid = true;

        if (!txt_placa.getText().contains("-")) {
            error_placa.setText("La placa debe contener un guion.");
            isValid = false;
        } else {
            error_placa.setText("");
        }

        BusModelo busExistente = busServicio.findByPlaca(txt_placa.getText());
        if (busExistente != null && (currentBus == null || !busExistente.getIdBus().equals(currentBus.getIdBus()))) {
            error_placa.setText("La placa ya existe.");
            isValid = false;
        }

        if (txt_marca.getText().isEmpty()) {
            error_marca.setText("La marca es obligatoria.");
            isValid = false;
        } else {
            error_marca.setText("");
        }

        // Validar el modelo
        if (txt_modelo.getText().isEmpty()) {
            error_modelo.setText("El modelo es obligatorio.");
            isValid = false;
        } else {
            error_modelo.setText("");
        }

        if (txtPiso1.getText().isEmpty()) {
            error_primer.setText("Campo es obligatorio.");
            isValid = false;
        } else {
            int primerPiso = Integer.parseInt(txtPiso1.getText());
            if (primerPiso < 0 || primerPiso > 16) {
                error_primer.setText("Capacidad entre 0 y 16 asientos.");
                isValid = false;
            } else {
                error_primer.setText("");
            }
        }

        if (txtPiso2.getText().isEmpty()) {
            error_seg.setText("Campo es obligatorio.");
            isValid = false;
        } else {
            int segundoPiso = Integer.parseInt(txtPiso2.getText());
            if (segundoPiso < 0 || segundoPiso > 40) {
                error_seg.setText("Capacidad entre 0 y 40 asientos.");
                isValid = false;
            } else {
                error_seg.setText("");
            }
        }

        if (txt_carga_maxima.getText().isEmpty()) {
            error_cap.setText("Campo es obligatorio.");
            isValid = false;
        } else {
            double capacidadCarga = Double.parseDouble(txt_carga_maxima.getText());
            if (capacidadCarga < 0 || capacidadCarga > 70.00) {
                error_cap.setText("Capacidad entre 0 y 70.00 kg.");
                isValid = false;
            } else {
                error_cap.setText("");
            }
        }

        if (!isValid) {
            throw new IllegalArgumentException("Hay errores en los campos.");
        }
    }

private void agregarValidacionAsientos(TextField textField, Label errorLabel, int maxLimit) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            textField.setText(oldValue);
        } else {
            int value = newValue.isEmpty() ? 0 : Integer.parseInt(newValue);
            if (value <= maxLimit) {
                errorLabel.setText(""); // Clear the error message if valid
                textField.setStyle(""); // Reset the style
            } else {
                errorLabel.setText("Limite de asientos: " + maxLimit);
                textField.setStyle("-fx-border-color: red;");
            }
        }
    });
}

    private void agregarValidacionDecimales(TextField textField, Label errorLabel, double maxLimit) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?") || (newValue.contains(".") && newValue.split("\\.").length > 1 && newValue.split("\\.")[1].length() > 2)) {
                textField.setText(oldValue);
            }
            try {
                double value = Double.parseDouble(newValue);
                if (value <= maxLimit) {
                    errorLabel.setText("");
                    textField.setStyle("");
                } else {
                    errorLabel.setText("Limite de carga: " + maxLimit);
                    textField.setStyle("-fx-border-color: red;");
                }
            } catch (NumberFormatException e) {
                errorLabel.setText("Ingrese un número decimal.");
                textField.setStyle("-fx-border-color: red;");
            }
        });
    }

    private void agregarValidacionNoVacio(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                errorLabel.setText("Campo es obligatorio.");
                textField.setStyle("-fx-border-color: red;");
            } else {
                errorLabel.setText(""); // Clear the error message if valid
                textField.setStyle(""); // Reset the style
            }
        });
    }

    private void agregarValidacionGuion(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.contains("-")) {
                errorLabel.setText("El campo debe contener un guion.");
                textField.setStyle("-fx-border-color: red;");
            } else {
                errorLabel.setText(""); // Clear the error message if valid
                textField.setStyle(""); // Reset the style
            }
        });
    }


    @FXML
    void actGuardar(ActionEvent event) {
        try {
            validarCampos(null);
        } catch (IllegalArgumentException e) {
            return;
        }
        int capacidadAsiento = Integer.parseInt(txtPiso1.getText()) + Integer.parseInt(txtPiso2.getText());
        BusModelo busModelo = new BusModelo();
        busModelo.setPlaca(txt_placa.getText());
        busModelo.setMarca(txt_marca.getText());
        busModelo.setModelo(txt_modelo.getText());
        busModelo.setCapacidadAsientos(capacidadAsiento);
        busModelo.setPrimerPiso(Integer.parseInt(txtPiso1.getText()));
        busModelo.setSegundoPiso(Integer.parseInt(txtPiso2.getText()));
        busModelo.setCapacidadCarga(Double.parseDouble(txt_carga_maxima.getText()));
        BusModelo busGuardar = busServicio.save(busModelo);
        List<AsientoModelo> listaAsientos = new ArrayList<>();
        for (int i = 0; i < capacidadAsiento; i++) {
            AsientoModelo asientoModelo = new AsientoModelo();
            asientoModelo.setNumeroAsiento(i + 1);
            asientoModelo.setPrecio(0);
            asientoModelo.setIdTipoAsiento(i < Integer.parseInt(txtPiso2.getText()) ? 1 : 2);
            asientoModelo.setIdBus(busGuardar.getIdBus());

            AsientoEstadoFechaModelo estadoAsiento = new AsientoEstadoFechaModelo();
            estadoAsiento.setAsiento(asientoModelo);
            estadoAsiento.setFecha(LocalDate.now());
            estadoAsiento.setHora(LocalTime.now());
            estadoAsiento.setEstado(Estado.DISPONIBLE);
            listaAsientos.add(asientoModelo);
        }

        listaAsientos.forEach(asientoServicio::save);
        listarBuses();
        limpiarCampos();
    }

    private void listarBuses() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idBus"));
        columnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnAsientoTotal.setCellValueFactory(new PropertyValueFactory<>("capacidadAsientos"));
        columnCarga.setCellValueFactory(new PropertyValueFactory<>("capacidadCarga"));
        tablaBuses.getItems().setAll(busServicio.findAll());
    }

    @FXML
    void actActualizar(ActionEvent event) {
        BusModelo busModelo = tablaBuses.getSelectionModel().getSelectedItem();
        try {
            validarCampos(busModelo);
        } catch (IllegalArgumentException e) {
            return;
        }
        busModelo.setPlaca(txt_placa.getText());
        busModelo.setMarca(txt_marca.getText());
        busModelo.setModelo(txt_modelo.getText());
        busModelo.setCapacidadAsientos(Integer.parseInt(txtPiso1.getText()) + Integer.parseInt(txtPiso2.getText()));
        busModelo.setPrimerPiso(Integer.parseInt(txtPiso1.getText()));
        busModelo.setSegundoPiso(Integer.parseInt(txtPiso2.getText()));
        busModelo.setCapacidadCarga(Double.parseDouble(txt_carga_maxima.getText()));

        List<AsientoModelo> listaAsientos = asientoServicio.getAsientosPorBus(busModelo.getIdBus());
        int capacidadAsiento = Integer.parseInt(txtPiso1.getText()) + Integer.parseInt(txtPiso2.getText());
        if (capacidadAsiento > listaAsientos.size()) {
            for (int i = listaAsientos.size(); i < capacidadAsiento; i++) {
                AsientoModelo asientoModelo = new AsientoModelo();
                asientoModelo.setNumeroAsiento(i + 1);
                asientoModelo.setPrecio(0);
                asientoModelo.setIdTipoAsiento(i < Integer.parseInt(txtPiso2.getText()) ? 1 : 2);
                asientoModelo.setIdBus(busModelo.getIdBus());

                asientoModelo.setEstadosFecha(new ArrayList<>());

                AsientoEstadoFechaModelo estadoAsiento = new AsientoEstadoFechaModelo();
                estadoAsiento.setAsiento(asientoModelo);
                estadoAsiento.setFecha(LocalDate.now());
                estadoAsiento.setHora(LocalTime.now());
                estadoAsiento.setEstado(Estado.DISPONIBLE);

                asientoModelo.getEstadosFecha().add(estadoAsiento);
                asientoServicio.save(asientoModelo);
            }
        } else if (capacidadAsiento < listaAsientos.size()) {
            for (int i = listaAsientos.size() - 1; i >= capacidadAsiento; i--) {
                asientoServicio.deleteById(listaAsientos.get(i).getIdAsiento());
            }
        }

        busServicio.update(busModelo);
        listarBuses();
        limpiarCampos();
    }

    @FXML
    public void seleccionarBus() {
        BusModelo busModelo = tablaBuses.getSelectionModel().getSelectedItem();

        txt_placa.setText(busModelo.getPlaca());
        txt_marca.setText(busModelo.getMarca());
        txt_modelo.setText(busModelo.getModelo());

        txtPiso1.setText(String.valueOf(busModelo.getPrimerPiso()));
        txtPiso2.setText(String.valueOf(busModelo.getSegundoPiso()));

        txt_carga_maxima.setText(String.valueOf(busModelo.getCapacidadCarga()));
    }

    @FXML
    void actEliminar(ActionEvent event) {
        BusModelo busModelo = tablaBuses.getSelectionModel().getSelectedItem();
        busServicio.deleteById(busModelo.getIdBus());
        listarBuses();
    }

    private void limpiarCampos() {
        txt_placa.setText("");
        txt_marca.setText("");
        txt_modelo.setText("");
        txtPiso2.setText("");
        txtPiso1.setText("");
        txt_carga_maxima.setText("");

        error_placa.setText("");
        error_primer.setText("");
        error_seg.setText("");
        error_modelo.setText("");
        error_cap.setText("");
        error_marca.setText("");

        txt_placa.setStyle("");
        txt_marca.setStyle("");
        txt_modelo.setStyle("");
        txtPiso1.setStyle("");
        txtPiso2.setStyle("");
        txt_carga_maxima.setStyle("");
    }

    @Deprecated
    public void btnGestionAsiento(ActionEvent event) throws IOException {
        Parent fxmlLoader = FxmlCargarUtil.load("/vista/AsientoVista.fxml");
        Stage stage = new Stage();
        stage.setTitle("Gestión de Asientos");
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Deprecated
    void btnAsignacionAsiento(ActionEvent event) throws IOException {
        Parent fxmlLoader = FxmlCargarUtil.load("/vista/AsignacionRutasVista.fxml");
        Stage stage = new Stage();
        stage.setTitle("Asignacion de Asientos");
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


}
