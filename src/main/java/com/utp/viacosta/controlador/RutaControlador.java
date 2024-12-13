package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.RutaModelo;
import com.utp.viacosta.servicio.RutaServicio;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class RutaControlador implements Initializable {

    @Autowired
    private RutaServicio rutaServicio;

    @FXML
    private Button btn_actualizar, btn_agregar, btnLimpiar;

    @FXML
    private TableColumn<RutaModelo, String> columnDuracion;
    @FXML
    private TableColumn<RutaModelo, String> columnId;
    @FXML
    private TableColumn<RutaModelo, String> columnOrigen;
    @FXML
    private TableColumn<RutaModelo, String> columnDestino;
    @FXML
    private TableColumn<RutaModelo, String> columnAcciones;
    @FXML
    private TableView<RutaModelo> tabla_rutas;
    private Image iconoEliminar;

    @FXML
    private TextField txt_destino, txt_duracion,txt_origen,txtBuscar;
    @FXML
    private Label error_origen;
    @FXML
    private Label error_destino;
    @FXML
    private Label error_duracion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iconoEliminar = new Image(getClass().getResourceAsStream("/img/eliminar.png"));
        txt_duracion.setPromptText("HH:mm");
        agregarValidaciones();
        listarRutas();
        tabla_rutas.getSelectionModel().selectedItemProperty().addListener((obs, anteriorSeleccion, nuevaSeleccion) -> {
            if (nuevaSeleccion != null) {
                seleccionarRuta();
                btn_actualizar.setVisible(true);
                btnLimpiar.setVisible(true);
                btn_agregar.setVisible(false);
            }
        });
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);
        btn_agregar.setVisible(true);
    }

    private void agregarValidaciones() {
        txt_origen.textProperty().addListener((observable, oldValue, newValue) -> {
            validarOrigenDestino(txt_origen, error_origen);
//            validarRutasDiferentes(txt_origen, txt_destino, error_origen);
        });
        txt_destino.textProperty().addListener((observable, oldValue, newValue) -> {
            validarOrigenDestino(txt_destino, error_destino);
//            validarRutasDiferentes(txt_origen, txt_destino, error_origen);
        });
        configureTimeField(txt_duracion, error_duracion);
    }

    private boolean validarOrigenDestino(TextField textField, Label errorLabel) {
        String text = textField.getText().toLowerCase();
        if (text.isEmpty()) {
            errorLabel.setText("El campo no puede estar vacío.");
            return false;
        }
        if (text.matches(".*\\d.*")) {
            errorLabel.setText("El campo no debe contener números.");
            return false;
        } else {
            errorLabel.setText("");
            textField.setStyle("");
            return true;
        }
    }

    private boolean validarRutasDiferentes(TextField origen, TextField destino) {
        if (origen.getText().toLowerCase().equals(destino.getText().toLowerCase())) {
            mostrarAlerta("Las rutas deben ser diferentes.");
            return false;
        } else {
            origen.setStyle("");
            destino.setStyle("");
            return true;
        }
    }

    private void configureTimeField(TextField textField, Label error_duracion) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Limitar a 5 caracteres (HH:mm)
            if (newValue.length() > 5) {
                textField.setText(oldValue);
                error_duracion.setText("Formato incorrecto.");
                return;
            }
            // Agregar ":" automáticamente en la posición correcta
            if (newValue.length() == 2 && !newValue.contains(":")) {
                textField.setText(newValue + ":");
            }
            // Asegurarse de que solo haya números y ":" en el formato correcto
            if (!newValue.matches("\\d{0,2}:?\\d{0,2}")) {
                textField.setText(oldValue);
                error_duracion.setText("Formato incorrecto.");
            }
            error_duracion.setText("");
        });
    }


    private boolean validarCampos() {
        boolean isValid = true;
        if (!validarOrigenDestino(txt_origen, error_origen)) {
            isValid = false;
        }
        if (!validarOrigenDestino(txt_destino, error_destino)) {
            isValid = false;
        }
        if (!validarRutasDiferentes(txt_origen, txt_destino)) {
            isValid = false;
        }
        if(!txt_duracion.getText().matches("\\d{2}:\\d{2}")){
            error_duracion.setText("Formato incorrecto.");
            isValid = false;
        }

        return isValid;
    }


    @FXML
    void act_guardar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        if (rutaServicio.rutaExiste(txt_origen.getText(), txt_destino.getText(), null)) {
            mostrarAlerta("La ruta ya existe.");
            return;
        }

        RutaModelo ruta = new RutaModelo();
        ruta.setOrigen(txt_origen.getText());
        ruta.setDestino(txt_destino.getText());
        ruta.setDuracion(txt_duracion.getText());

        rutaServicio.guardarRuta(ruta);
        listarRutas();
        limpiarCampos();
    }


    @FXML
    void act_actualizar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        RutaModelo ruta = tabla_rutas.getSelectionModel().getSelectedItem();
        if (rutaServicio.rutaExiste(txt_origen.getText(), txt_destino.getText(), ruta.getIdRuta())) {
            mostrarAlerta("La ruta ya existe.");
            return;
        }

        ruta.setOrigen(txt_origen.getText());
        ruta.setDestino(txt_destino.getText());
        ruta.setDuracion(txt_duracion.getText());

        rutaServicio.actualizarRuta(ruta);
        listarRutas();
        limpiarCampos();
    }

    private void listarRutas() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idRuta"));
        columnOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        columnDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        columnDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        columnAcciones.setCellFactory(param -> new TableCell<>() {
            ImageView icono = new ImageView(iconoEliminar);

            {
                icono.setFitHeight(20);
                icono.setFitWidth(20);
                setGraphic(icono);

                setOnMouseClicked(event -> {
                    RutaModelo ruta = tabla_rutas.getItems().get(getIndex());
                    rutaServicio.eliminarRuta(ruta.getIdRuta());
                    limpiarCampos();
                    activarGuardar();
                    listarRutas();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : icono); // Solo mostrar el ícono si la celda no está vacía
            }
        });
        tabla_rutas.getItems().setAll(rutaServicio.listarRutas());
    }

    @FXML
    void actLimpiar(ActionEvent event) {
        limpiarCampos();
        activarGuardar();
    }

    public void activarGuardar(){
        btn_agregar.setVisible(true);
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);
    }


    //Metodos de apoyo
    private void limpiarCampos(){
        txt_origen.setText("");
        txt_destino.setText("");
        txt_duracion.setText("");

        error_origen.setText("");
        error_destino.setText("");
        error_duracion.setText("");
    }

    @FXML
    private void seleccionarRuta(){
        RutaModelo ruta = tabla_rutas.getSelectionModel().getSelectedItem();
        txt_origen.setText(ruta.getOrigen());
        txt_destino.setText(ruta.getDestino());
        txt_duracion.setText(ruta.getDuracion());
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.show();
    }


    private void buscarRutas(String searchText) {
        List<RutaModelo> rutas = rutaServicio.buscarRutas(searchText);
        tabla_rutas.getItems().setAll(rutas);
    }

    @FXML
    public void handleBuscarRutas(Event event) {
        buscarRutas(txtBuscar.getText());
    }
}
