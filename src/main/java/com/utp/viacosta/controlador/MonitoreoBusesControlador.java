package com.utp.viacosta.controlador;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MonitoreoBusesControlador implements Initializable {
    @javafx.fxml.FXML
    private AnchorPane panelBusRuta;
    @javafx.fxml.FXML
    private AnchorPane contenedorPanelFact;
    @javafx.fxml.FXML
    private GridPane gridBuses;
    @javafx.fxml.FXML
    private Pane panelPlanillaPasajeros;
    @FXML
    private Pane panelSeguimiento;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void btnBusesEnMarcha(Event event) {
        cambiarPanel(true);
    }

    @FXML
    public void fnExportarExcel(Event event) {
    }

    @FXML
    public void btnBusesEstacionados(Event event) {
        cambiarPanel(false);
    }

    private void cambiarPanel(boolean isBusesEnMarcha) {
        if(isBusesEnMarcha){
            panelSeguimiento.setVisible(isBusesEnMarcha);
            panelPlanillaPasajeros.setVisible(!isBusesEnMarcha);
        }else{
            panelSeguimiento.setVisible(isBusesEnMarcha);
            panelPlanillaPasajeros.setVisible(!isBusesEnMarcha);
        }
    }

    @FXML
    public void fnPonerEnMarcha(Event event) {
    }
}
