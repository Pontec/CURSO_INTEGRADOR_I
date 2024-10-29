package com.utp.viacosta.controller;

import com.utp.viacosta.model.RutaModel;
import com.utp.viacosta.service.RutaService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class RutasControlador implements Initializable {

    @Autowired
    private RutaService rutaService;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private TableColumn<RutaModel, String> columnDuracion;
    @FXML
    private TableColumn<RutaModel, String> columnId;
    @FXML
    private TableColumn<RutaModel, String> columnOrigen;
    @FXML
    private TableColumn<RutaModel, String> columnDestino;
    @FXML
    private TableView<RutaModel> tabla_rutas;

    @FXML
    private TextField txt_destino, txt_duracion, txt_origen;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarRutas();

        tabla_rutas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                seleccionarRuta();
                btn_actualizar.setDisable(false);
                btn_eliminar.setDisable(false);
                btn_agregar.setDisable(true);
            } else {
                btn_actualizar.setDisable(true);
                btn_eliminar.setDisable(true);
                btn_agregar.setDisable(false);
            }
        });

    }

    @FXML
    void act_guardar(ActionEvent event) {
        RutaModel ruta = new RutaModel();
        ruta.setOrigen(txt_origen.getText());
        ruta.setDestino(txt_destino.getText());
        ruta.setDuracion(txt_duracion.getText());

        rutaService.guardarRuta(ruta);
        listarRutas();
        limpiarCampos();

    }

    private void listarRutas() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idRuta"));
        columnOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        columnDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        columnDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        tabla_rutas.getItems().setAll(rutaService.listarRutas());
    }

    @FXML
    void act_eliminar(ActionEvent event) {
        RutaModel ruta = tabla_rutas.getSelectionModel().getSelectedItem();
        rutaService.eliminarRuta(ruta.getIdRuta());
        listarRutas();
        limpiarCampos();
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        RutaModel ruta = tabla_rutas.getSelectionModel().getSelectedItem();
        ruta.setOrigen(txt_origen.getText());
        ruta.setDestino(txt_destino.getText());
        ruta.setDuracion(txt_duracion.getText());

        rutaService.actualizarRuta(ruta);
        listarRutas();
        limpiarCampos();
    }



        //Metodos de apoyo
    public void limpiarCampos(){
        txt_origen.setText("");
        txt_destino.setText("");
        txt_duracion.setText("");

    }

    @FXML
    private void seleccionarRuta(){
        RutaModel ruta = tabla_rutas.getSelectionModel().getSelectedItem();
        txt_origen.setText(ruta.getOrigen());
        txt_destino.setText(ruta.getDestino());
        txt_duracion.setText(ruta.getDuracion());
    }



}
