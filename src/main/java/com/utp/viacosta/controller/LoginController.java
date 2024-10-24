package com.utp.viacosta.controller;
import com.utp.viacosta.model.EmpleadoModel;
import com.utp.viacosta.service.impl.EmpleadoServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    @Autowired
    private EmpleadoServiceImpl empleadoServiceImpl;
    @FXML
    private TextField txt_correo;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Button btn_ingresar;

    @FXML
    void Login(ActionEvent event) throws IOException {
        Login();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // Metodo que maneja el inicio de sesión
    public void Login() throws IOException {
        String correo = txt_correo.getText();
        String password = txt_password.getText();


        // Buscar el usuario por correo
        EmpleadoModel usuario = empleadoServiceImpl.findByCorreo(correo);

        if (usuario != null && usuario.getPassword().equals(password)) {
            //mostrarAlerta("Inicio de sesión exitoso", "Bienvenido " + usuario.getCorreo(), Alert.AlertType.INFORMATION);
            // Llamar al metodo para abrir la nueva ventana
            abrirNuevaVentana(usuario);

        } else {
            mostrarAlerta("Error de autenticación", "Correo o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    // Metodo para mostrar alertas en JavaFX
    public void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    // Metodo para abrir la nueva ventana o cambiar de escena
    public void abrirNuevaVentana(EmpleadoModel usuario) throws IOException {
        // Crear un FXMLLoader para cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VentanaPrincipal.fxml"));

        // Cargar el archivo FXML
        Parent root = loader.load();

        // Obtener el controlador del archivo FXML cargado
        VentanaPrincipalController ventanaController = loader.getController();
        ventanaController.setEmpleadoModel(usuario); // Configura el usuario actual en el controlador

        // Obtener el stage actual desde el botón o la ventana
        Stage stage = (Stage) btn_ingresar.getScene().getWindow();

        // Cambiar la escena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}