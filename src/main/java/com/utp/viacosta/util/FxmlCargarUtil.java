package com.utp.viacosta.util;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class FxmlCargarUtil {

    private static ApplicationContext springContext;

    // Metodo para configurar el contexto de Spring
    public static void setApplicationContext(ApplicationContext context) {
        springContext = context;
    }

    // Metodo utilitario para cargar un archivo FXML y aplicar inyección de
    // dependencias
    public static Parent load(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FxmlCargarUtil.class.getResource(fxmlPath));
        fxmlLoader.setControllerFactory(springContext::getBean); // Inyección de dependencias con Spring
        return fxmlLoader.load();
    }

    public static void cargarComboBox(ObservableList<String> listaCmb, ComboBox<String> cmb) {
        cmb.setItems(listaCmb);
        if (!listaCmb.isEmpty()) {
            cmb.getSelectionModel().select(0);
            cmb.requestFocus();
            cmb.fireEvent(new javafx.event.ActionEvent());
        }
    }

}
