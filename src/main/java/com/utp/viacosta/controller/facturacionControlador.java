package com.utp.viacosta.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class facturacionControlador implements Initializable {
    @FXML
    private AnchorPane contenedorPanelFact;
    @FXML
    private AnchorPane panelBusRuta;
    @FXML
    private AnchorPane panelProcesarPago;
    @FXML
    private Button irAPago;
    @FXML
    private Button volverPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void irAPago(ActionEvent actionEvent) {
        isPanelPrimary(false);
    }

    @FXML
    public void volverPanel(ActionEvent actionEvent) {
        isPanelPrimary(true);
    }

    private void isPanelPrimary(boolean panel) {
        if (panel) {
            panelBusRuta.setVisible(true);
            panelProcesarPago.setVisible(false);
        } else {
            panelBusRuta.setVisible(false);
            panelProcesarPago.setVisible(true);
        }
    }
}
