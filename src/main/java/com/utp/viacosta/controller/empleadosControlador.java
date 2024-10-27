package com.utp.viacosta.controller;

import com.utp.viacosta.model.EmpleadoModel;
import com.utp.viacosta.model.RolModel;
import com.utp.viacosta.service.EmpleadoService;
import com.utp.viacosta.service.RolService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class empleadosControlador implements Initializable {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private RolService rolService;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private Button btn_guardar;

    @FXML
    private TableView<EmpleadoModel> tabla_empleados;
    @FXML
    private TableColumn<EmpleadoModel, String> columnNombre;
    @FXML
    private TableColumn<EmpleadoModel, String> columnApellido;
    @FXML
    private TableColumn<EmpleadoModel, String> columnCorreo;
    @FXML
    private TableColumn<EmpleadoModel, String> columnDni;
    @FXML
    private TableColumn<EmpleadoModel, String> columnId;
    @FXML
    private TableColumn<RolModel, String> columnRol;
    @FXML
    private TableColumn<EmpleadoModel, String> columnTelefono;

    @FXML
    private TextField txt_apellido;
    @FXML
    private TextField txt_correo;
    @FXML
    private TextField txt_dni;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_telefono;
    @FXML
    private ComboBox<RolModel> cboxRol;
    @FXML
    private PasswordField txt_contraseña;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarEmpleados();
        cargarRoles();

        // Listener para detectar la selección en la tabla y cargar los datos en los campos
        tabla_empleados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarActualizar();
                btn_actualizar.setVisible(true);
                btn_eliminar.setVisible(true);
            }else{

            }
        });
        btn_actualizar.setVisible(false);
        btn_eliminar.setVisible(false);
    }


    @FXML
    private void guardarEmpleados(ActionEvent event){
        if (!validarEntradas()){
            return;
        }
        EmpleadoModel empleado = new EmpleadoModel();
        empleado.setDni(txt_dni.getText());
        empleado.setNombre(txt_nombre.getText());
        empleado.setApellido(txt_apellido.getText());
        empleado.setCorreo(txt_correo.getText());
        empleado.setPassword(txt_contraseña.getText());
        empleado.setTelefono(txt_telefono.getText());
        empleado.setIdSede(1);

        RolModel rolSeleccionado = cboxRol.getValue();  // Obtener el rol seleccionado
        Set<RolModel> roles = new HashSet<>();  // Crear un Set de roles (o lista, dependiendo de tu modelo)
        roles.add(rolSeleccionado);  // Agregar el rol seleccionado al conjunto de roles
        empleado.setRoles(roles);  // Asignar el conjunto de roles al empleado

        empleadoService.save(empleado);
        listarEmpleados();
        clear();
    }

    @FXML
    private void listarEmpleados(){
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnRol.setCellValueFactory(new PropertyValueFactory<>("rolNombres"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        tabla_empleados.getItems().setAll(empleadoService.findAll());

    }

    private void cargarRoles(){
        cboxRol.getItems().setAll(rolService.findAll());
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        EmpleadoModel empleado = tabla_empleados.getSelectionModel().getSelectedItem();

        if(empleado != null && mostrarConfirmacion("¿Estás seguro de eliminar el empleado?")){
            empleadoService.deleteById(empleado.getId());
            tabla_empleados.getItems().remove(empleado);
        }else{
            mostrarAlerta("Selecciona el cliente a eliminar");
        }
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        EmpleadoModel empleadoSeleccionado = tabla_empleados.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado == null) {
            mostrarAlerta("Por favor, selecciona un empleado para actualizar.");
            return;
        }

        // Actualizar los campos del empleado seleccionado
        empleadoSeleccionado.setDni(txt_dni.getText());
        empleadoSeleccionado.setNombre(txt_nombre.getText());
        empleadoSeleccionado.setApellido(txt_apellido.getText());
        empleadoSeleccionado.setCorreo(txt_correo.getText());
        empleadoSeleccionado.setPassword(txt_contraseña.getText());
        empleadoSeleccionado.setTelefono(txt_telefono.getText());

        RolModel rolSeleccionado = cboxRol.getValue();
        if (rolSeleccionado != null) {
            Set<RolModel> roles = new HashSet<>();
            roles.add(rolSeleccionado);  // Actualizar con el rol seleccionado
            empleadoSeleccionado.setRoles(roles);
        }

        // Guardar el empleado actualizado en la base de datos
        empleadoService.save(empleadoSeleccionado);
        listarEmpleados();  // Refrescar la tabla para mostrar los cambios
        clear();  // Limpiar los campos de texto
    }

    //Metodos de apoyo

    @FXML
    public void clear(){
        txt_dni.setText("");
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_correo.setText("");
        txt_contraseña.setText("");
        txt_telefono.setText("");
    }



    @FXML
    private void seleccionarActualizar() {
        EmpleadoModel empleadoSeleccionado = tabla_empleados.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            txt_dni.setText(empleadoSeleccionado.getDni());
            txt_nombre.setText(empleadoSeleccionado.getNombre());
            txt_apellido.setText(empleadoSeleccionado.getApellido());
            txt_correo.setText(empleadoSeleccionado.getCorreo());
            txt_contraseña.setText(empleadoSeleccionado.getPassword());
            txt_telefono.setText(empleadoSeleccionado.getTelefono());

            // Cargar el rol seleccionado en el ComboBox
            RolModel rol = empleadoSeleccionado.getRoles().stream().findFirst().orElse(null);
            cboxRol.setValue(rol);  // Seleccionar el rol en el ComboBox
        }
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.show();
    }

    //para eliminar el cliente
    private boolean mostrarConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(mensaje);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    private boolean validarEntradas() {
        if (
                txt_dni.getText().isEmpty() || txt_nombre.getText().isEmpty() ||
                        txt_apellido.getText().isEmpty() || txt_correo.getText().isEmpty() ||
                        txt_contraseña.getText().isEmpty() || cboxRol.getValue() == null ){
            mostrarAlerta("Por favor, completa todos los campos.");
            return false;
        }
        if (txt_dni.getText().length() != 8) {
            mostrarAlerta("El DNI debe tener 8 dígitos.");
            return false;
        }
        if (txt_telefono.getText().length() != 9) {
            mostrarAlerta("El teléfono debe tener 9 dígitos.");
            return false;
        }

        if (!txt_correo.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            mostrarAlerta("El correo no es válido.");
            return false;
        }
        return true;
    }

}
