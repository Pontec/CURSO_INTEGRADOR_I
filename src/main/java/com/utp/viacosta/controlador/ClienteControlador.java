package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.respuesta.ReniecRespuesta;
import com.utp.viacosta.agregates.retrofit.ReniecService;
import com.utp.viacosta.agregates.retrofit.api.ReniecCliente;
import com.utp.viacosta.modelo.ClienteModelo;
import com.utp.viacosta.servicio.ClienteServicio;
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
import java.util.List;
import java.util.ResourceBundle;


@Component
public class ClienteControlador implements Initializable {

    @Autowired
    private ClienteServicio clienteServicio;

    //el token se encuentra en properties
    @Value("${token.api}")
    private String tokenApi;

    @FXML
    private Button btn_actualizar, btn_guardar, btnLimpiar;
    @FXML
    private TableColumn<ClienteModelo, String> columnApellido;
    @FXML
    private TableColumn<ClienteModelo, String> columnCorreo;
    @FXML
    private TableColumn<ClienteModelo, String> columnDni;
    @FXML
    private TableColumn<ClienteModelo, String> columnId;
    @FXML
    private TableColumn<ClienteModelo, String> columnNombre;
    @FXML
    private TableColumn<ClienteModelo, String> columnTelefono;
    @FXML
    private TableView<ClienteModelo> tabla_clientes;

    @FXML
    private TextField txt_apellido;
    @FXML
    private Label error_Dni;
    @FXML
    private Label error_tel;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_dni;
    @FXML
    private TextField txt_direccion, txtBuscar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarClientes();
        inicializarValidacion();

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
        // Añadir listener al campo de búsqueda
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarClientes(newValue);
    });
    }

    private void inicializarValidacion() {
        agregarValidacionDni(txt_dni, error_Dni);
        agregarValidacionTelefono(txt_telefono, error_tel);
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

    private boolean validarEntradas(boolean validarDni) {
        boolean isValid = true;

        if (validarDni) {
            if (txt_dni.getText().length() != 8 || txt_dni.getText().isEmpty()) {
                error_Dni.setText("El DNI debe tener 8 dígitos.");
                isValid = false;
            } else {
                error_Dni.setText("");
            }

            if (clienteServicio.findByDni(txt_dni.getText()) != null) {
                error_Dni.setText("El DNI ya existe.");
                isValid = false;
            }
        }

        if (!txt_telefono.getText().isEmpty() && txt_telefono.getText().length() != 9) {
            error_tel.setText("El teléfono debe tener 9 dígitos.");
            isValid = false;
        } else {
            error_tel.setText("");
        }

        return isValid;
    }

    @FXML
    void act_save(ActionEvent event) {
        if (!validarEntradas(true)) {
            return;
        }

        ClienteModelo clienteModelo = new ClienteModelo();
        clienteModelo.setNombre(txt_nombre.getText());
        clienteModelo.setApellido(txt_apellido.getText());
        clienteModelo.setDni(txt_dni.getText());
        clienteModelo.setTelefono(txt_telefono.getText());
        clienteModelo.setDireccion(txt_direccion.getText());

        clienteServicio.save(clienteModelo);
        listarClientes();
        limpiar();
        limpiarStyles();
    }


    @FXML
    void act_actualizar(ActionEvent event) {
        if (!validarEntradas(false)) {
            return;
        }
        ClienteModelo clienteModelo = tabla_clientes.getSelectionModel().getSelectedItem();
        clienteModelo.setNombre(txt_nombre.getText());
        clienteModelo.setApellido(txt_apellido.getText());
        clienteModelo.setDni(txt_dni.getText());
        clienteModelo.setCorreo(txt_direccion.getText());
        clienteModelo.setTelefono(txt_telefono.getText());

        clienteServicio.actualizarCliente(clienteModelo);
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

        tabla_clientes.getItems().setAll(clienteServicio.listaClientes());
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
        txt_direccion.setText("");
        txt_telefono.setText("");
        txt_dni.setDisable(false);
    }

    @FXML
    public void seleccionarCliente() {
        ClienteModelo clienteModelo = tabla_clientes.getSelectionModel().getSelectedItem();
        txt_nombre.setText(clienteModelo.getNombre());
        txt_apellido.setText(clienteModelo.getApellido());
        txt_dni.setText(clienteModelo.getDni());
        txt_direccion.setText(clienteModelo.getDireccion());
        txt_telefono.setText(clienteModelo.getTelefono());

        txt_dni.setDisable(true);
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
        txt_direccion.setStyle("");
        txt_telefono.setStyle("");
    }


    @FXML
    public void actBuscar(Event event) {
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

    private void buscarClientes(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            listarClientes(); // Mostrar todos los clientes si no hay texto
        } else {
            List<ClienteModelo> clientesFiltrados = clienteServicio.buscarClientes(searchText);
            tabla_clientes.getItems().setAll(clientesFiltrados);
        }
    }

    @FXML
    public void btnRefresh(Event event) {
        txtBuscar.setText("");
        listarClientes();
    }
}
