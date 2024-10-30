package com.utp.viacosta.controlador;
import com.utp.viacosta.modelo.EmpleadoModelo;
import com.utp.viacosta.servicio.impl.EmpleadoServicioImpl;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginControlador implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Autowired
    private EmpleadoServicioImpl empleadoServiceImpl;
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

    // Metodo que maneja el inicio de sesión
    public void Login() {
        String correo = txt_correo.getText();
        String password = txt_password.getText();
        try {
            EmpleadoModelo usuario = empleadoServiceImpl.autenticar(correo, password);
            abrirNuevaVentana(usuario);
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al abrir la nueva ventana", Alert.AlertType.ERROR);
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
    public void abrirNuevaVentana(EmpleadoModelo usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VentanaPrincipalVista.fxml"));
        Parent root = loader.load();
        VentanaPrincipalControlador ventanaController = loader.getController();
        ventanaController.setEmpleadoModel(usuario); // Configura el usuario actual en el controlador
        Stage stage = (Stage) btn_ingresar.getScene().getWindow();

        // Cambiar la escena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}