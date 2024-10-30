package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.respuesta.ReniecRespuesta;
import com.utp.viacosta.agregates.retrofit.ReniecService;
import com.utp.viacosta.agregates.retrofit.api.ReniecCliente;
import com.utp.viacosta.modelo.ClienteModelo;
import com.utp.viacosta.servicio.ClienteServicio;
import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;


@Component
public class ClienteControlador implements Initializable {

    @Autowired
    private ClienteServicio clienteServicio;

    //el token se encuentra en properties
    @Value("${token.api}")
    private String tokenApi;

    @FXML
    private Button btn_actualizar,btn_guardar, btnLimpiar;
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

        ClienteModelo clienteModelo = new ClienteModelo();
        clienteModelo.setNombre(txt_nombre.getText());
        clienteModelo.setApellido(txt_apellido.getText());
        clienteModelo.setDni(txt_dni.getText());
        clienteModelo.setCorreo(txt_correo.getText());
        clienteModelo.setTelefono(txt_telefono.getText());

        clienteServicio.guardarCliente(clienteModelo);
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


        tabla_clientes.getItems().setAll(clienteServicio.listaClientes());
    }

    @FXML
    void act_actualizar(ActionEvent event) {
        if (!validarEntradas()) {
            return;
        }
        ClienteModelo clienteModelo = tabla_clientes.getSelectionModel().getSelectedItem();
        clienteModelo.setNombre(txt_nombre.getText());
        clienteModelo.setApellido(txt_apellido.getText());
        clienteModelo.setDni(txt_dni.getText());
        clienteModelo.setCorreo(txt_correo.getText());
        clienteModelo.setTelefono(txt_telefono.getText());

        clienteServicio.actualizarCliente(clienteModelo);
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
        ClienteModelo clienteModelo = tabla_clientes.getSelectionModel().getSelectedItem();
        txt_nombre.setText(clienteModelo.getNombre());
        txt_apellido.setText(clienteModelo.getApellido());
        txt_dni.setText(clienteModelo.getDni());
        txt_correo.setText(clienteModelo.getCorreo());
        txt_telefono.setText(clienteModelo.getTelefono());
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


    @FXML
    public void actBuscar(ActionEvent event) {
        String dni = txt_dni.getText();
        if (dni.isEmpty() || dni.length() != 8) {
            mostrarAlerta("El DNI debe tener 8 dígitos.");
            return;
        }

        Retrofit retrofit = ReniecCliente.getClient();
        ReniecService reniecService = retrofit.create(ReniecService.class);
        String token = "Bearer " + tokenApi;

        Call<ReniecRespuesta> call = reniecService.getDatosPersona(token, dni);
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

