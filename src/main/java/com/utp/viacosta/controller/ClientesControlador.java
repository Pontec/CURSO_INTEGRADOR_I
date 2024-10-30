package com.utp.viacosta.controller;

import com.utp.viacosta.model.ClienteModel;
import com.utp.viacosta.service.ClienteService;
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
public class ClientesControlador implements Initializable {

    @Autowired
    private ClienteService clienteService;

    @FXML
    private Button btn_actualizar,btn_guardar, btnLimpiar;
    @FXML
    private TableColumn<ClienteModel, String> columnApellido;
    @FXML
    private TableColumn<ClienteModel, String> columnCorreo;
    @FXML
    private TableColumn<ClienteModel, String> columnDni;
    @FXML
    private TableColumn<ClienteModel, String> columnId;
    @FXML
    private TableColumn<ClienteModel, String> columnNombre;
    @FXML
    private TableColumn<ClienteModel, String> columnTelefono;
    @FXML
    private TableView<ClienteModel> tabla_clientes;

    @FXML
    private TextField txt_apellido, txt_correo, txt_nombre, txt_telefono, txt_dni;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarClientes();

        tabla_clientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarCliente();
                btn_guardar.setVisible(false);
                btn_actualizar.setVisible(true);
                btnLimpiar.setVisible(true);
            }
        });
        btn_guardar.setVisible(true);
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);

    }

    @FXML
    void act_save(ActionEvent event) {
        if (!validarEntradas()) {
            return;
        }

        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNombre(txt_nombre.getText());
        clienteModel.setApellido(txt_apellido.getText());
        clienteModel.setDni(txt_dni.getText());
        clienteModel.setCorreo(txt_correo.getText());
        clienteModel.setTelefono(txt_telefono.getText());

        clienteService.guardarCliente(clienteModel);
        listarClientes();
        limpiar();
        limpiarStyles();
    }

    private void listarClientes() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        //configurar la columna de acciones


        tabla_clientes.getItems().setAll(clienteService.listaClientes());
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        if (!validarEntradas()) {
            return;
        }
        ClienteModel clienteModel = tabla_clientes.getSelectionModel().getSelectedItem();
        clienteModel.setNombre(txt_nombre.getText());
        clienteModel.setApellido(txt_apellido.getText());
        clienteModel.setDni(txt_dni.getText());
        clienteModel.setCorreo(txt_correo.getText());
        clienteModel.setTelefono(txt_telefono.getText());

        clienteService.actualizarCliente(clienteModel);
        listarClientes();
        limpiar();
        limpiarStyles();
    }
    @FXML
    public void actLimpiar(ActionEvent event) {
        limpiar();
        btn_guardar.setVisible(true);
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);
    }


    //Metodos de apoyo
    @FXML
    public void limpiar() {
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_dni.setText("");
        txt_correo.setText("");
        txt_telefono.setText("");
    }

    @FXML
    public void seleccionarCliente() {
        ClienteModel clienteModel = tabla_clientes.getSelectionModel().getSelectedItem();
        txt_nombre.setText(clienteModel.getNombre());
        txt_apellido.setText(clienteModel.getApellido());
        txt_dni.setText(clienteModel.getDni());
        txt_correo.setText(clienteModel.getCorreo());
        txt_telefono.setText(clienteModel.getTelefono());
    }



    //Metodos para validar los campos
    private boolean validarNombre() {
        if (txt_nombre.getText().isEmpty()) {
            txt_nombre.setStyle("-fx-border-color: red");
            mostrarAlerta("El nombre no puede estar vacio.");
            return false;
        }
        return true;
    }

    private boolean validarApellido() {
        if (txt_apellido.getText().isEmpty()) {
            txt_apellido.setStyle("-fx-border-color: red");
            mostrarAlerta("El apellido no puede estar vacio.");
            return false;
        }
        return true;
    }

    private boolean validarDni() {
        String dni = txt_dni.getText();
        if (dni.isEmpty() || dni.length() != 8) {
            txt_dni.setStyle("-fx-border-color: red");
            mostrarAlerta("El DNI debe tener 8 digitos.");
            return false;
        }
        return true;
    }

    private boolean validarCorreo() {
        String correo = txt_correo.getText();
        if ( !correo.isEmpty() && !correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            txt_correo.setStyle("-fx-border-color: red");
            mostrarAlerta("Ingrese un correo valido");
            return false;
        }
        return true;
    }

    private boolean validarEntradas() {
        return validarDni() && validarNombre() && validarApellido() && validarCorreo();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void limpiarStyles() {
        txt_nombre.setStyle("");
        txt_apellido.setStyle("");
        txt_dni.setStyle("");
        txt_correo.setStyle("");
        txt_telefono.setStyle("");
    }

}

