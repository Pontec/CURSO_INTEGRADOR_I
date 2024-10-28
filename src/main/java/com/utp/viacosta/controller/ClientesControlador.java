package com.utp.viacosta.controller;

import com.utp.viacosta.model.ClienteModel;
import com.utp.viacosta.service.ClienteService;
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
public class ClientesControlador implements Initializable {

    @Autowired
    private ClienteService clienteService;

    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_guardar;
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
    private TextField txt_apellido;
    @FXML
    private TextField txt_correo;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_dni;
    @FXML
    private Button btn_actulizar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarClientes();

        tabla_clientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarCliente();
            }
        });

    }

    @FXML
    void act_save(ActionEvent event) {

        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNombre(txt_nombre.getText());
        clienteModel.setApellido(txt_apellido.getText());
        clienteModel.setDni(txt_dni.getText());
        clienteModel.setCorreo(txt_correo.getText());
        clienteModel.setTelefono(txt_telefono.getText());

        clienteService.guardarCliente(clienteModel);
        listarClientes();
        limpiar();
    }

    private void listarClientes() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tabla_clientes.getItems().setAll(clienteService.listaClientes());
    }

    @FXML
    void act_eliminar(ActionEvent event) {
        ClienteModel clienteModel = tabla_clientes.getSelectionModel().getSelectedItem();
        clienteService.eliminarCliente(clienteModel.getIdCliente());
        listarClientes();
        limpiar();
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        ClienteModel clienteModel = tabla_clientes.getSelectionModel().getSelectedItem();
        clienteModel.setNombre(txt_nombre.getText());
        clienteModel.setApellido(txt_apellido.getText());
        clienteModel.setDni(txt_dni.getText());
        clienteModel.setCorreo(txt_correo.getText());
        clienteModel.setTelefono(txt_telefono.getText());

        clienteService.actualizarCliente(clienteModel);
        listarClientes();
        limpiar();
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

}

