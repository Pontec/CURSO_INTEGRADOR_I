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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iconoEliminar = new Image(getClass().getResourceAsStream("/img/eliminar.png"));
        txt_duracion.setPromptText("HH:mm");
        configureTimeField(txt_duracion);
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

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
        buscarRutas(newValue);
    });
    }
 
    @FXML
    void act_guardar(ActionEvent event) {
        if(validarRutas()){
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
    void act_actualizar(ActionEvent event) {
        if(validarRutas()){
            return;
        }

        RutaModelo ruta = tabla_rutas.getSelectionModel().getSelectedItem();
        ruta.setOrigen(txt_origen.getText());
        ruta.setDestino(txt_destino.getText());
        ruta.setDuracion(txt_duracion.getText());

        rutaServicio.actualizarRuta(ruta);
        listarRutas();
        limpiarCampos();
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
    }

    @FXML
    private void seleccionarRuta(){
        RutaModelo ruta = tabla_rutas.getSelectionModel().getSelectedItem();
        txt_origen.setText(ruta.getOrigen());
        txt_destino.setText(ruta.getDestino());
        txt_duracion.setText(ruta.getDuracion());
    }

    //Validar que el origen y destino no sean iguales


    //Validar que las rutas no sean vacias
    private boolean validarRutas(){
        if(txt_origen.getText().isEmpty() || txt_destino.getText().isEmpty()){
            mostrarAlerta("Los campos de origen y destino no pueden estar vacios");
            return true;
        }
        return false;
    }

    //Mostrar alerta
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


    private void buscarRutas(String searchText) {
        List<RutaModelo> rutas = rutaServicio.buscarRutas(searchText);
        tabla_rutas.getItems().setAll(rutas);
    }
}
