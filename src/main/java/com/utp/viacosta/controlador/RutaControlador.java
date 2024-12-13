package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.RutaModelo;
import com.utp.viacosta.servicio.RutaServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
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
    private TextField txt_destino, txt_duracion, txt_origen;
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
        configureTimeField(txt_duracion);
        listarRutas();
        agregarValidaciones();
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
        txt_duracion.textProperty().addListener((observable, oldValue, newValue) -> validarDuracion(txt_duracion, error_duracion));
    }

    private boolean validarDuracion(TextField textField, Label errorLabel) {
        String text = textField.getText();
        if (text.isEmpty()) {
            errorLabel.setText("El campo no puede estar vacío.");
            textField.setStyle("-fx-border-color: red;");
            return false;
        }
        if (!text.matches("\\d*")) {
            errorLabel.setText("Ingrese un número.");
            textField.setStyle("-fx-border-color: red;");
            return false;
        }
        int duracion = Integer.parseInt(text);
        if (duracion < 0 || duracion > 48) {
            errorLabel.setText("Entre 0 y 48 horas.");
            textField.setStyle("-fx-border-color: red;");
            return false;
        } else {
            errorLabel.setText("");
            textField.setStyle("");
            return true;
        }
    }

    private boolean validarOrigenDestino(TextField textField, Label errorLabel) {
        String text = textField.getText().toLowerCase();
        if (text.isEmpty()) {
            errorLabel.setText("El campo no puede estar vacío.");
            textField.setStyle("-fx-border-color: red;");
            return false;
        }
        if (text.matches(".*\\d.*")) {
            errorLabel.setText("El campo no debe contener números.");
            textField.setStyle("-fx-border-color: red;");
            return false;
        } else {
            errorLabel.setText("");
            textField.setStyle("");
            return true;
        }
    }

    private boolean validarRutasDiferentes(TextField origen, TextField destino, Label errorLabel) {
        if (origen.getText().toLowerCase().equals(destino.getText().toLowerCase())) {
            errorLabel.setText("El origen y el destino no pueden ser iguales.");
            origen.setStyle("-fx-border-color: red;");
            destino.setStyle("-fx-border-color: red;");
            return false;
        } else {
            errorLabel.setText("");
            origen.setStyle("");
            destino.setStyle("");
            return true;
        }
    }


    private boolean validarCampos() {
        boolean isValid = true;
        if (!validarOrigenDestino(txt_origen, error_origen)) {
            isValid = false;
        }
        if (!validarOrigenDestino(txt_destino, error_destino)) {
            isValid = false;
        }
        if (!validarDuracion(txt_duracion, error_duracion)) {
            isValid = false;
        }
//        if (!validarRutasDiferentes(txt_origen, txt_destino, error_origen)) {
//            isValid = false;
//        }
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
        ruta.setDuracion(txt_duracion.getText() + " horas");

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
        ruta.setDuracion(txt_duracion.getText() + " horas");

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

    public void activarGuardar() {
        btn_agregar.setVisible(true);
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);
    }


    //Metodos de apoyo
    private void limpiarCampos() {
        txt_origen.setText("");
        txt_destino.setText("");
        txt_duracion.setText("");

        error_origen.setText("");
        error_destino.setText("");
        error_duracion.setText("");

        txt_origen.setStyle("");
        txt_destino.setStyle("");
        txt_duracion.setStyle("");
    }

    @FXML
    private void seleccionarRuta() {
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

    private void configureTimeField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Limitar a 5 caracteres (HH:mm)
            if (newValue.length() > 5) {
                textField.setText(oldValue);
                return;
            }
            // Agregar ":" automáticamente en la posición correcta
            if (newValue.length() == 2 && !newValue.contains(":")) {
                textField.setText(newValue + ":");
            }
            // Asegurarse de que solo haya números y ":" en el formato correcto
            if (!newValue.matches("\\d{0,2}:?\\d{0,2}")) {
                textField.setText(oldValue);
            }
        });
    }

}
