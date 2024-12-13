package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.EmpresaModelo;
import com.utp.viacosta.modelo.SedeModelo;
import com.utp.viacosta.servicio.EmpresaServicio;
import com.utp.viacosta.servicio.SedeServicio;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SedeControlador implements Initializable {

    @Autowired
    private EmpresaServicio empresaServicio;
    @Autowired
    private SedeServicio sedeServicio;

    @FXML
    private TextField confTelefono,confCiudad,confDepartamento,confDireccion,confNombre,confPais,confRuc;
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
    private TextField txtCiudadSede, txtDepartamentoSede, txtDireccionSede, txtNombreSede, txtPaisSede, txtTelefonoSede;
    @FXML
    private TableColumn<SedeModelo, String> columnCiudadSede;
    @FXML
    private TableColumn<SedeModelo,String> columnDepartamentoSede;
    @FXML
    private TableColumn<SedeModelo, String> columnDireccionSede;
    @FXML
    private TableColumn<SedeModelo, String> columnNombreSede;
    @FXML
    private TableColumn<SedeModelo, String> columnPaisSede;
    @FXML
    private TableColumn<SedeModelo, String> columnRucSede;
    @FXML
    private TableColumn<SedeModelo, String> columnTelefonoSede;
    @FXML
    private TableView<SedeModelo> tablaSedes;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnGuardar, btnLimpiar, btnRevisarSedes;
     @FXML
    private Pane paneEmpresa;
    @FXML
    private Pane paneSede;
    @FXML
    private TextField txtBuscarSede;
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneEmpresa.setVisible(true);
        paneSede.setVisible(false);
        listarEmpresa();
        tablaEmpresa.getSelectionModel().selectedItemProperty().addListener((obs, anteriorSeleccion, nuevaSeleccion) -> {
            if (nuevaSeleccion != null) {
                seleccionarEmpresa();
                btnEditar.setVisible(true);
                btnLimpiar.setVisible(true);
                btnGuardar.setVisible(false);
            }
        });
        btnEditar.setVisible(false);
        btnGuardar.setVisible(true);
        btnLimpiar.setVisible(false);

        configurarBuscador();
        listarSedes();
        configurarTabla();
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
        listarEmpresa();
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
        EmpresaModelo empresaModelo = new EmpresaModelo();
        empresaModelo.setRazonSocial(confNombre.getText());
        empresaModelo.setRuc(confRuc.getText());
        empresaModelo.setDireccion(confDireccion.getText());
        empresaModelo.setTelefono(confTelefono.getText());
        empresaModelo.setDepartamento(confDepartamento.getText());
        empresaModelo.setCiudad(confCiudad.getText());
        empresaModelo.setPais(confPais.getText());

        empresaServicio.save(empresaModelo);
        clear();
        listarEmpresa();
    }

    private void seleccionarEmpresa(){
        EmpresaModelo empresaModelo = tablaEmpresa.getSelectionModel().getSelectedItem();
        confNombre.setText(empresaModelo.getRazonSocial());
        confRuc.setText(empresaModelo.getRuc());
        confDireccion.setText(empresaModelo.getDireccion());
        confTelefono.setText(empresaModelo.getTelefono());
        confDepartamento.setText(empresaModelo.getDepartamento());
        confCiudad.setText(empresaModelo.getCiudad());
        confPais.setText(empresaModelo.getPais());
    }

    @FXML
    void actLimpiar(ActionEvent event) {
        clear();
        btnEditar.setVisible(false);
        btnGuardar.setVisible(true);
        btnLimpiar.setVisible(false);
    }

    @FXML
    void btnRevisarSedes(ActionEvent event) {
        paneEmpresa.setVisible(false);
        paneSede.setVisible(true);
        listarSedes();
    }

    private void configurarTabla() {
        columnNombreSede.setCellValueFactory(new PropertyValueFactory<>("nombreSedes"));
        columnDireccionSede.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefonoSede.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnDepartamentoSede.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        columnCiudadSede.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnPaisSede.setCellValueFactory(new PropertyValueFactory<>("pais"));

        tablaSedes.getSelectionModel().selectedItemProperty().addListener((obs, anteriorSeleccion, nuevaSeleccion) -> {
            if (nuevaSeleccion != null) {
                mostrarSedeEnFormulario();
            }
        });
    }
    
    private void mostrarSedeEnFormulario() {
        SedeModelo sedeModelo = tablaSedes.getSelectionModel().getSelectedItem();
        txtNombreSede.setText(sedeModelo.getNombreSedes());
        txtDireccionSede.setText(sedeModelo.getDireccion());
        txtTelefonoSede.setText(sedeModelo.getTelefono());
        txtDepartamentoSede.setText(sedeModelo.getDepartamento());
        txtCiudadSede.setText(sedeModelo.getCiudad());
        txtPaisSede.setText(sedeModelo.getPais());
    }

    private void listarSedes() {
        tablaSedes.setItems(FXCollections.observableArrayList(sedeServicio.listaSedes()));
    }

    private void configurarBuscador() {
    txtBuscarSede.textProperty().addListener((obs, valorAnterior, nuevoValor) -> {
        tablaSedes.setItems(FXCollections.observableArrayList(sedeServicio.buscarSedes(nuevoValor)));
    });
}

    @FXML
    void guardarSede(ActionEvent event) {
        if (validarCamposSede()) {
            SedeModelo sede = new SedeModelo();
            sede.setNombreSedes(txtNombreSede.getText());
            sede.setDireccion(txtDireccionSede.getText());
            sede.setTelefono(txtTelefonoSede.getText());
            sede.setDepartamento(txtDepartamentoSede.getText());
            sede.setCiudad(txtCiudadSede.getText());
            sede.setPais(txtPaisSede.getText());

            sedeServicio.guardarSede(sede);
            limpiarCamposSede();
            listarSedes();
        }
    }

    @FXML
    void btnEditarSede(ActionEvent event) {
        SedeModelo sedeModelo = tablaSedes.getSelectionModel().getSelectedItem();
        sedeModelo.setNombreSedes(txtNombreSede.getText());
        sedeModelo.setDireccion(txtDireccionSede.getText());
        sedeModelo.setTelefono(txtTelefonoSede.getText());
        sedeModelo.setDepartamento(txtDepartamentoSede.getText());
        sedeModelo.setCiudad(txtCiudadSede.getText());
        sedeModelo.setPais(txtPaisSede.getText());

        sedeServicio.guardarSede(sedeModelo);
        listarSedes();
        limpiarCamposSede();
    }

    @FXML
    void btnLimpiarSede(ActionEvent event) {
        limpiarCamposSede();
    }

    private void limpiarCamposSede() {
        txtNombreSede.clear();
        txtDireccionSede.clear();
        txtTelefonoSede.clear();
        txtDepartamentoSede.clear();
        txtCiudadSede.clear();
        txtPaisSede.clear();
    }

    private boolean validarCamposSede() {
        return !txtNombreSede.getText().isEmpty() &&
                !txtDireccionSede.getText().isEmpty() &&
                !txtTelefonoSede.getText().isEmpty() &&
                !txtDepartamentoSede.getText().isEmpty() &&
                !txtCiudadSede.getText().isEmpty() &&
                !txtPaisSede.getText().isEmpty();
    }

    
}

