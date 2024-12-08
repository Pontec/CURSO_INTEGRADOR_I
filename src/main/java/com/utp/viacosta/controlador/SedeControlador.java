package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.EmpresaModelo;
import com.utp.viacosta.servicio.EmpresaServicio;
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
    private EmpresaServicio empresaServicio;

    @FXML
    private TextField confTelefono;

    @FXML
    private TextField confCiudad;

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
    private TableColumn<EmpresaModelo, String> columnCiudad;

    @FXML
    private TableColumn<EmpresaModelo,String> columnDepartamento;

    @FXML
    private TableColumn<EmpresaModelo, String> columnDireccion;

    @FXML
    private TableColumn<EmpresaModelo, String> columnNombre;

    @FXML
    private TableColumn<EmpresaModelo, String> columnPais;

    @FXML
    private TableColumn<EmpresaModelo, String> columnRuc;

    @FXML
    private TableColumn<EmpresaModelo, String> columnTelefono;

    @FXML
    private TableView<EmpresaModelo> tablaEmpresa;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarEmpresa();
    }

    @FXML
    void btnActualizar(ActionEvent event) {
        EmpresaModelo empresaModelo = new EmpresaModelo();
        empresaModelo.setRazonSocial(confNombre.getText());
        empresaModelo.setDireccion(confDireccion.getText());
        empresaModelo.setTelefono(confTelefono.getText());
        empresaModelo.setDepartamento(confDepartamento.getText());
        empresaModelo.setCiudad(confCiudad.getText());
        empresaModelo.setPais(confPais.getText());

        empresaServicio.save(empresaModelo);
        clear();
    }
    private void listarEmpresa(){
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        columnRuc.setCellValueFactory(new PropertyValueFactory<>("ruc"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        columnPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tablaEmpresa.getItems().setAll(empresaServicio.findAll());
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

    @FXML
    public void btnGuardar(ActionEvent actionEvent) {
    }
}

