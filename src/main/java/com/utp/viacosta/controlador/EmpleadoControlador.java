package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.respuesta.ReniecRespuesta;
import com.utp.viacosta.agregates.retrofit.ReniecService;
import com.utp.viacosta.agregates.retrofit.api.ReniecCliente;
import com.utp.viacosta.modelo.EmpleadoModelo;
import com.utp.viacosta.modelo.RolModelo;
import com.utp.viacosta.servicio.EmpleadoServicio;
import com.utp.viacosta.servicio.RolServicio;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class EmpleadoControlador implements Initializable {

    @Autowired
    private EmpleadoServicio empleadoServicio;
    @Autowired
    private RolServicio rolServicio;
    @Value("${token.api}")
    private String tokenApi;

    @FXML
    private Button btn_actualizar,btnLimpiar, btn_guardar;

    @FXML
    private TableView<EmpleadoModelo> tabla_empleados;
    @FXML
    private TableColumn<EmpleadoModelo, String> columnNombre;
    @FXML
    private TableColumn<EmpleadoModelo, String> columnApellido;
    @FXML
    private TableColumn<EmpleadoModelo, String> columnCorreo;
    @FXML
    private TableColumn<EmpleadoModelo, String> columnDni;
    @FXML
    private TableColumn<EmpleadoModelo, String> columnId;
    @FXML
    private TableColumn<RolModelo, String> columnRol;
    @FXML
    private TableColumn<EmpleadoModelo, String> columnTelefono;
    @FXML
    private TableColumn<EmpleadoModelo, Void> columnAcciones;

    @FXML
    private TextField txt_apellido,txt_correo,txt_dni,txt_nombre,txt_telefono;
    @FXML
    private ComboBox<RolModelo> cboxRol;
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
                btnLimpiar.setVisible(true);
                btn_guardar.setVisible(false);
            }
        });
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);

    }


    @FXML
    private void guardarEmpleados(ActionEvent event){
        if (!validarEntradas()){
            return;
        }
        EmpleadoModelo empleado = new EmpleadoModelo();
        empleado.setDni(txt_dni.getText());
        empleado.setNombre(txt_nombre.getText());
        empleado.setApellido(txt_apellido.getText());
        empleado.setCorreo(txt_correo.getText());
        empleado.setPassword(txt_contraseña.getText());
        empleado.setTelefono(txt_telefono.getText());
        empleado.setIdSede(1);

        RolModelo rolSeleccionado = cboxRol.getValue();  // Obtener el rol seleccionado
        Set<RolModelo> roles = new HashSet<>();  // Crear un Set de roles (o lista, dependiendo de tu modelo)
        roles.add(rolSeleccionado);  // Agregar el rol seleccionado al conjunto de roles
        empleado.setRoles(roles);  // Asignar el conjunto de roles al empleado

        empleadoServicio.save(empleado);
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

        // Configurar la columna de Estado

        columnAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button iconoEstado = new Button();
            {
               iconoEstado.setOnAction(event -> {
                    EmpleadoModelo empleado = getTableView().getItems().get(getIndex());
                    empleado.setEstado(!empleado.isEstado());
                    empleadoServicio.save(empleado);
                    listarEmpleados();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    EmpleadoModelo empleado = getTableView().getItems().get(getIndex());
                    iconoEstado.setText(empleado.isEstado() ? "Deshabilitar" : "   Habilitar  ");
                    iconoEstado.setStyle(empleado.isEstado() ? "-fx-background-color: #1a44ff; -fx-text-fill: white; " : "-fx-background-color: #ff0000;-fx-text-fill: white;");
                    setGraphic(iconoEstado);
                }
            }
        });
        tabla_empleados.getItems().setAll(empleadoServicio.findAll());
    }

    private void cargarRoles(){
        cboxRol.getItems().setAll(rolServicio.findAll());
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        EmpleadoModelo empleadoSeleccionado = tabla_empleados.getSelectionModel().getSelectedItem();

        // Actualizar los campos del empleado seleccionado
        empleadoSeleccionado.setDni(txt_dni.getText());
        empleadoSeleccionado.setNombre(txt_nombre.getText());
        empleadoSeleccionado.setApellido(txt_apellido.getText());
        empleadoSeleccionado.setCorreo(txt_correo.getText());
        empleadoSeleccionado.setPassword(txt_contraseña.getText());
        empleadoSeleccionado.setTelefono(txt_telefono.getText());

        RolModelo rolSeleccionado = cboxRol.getValue();
        if (rolSeleccionado != null) {
            Set<RolModelo> roles = new HashSet<>();
            roles.add(rolSeleccionado);  // Actualizar con el rol seleccionado
            empleadoSeleccionado.setRoles(roles);
        }
        empleadoServicio.save(empleadoSeleccionado);
        listarEmpleados();
        clear();
        btnLimpiar();// Ocultar los botones de limpiar y actualizar
    }

    //Metodos de apoyo
    @FXML
    void actLimpiar(ActionEvent event) {
        btnLimpiar();
    }

    @FXML
    public void clear(){
        txt_dni.setText("");
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_correo.setText("");
        txt_contraseña.setText("");
        txt_telefono.setText("");
    }
    public void btnLimpiar(){
        clear();
        btn_actualizar.setVisible(false);
        btnLimpiar.setVisible(false);
        btn_guardar.setVisible(true);
    }

    @FXML
    private void seleccionarActualizar() {
        EmpleadoModelo empleadoSeleccionado = tabla_empleados.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            txt_dni.setText(empleadoSeleccionado.getDni());
            txt_nombre.setText(empleadoSeleccionado.getNombre());
            txt_apellido.setText(empleadoSeleccionado.getApellido());
            txt_correo.setText(empleadoSeleccionado.getCorreo());
            txt_contraseña.setText(empleadoSeleccionado.getPassword());
            txt_telefono.setText(empleadoSeleccionado.getTelefono());

            // Cargar el rol seleccionado en el ComboBox
            RolModelo rol = empleadoSeleccionado.getRoles().stream().findFirst().orElse(null);
            cboxRol.setValue(rol);  // Seleccionar el rol en el ComboBox
        }
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.show();
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

    @FXML
    public void handleKeyEmpleado(Event event) {
        if (txt_dni.getText().length() < 8) {
            txt_nombre.setText("");
            txt_apellido.setText("");
            return;
        }

        if (txt_dni.getText().length() == 8) {
            Retrofit retrofit = ReniecCliente.getClient();
            ReniecService reniecService = retrofit.create(ReniecService.class);
            String token = "Bearer " + tokenApi;
            Call<ReniecRespuesta> call = reniecService.getDatosPersona(token, txt_dni.getText());
            call.enqueue(new Callback<ReniecRespuesta>() {
                @Override
                public void onResponse(Call<ReniecRespuesta> call, Response<ReniecRespuesta> response) {
                    if (response.isSuccessful()) {
                        ReniecRespuesta datosPersona = response.body();
                        txt_nombre.setText(datosPersona.getNombres());
                        txt_apellido.setText(datosPersona.getApellidoPaterno() + " " + datosPersona.getApellidoMaterno());
                    } else {
                        mostrarAlerta("Error en la respuesta: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<ReniecRespuesta> call, Throwable t) {
                    mostrarAlerta("Error en la llamada: " + t.getMessage());
                }
            });
        }
    }
}
