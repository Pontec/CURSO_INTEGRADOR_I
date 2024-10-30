package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.SedeModelo;
import com.utp.viacosta.servicio.SedeServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SedeControlador implements Initializable {

    @Autowired
    private SedeServicio sedeServicio;

    @FXML
    private TextField confTelefono;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnLogo;

    @FXML
    private TextField confCiudad;

    @FXML
    private TextField cofDescripcion;

    @FXML
    private TextField confDepartamento;

    @FXML
    private TextField confDireccion;

    @FXML
    private TextField confNombre;

    @FXML
    private TextField confPais;

    @FXML
    private TextField confRuc;
    @FXML
    private TableColumn<SedeModelo, String> columnCiudad;

    @FXML
    private TableColumn<SedeModelo,String> columnDepartamento;

    @FXML
    private TableColumn<SedeModelo, String> columnDescripcion;

    @FXML
    private TableColumn<SedeModelo, String> columnDireccion;

    @FXML
    private TableColumn<SedeModelo, String> columnNombre;

    @FXML
    private TableColumn<SedeModelo, String> columnPais;

    @FXML
    private TableColumn<SedeModelo, String> columnRuc;

    @FXML
    private TableColumn<SedeModelo, String> columnTelefono;

    @FXML
    private TableView<SedeModelo> tablaEmpresa;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarSedes();

    }


    @FXML
    void btnActualizar(ActionEvent event) {
        SedeModelo sedeModelo = new SedeModelo();
        sedeModelo.setNombreSedes(confNombre.getText());
        sedeModelo.setRuc(confRuc.getText());
        sedeModelo.setDireccion(confDireccion.getText());
        sedeModelo.setTelefono(confTelefono.getText());
        sedeModelo.setDepartamento(confDepartamento.getText());
        sedeModelo.setCiudad(confCiudad.getText());
        sedeModelo.setPais(confPais.getText());


        sedeServicio.guardarSede(sedeModelo);
        clear();
    }
    private void listarSedes(){
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreSedes"));
        columnRuc.setCellValueFactory(new PropertyValueFactory<>("ruc"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        columnPais.setCellValueFactory(new PropertyValueFactory<>("pais"));

        tablaEmpresa.getItems().setAll(sedeServicio.listaSedes());
    }

    //metodo de limpiar
    @FXML
    public void clear(){
        confNombre.setText("");
        confRuc.setText("");
        confDireccion.setText("");
        confTelefono.setText("");
        confDepartamento.setText("");
        confCiudad.setText("");
        confPais.setText("");

    }

}

