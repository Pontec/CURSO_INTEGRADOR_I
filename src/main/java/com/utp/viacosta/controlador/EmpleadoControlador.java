package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.respuesta.ReniecRespuesta;
import com.utp.viacosta.agregates.retrofit.ReniecService;
import com.utp.viacosta.agregates.retrofit.api.ReniecCliente;
import com.utp.viacosta.modelo.EmpleadoModelo;
import com.utp.viacosta.modelo.RolModelo;
import com.utp.viacosta.modelo.SedeModelo;
import com.utp.viacosta.servicio.EmpleadoServicio;
import com.utp.viacosta.servicio.RolServicio;
import com.utp.viacosta.servicio.SedeServicio;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    @Autowired
    private SedeServicio sedeServicio;
    @Value("${token.api}")
    private String tokenApi;
    @FXML
    private Button btn_actualizar, btnLimpiar, btn_guardar;
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
    private TableColumn<RolModelo, String> columnRol;
    @FXML
    private TableColumn<EmpleadoModelo, String> columnTelefono;
    @FXML
    private TableColumn<EmpleadoModelo, Void> columnAcciones;
    @FXML
    private TextField txt_apellido, txt_correo, txt_dni, txt_nombre, txt_telefono;
    @FXML
    private ComboBox<RolModelo> cmbRol;
    @FXML
    private PasswordField txt_contraseña;
    @FXML
    private TableColumn<EmpleadoModelo, String> columnSede;
    @FXML
    private ComboBox<SedeModelo> cmbSede;
    @FXML
    private Label error_Dni;
    @FXML
    private Label error_Correo;
    @FXML
    private Label error_tel;
    @FXML
    private Label error_sede;
    @FXML
    private Label error_rol;
    @FXML
    private Label error_contra;
    @FXML
    private Label error_nombre;
    @FXML
    private Label error_apellido;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarEmpleados();
        cargarRoles();
        cargarSedes();
        inicializarValidacion();
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

    private void inicializarValidacion() {
        agregarValidacionDni(txt_dni, error_Dni);
        agregarValidacionTelefono(txt_telefono, error_tel);
        agregarValidacionCorreoConLabel(txt_correo, error_Correo);
        agregarValidacionNotEmpty(txt_contraseña, error_contra);
        agregarValidacionComboBox(cmbRol, error_rol);
        agregarValidacionComboBox(cmbSede, error_sede);
    }

    private boolean validarEntradas(boolean validarDni, String currentCorreo) {
        boolean isValid = true;

        if (validarDni) {
            if (txt_dni.getText().length() != 8 || txt_dni.getText().isEmpty()) {
                error_Dni.setText("El DNI debe tener 8 dígitos.");
                isValid = false;
            } else {
                error_Dni.setText("");
            }

            if (empleadoServicio.findByDni(txt_dni.getText()) != null) {
                error_Dni.setText("El DNI ya existe.");
                isValid = false;
            }
        }

        if (txt_telefono.getText().length() != 9 || txt_telefono.getText().isEmpty()) {
            error_tel.setText("El teléfono debe tener 9 dígitos.");
            isValid = false;
        } else {
            error_tel.setText("");
        }

        if (!txt_correo.getText().matches(".*@gmail\\.com") || txt_correo.getText().isEmpty()) {
            error_Correo.setText("El correo no es válido.");
            isValid = false;
        } else {
            error_Correo.setText("");
        }

        if (txt_contraseña.getText().isEmpty()) {
            error_contra.setText("La contraseña es obligatoria.");
            isValid = false;
        } else {
            error_contra.setText("");
        }

        if (cmbRol.getValue() == null) {
            error_rol.setText("Seleccione un rol");
            isValid = false;
        } else {
            error_rol.setText("");
        }

        if (cmbSede.getValue() == null) {
            error_sede.setText("Seleccione una sede");
            isValid = false;
        } else {
            error_sede.setText("");
        }

        if (!txt_correo.getText().equals(currentCorreo) && empleadoServicio.findByCorreo(txt_correo.getText()) != null) {
            error_Correo.setText("El correo ya existe.");
            isValid = false;
        }

        return isValid;
    }

    private void agregarValidacionNotEmpty(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                errorLabel.setText("Este campo es obligatorio.");
                textField.setStyle("-fx-border-color: red;");
            } else {
                errorLabel.setText(""); // Limpiar el mensaje si es válido
                textField.setStyle(""); // Restablecer el estilo
            }
        });
    }

    private void agregarValidacionCorreoConLabel(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(".*@gmail\\.com")) { // Si no incluye '@gmail.com'
                errorLabel.setText("El correo debe incluir '@gmail.com'");
                textField.setStyle("-fx-border-color: red;");
            } else {
                errorLabel.setText(""); // Limpiar el mensaje si es válido
                textField.setStyle(""); // Restablecer el estilo
            }
        });
    }

    private void agregarValidacionDni(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 8) {
                textField.setText(oldValue); // Restore the previous value
            }
            if (newValue.length() == 8) {
                errorLabel.setText(""); // Clear the error message if valid
                textField.setStyle(""); // Reset the style
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
                textField.setStyle(""); // Reset the style
            } else if (newValue.length() == 9 && !newValue.startsWith("9")) {
                errorLabel.setText("El teléfono debe comenzar con 9.");
                textField.setStyle("-fx-border-color: red;");
            }
        });
    }

    private void agregarValidacionComboBox(ComboBox comboBox, Label errorLabel) {
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                errorLabel.setText("Seleccione una opción.");
                comboBox.setStyle("-fx-border-color: red;");
            } else {
                errorLabel.setText("");
                comboBox.setStyle("");
            }
        });
    }


    @FXML
    private void guardarEmpleados(ActionEvent event) {
        if (!validarEntradas(true, null)) {
            return;
        }
        EmpleadoModelo empleado = new EmpleadoModelo();
        empleado.setDni(txt_dni.getText());
        empleado.setNombre(txt_nombre.getText());
        empleado.setApellido(txt_apellido.getText());
        empleado.setCorreo(txt_correo.getText());
        empleado.setPassword(txt_contraseña.getText());
        empleado.setTelefono(txt_telefono.getText());
        empleado.setIdSede(cmbSede.getValue().getId());
        RolModelo rolSeleccionado = cmbRol.getValue();
        Set<RolModelo> roles = new HashSet<>();
        roles.add(rolSeleccionado);
        empleado.setRoles(roles);
        empleadoServicio.save(empleado);
        listarEmpleados();
        clear();
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        EmpleadoModelo empleadoSeleccionado = tabla_empleados.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado == null) {
            mostrarAlerta("Seleccione un empleado para actualizar.");
            return;
        }
        String currentCorreo = empleadoSeleccionado.getCorreo();
        if (!validarEntradas(false, currentCorreo)) {
            return;
        }

        empleadoSeleccionado.setNombre(txt_nombre.getText());
        empleadoSeleccionado.setApellido(txt_apellido.getText());
        empleadoSeleccionado.setCorreo(txt_correo.getText());
        empleadoSeleccionado.setPassword(txt_contraseña.getText());
        empleadoSeleccionado.setTelefono(txt_telefono.getText());
        empleadoSeleccionado.setIdSede(cmbSede.getValue().getId());

        RolModelo rolSeleccionado = cmbRol.getValue();
        if (rolSeleccionado != null) {
            Set<RolModelo> roles = new HashSet<>();
            roles.add(rolSeleccionado);
            empleadoSeleccionado.setRoles(roles);
        }

        empleadoServicio.save(empleadoSeleccionado);
        listarEmpleados();
        clear();
        btnLimpiar();
    }

    @FXML
    private void listarEmpleados() {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnRol.setCellValueFactory(new PropertyValueFactory<>("rolNombres"));
        columnSede.setCellValueFactory(new PropertyValueFactory<>("sede"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

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

    private void cargarRoles() {
        cmbRol.getItems().setAll(rolServicio.findAll());
    }

    private void cargarSedes() {
        cmbSede.getItems().setAll(sedeServicio.listaSedes());
    }

    //Metodos de apoyo
    @FXML
    void actLimpiar(ActionEvent event) {
        btnLimpiar();
    }

    @FXML
    public void clear() {
        txt_dni.setText("");
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_correo.setText("");
        txt_contraseña.setText("");
        txt_telefono.setText("");
        cmbSede.setValue(null);
        cmbRol.setValue(null);
        txt_dni.setDisable(false);

        // Clear error labels and reset styles
        error_Dni.setText("");
        error_Correo.setText("");
        error_tel.setText("");
        error_sede.setText("");
        error_rol.setText("");
        error_contra.setText("");
        error_nombre.setText("");
        error_apellido.setText("");

        txt_dni.setStyle("");
        txt_nombre.setStyle("");
        txt_apellido.setStyle("");
        txt_correo.setStyle("");
        txt_contraseña.setStyle("");
        txt_telefono.setStyle("");
        cmbRol.setStyle("");
        cmbSede.setStyle("");
    }

    public void btnLimpiar() {
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
            cmbSede.setValue(empleadoSeleccionado.getSede());
            RolModelo rol = empleadoSeleccionado.getRoles().stream().findFirst().orElse(null);
            cmbRol.setValue(rol);
            txt_dni.setDisable(true);
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.show();
    }

    @FXML
    public void handleKeyEmpleado(KeyEvent event) {
        if (txt_dni.getText().length() < 8) {
            txt_nombre.clear();
            txt_apellido.clear();
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
                    if (response.isSuccessful() && response.body() != null) {
                        ReniecRespuesta datosPersona = response.body();
                        txt_nombre.setText(datosPersona.getNombres());
                        txt_apellido.setText(
                                datosPersona.getApellidoPaterno() + " " + datosPersona.getApellidoMaterno()
                        );
                    } else {
                        error_Dni.setText("DNI no encontrado");
                    }
                }

                @Override
                public void onFailure(Call<ReniecRespuesta> call, Throwable t) {
                    mostrarAlerta("Error al conectarse al servicio: " + t.getMessage());
                }
            });
        }
    }

}
