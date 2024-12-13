package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.EmpresaModelo;
import com.utp.viacosta.modelo.SedeModelo;
import com.utp.viacosta.servicio.EmpresaServicio;
import com.utp.viacosta.servicio.SedeServicio;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TextField confTelefono, confCiudad, confDepartamento, confDireccion, confNombre, confPais, confRuc;
    @FXML
    private TableColumn<EmpresaModelo, String> columnCiudad;
    @FXML
    private TableColumn<EmpresaModelo, String> columnDepartamento;
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
    private TableColumn<SedeModelo, String> columnDepartamentoSede;
    @FXML
    private TableColumn<SedeModelo, String> columnDireccionSede;
    @FXML
    private TableColumn<SedeModelo, String> columnNombreSede;
    @FXML
    private TableColumn<SedeModelo, String> columnPaisSede;
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
    private TextField txtBuscarSede;
    @FXML
    private Label err_empresa;
    @FXML
    private Label err_direc;
    @FXML
    private Label err_tel;
    @FXML
    private Label error_ciudad;
    @FXML
    private Label err_pais;
    @FXML
    private Button guardarSede;
    @FXML
    private Label error_pais;
    @FXML
    private Button btnEditarSede;
    @FXML
    private Label err_city;
    @FXML
    private Label error_tel;
    @FXML
    private Button btnVolver;
    @FXML
    private Pane panelSede;
    @FXML
    private Button btnLimpiarSede;
    @FXML
    private Label err_dep;
    @FXML
    private Label error_nombre;
    @FXML
    private Label err_ruc;
    @FXML
    private Label error_depa;
    @FXML
    private Label error_dir;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneEmpresa.setVisible(true);
        panelSede.setVisible(false);
        listarEmpresa();
        validacionesEmpresa();
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
        btnLimpiar.setVisible(true);

        configurarBuscador();
        listarSedes();
        configurarTabla();
        validacionesSede();
    }

    public void validacionesSede() {
        agregarValidacionNotEmpty(txtNombreSede, error_nombre);
        agregarValidacionNotEmpty(txtDireccionSede, error_dir);
        agregarValidacionTelefono(txtTelefonoSede, error_tel);
        agregarValidacionSoloLetras(txtDepartamentoSede, err_dep);
        agregarValidacionSoloLetras(txtCiudadSede, err_city);
        agregarValidacionSoloLetras(txtPaisSede, err_pais);
    }

    public void validacionesEmpresa() {
        agregarValidacionNotEmpty(confNombre, err_empresa);
        agregarValidacionNotEmpty(confDireccion, err_direc);
        agregarValidacionTelefono(confTelefono, err_tel);
        agregarValidacionSoloLetras(confDepartamento, err_dep);
        agregarValidacionSoloLetras(confCiudad, error_ciudad);
        agregarValidacionSoloLetras(confPais, err_pais);
        agregarValidacionRuc(confRuc, err_ruc);
    }

    private void agregarValidacionSoloLetras(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                textField.setText(oldValue); // Restore the previous value
            }
            if (newValue.matches("^[a-zA-Z]*$")) {
                errorLabel.setText(""); // Clear the error message if valid
            } else {
                errorLabel.setText("Este campo solo puede contener letras.");
            }
        });
    }

    private void agregarValidacionTelefono(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 9) {
                textField.setText(oldValue); // Restore the previous value
            }
            if (newValue.length() == 9 && newValue.startsWith("9")) {
                errorLabel.setText(""); // Clear the error message if valid
            } else if (newValue.length() == 9 && !newValue.startsWith("9")) {
                errorLabel.setText("El teléfono debe comenzar con 9.");
            }
        });
    }

    private void agregarValidacionRuc(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 11) {
                textField.setText(oldValue); // Restore the previous value
            }
            if (newValue.length() == 11) {
                errorLabel.setText(""); // Clear the error message if valid
            } else {
                errorLabel.setText("El RUC debe tener 11 dígitos.");
            }
        });
    }

    private void agregarValidacionNotEmpty(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                errorLabel.setText("Este campo es obligatorio.");
            } else {
                errorLabel.setText(""); // Limpiar el mensaje si es válido
            }
        });
    }

    @FXML
    void btnActualizar(ActionEvent event) {
        EmpresaModelo empresaModelo = new EmpresaModelo();
        if (!validarCamposEmpresa(true, empresaModelo.getRuc())) {
            return;
        }
        empresaModelo.setRazonSocial(confNombre.getText());
        empresaModelo.setRuc(confRuc.getText());
        empresaModelo.setDireccion(confDireccion.getText());
        empresaModelo.setTelefono(confTelefono.getText());
        empresaModelo.setDepartamento(confDepartamento.getText());
        empresaModelo.setCiudad(confCiudad.getText());
        empresaModelo.setPais(confPais.getText());

        empresaServicio.save(empresaModelo);
        listarEmpresa();
        clear();
    }

    private void listarEmpresa() {
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
    public void clear() {
        confNombre.setText("");
        confRuc.setText("");
        confDireccion.setText("");
        confTelefono.setText("");
        confDepartamento.setText("");
        confCiudad.setText("");
        confPais.setText("");

        err_empresa.setText("");
        err_ruc.setText("");
        err_direc.setText("");
        err_tel.setText("");
        err_dep.setText("");
        error_ciudad.setText("");
        err_pais.setText("");
        btnEditar.setVisible(false);
        confRuc.setDisable(false);
        btnGuardar.setVisible(true);

    }

    @FXML
    public void btnGuardar(ActionEvent actionEvent) {
        if (!validarCamposEmpresa(false, null)) {
            return;
        }
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

    private void seleccionarEmpresa() {
        EmpresaModelo empresaModelo = tablaEmpresa.getSelectionModel().getSelectedItem();
        confNombre.setText(empresaModelo.getRazonSocial());
        confRuc.setText(empresaModelo.getRuc());
        confDireccion.setText(empresaModelo.getDireccion());
        confTelefono.setText(empresaModelo.getTelefono());
        confDepartamento.setText(empresaModelo.getDepartamento());
        confCiudad.setText(empresaModelo.getCiudad());
        confPais.setText(empresaModelo.getPais());
        confRuc.setDisable(true);
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
        panelSede.setVisible(true);
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
        btnGuardar.setVisible(false);
        btnEditarSede.setVisible(true);
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
        if (!validarCamposSede()) {
            return;
        }
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
        btnEditarSede.setVisible(false);
    }

    @FXML
    void btnLimpiarSede(ActionEvent event) {
        limpiarCamposSede();
        btnEditarSede.setVisible(false);
    }

    private void limpiarCamposSede() {
        txtNombreSede.clear();
        txtDireccionSede.clear();
        txtTelefonoSede.clear();
        txtDepartamentoSede.clear();
        txtCiudadSede.clear();
        txtPaisSede.clear();

        error_nombre.setText("");
        error_dir.setText("");
        error_tel.setText("");
        error_depa.setText("");
        error_ciudad.setText("");
        error_pais.setText("");
        btnEditarSede.setVisible(false);

    }

    private boolean validarCamposSede() {
        boolean isValid = true;
        if (txtNombreSede.getText().isEmpty() || !txtNombreSede.getText().matches("^[a-zA-Z\\s]*$")) {
            error_nombre.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            error_nombre.setText("");
        }
        if (txtDireccionSede.getText().isEmpty()) {
            error_dir.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            error_dir.setText("");
        }
        if (txtTelefonoSede.getText().isEmpty() || txtTelefonoSede.getText().length() != 9 || !txtTelefonoSede.getText().startsWith("9") || !txtTelefonoSede.getText().matches("\\d*")) {
            error_tel.setText("El teléfono debe tener 9 dígitos, comenzar con 9.");
            isValid = false;
        } else {
            error_tel.setText("");
        }
        if (txtDepartamentoSede.getText().isEmpty() || !txtDepartamentoSede.getText().matches("^[a-zA-Z\\s]*$")) {
            err_dep.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            err_dep.setText("");
        }
        if (txtCiudadSede.getText().isEmpty() || !txtCiudadSede.getText().matches("^[a-zA-Z\\s]*$")) {
            err_city.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            err_city.setText("");
        }
        if (txtPaisSede.getText().isEmpty() || !txtPaisSede.getText().matches("^[a-zA-Z\\s]*$")) {
            err_pais.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            err_pais.setText("");
        }
        return isValid;
    }

    private boolean validarCamposEmpresa(boolean isUpdating, String currentRuc) {
        boolean isValid = true;

        if (confNombre.getText().isEmpty() || !confNombre.getText().matches("^[a-zA-Z\\s]*$")) {
            err_empresa.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            err_empresa.setText("");
        }

        if (confDireccion.getText().isEmpty()) {
            err_direc.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            err_direc.setText("");
        }

        if (confTelefono.getText().isEmpty() || confTelefono.getText().length() != 9 || !confTelefono.getText().startsWith("9") || !confTelefono.getText().matches("\\d*")) {
            err_tel.setText("El teléfono debe tener 9 dígitos, comenzar con 9.");
            isValid = false;
        } else {
            err_tel.setText("");
        }

        if (confDepartamento.getText().isEmpty() || !confDepartamento.getText().matches("^[a-zA-Z\\s]*$")) {
            err_dep.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            err_dep.setText("");
        }

        if (confCiudad.getText().isEmpty() || !confCiudad.getText().matches("^[a-zA-Z\\s]*$")) {
            error_ciudad.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            error_ciudad.setText("");
        }

        if (confPais.getText().isEmpty() || !confPais.getText().matches("^[a-zA-Z\\s]*$")) {
            err_pais.setText("Este campo es obligatorio.");
            isValid = false;
        } else {
            err_pais.setText("");
        }

        if (confRuc.getText().isEmpty() || confRuc.getText().length() != 11 || !confRuc.getText().matches("\\d*")) {
            err_ruc.setText("El RUC debe tener 11 dígitos.");
            isValid = false;
        } else if (!isUpdating && empresaServicio.existeRuc(confRuc.getText())) {
            err_ruc.setText("El RUC ya existe.");
            isValid = false;
        } else if (isUpdating && !confRuc.getText().equals(currentRuc) && empresaServicio.existeRuc(confRuc.getText())) {
            err_ruc.setText("El RUC ya existe.");
            isValid = false;
        } else {
            err_ruc.setText("");
        }

        return isValid;
    }


    @FXML
    public void btnVolver(ActionEvent actionEvent) {
        paneEmpresa.setVisible(true);
        panelSede.setVisible(false);
    }
}

