package com.utp.viacosta.controller;

import com.utp.viacosta.model.AsientoModel;
import com.utp.viacosta.model.enums.Estado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private Button volverPanel1;
    @FXML
    private GridPane gridAsientos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setearAsientos(generarAsientosDisponibles(1, 1, 10.0));
    }


    private void setearAsientos(List<AsientoModel> asientos) {
        // Índice para recorrer la lista de asientos
        int index = 0;

        // Llenar la primera fila (9 primeros asientos)
        for (int col = 0; col < 9; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            gridAsientos.add(botonAsiento, col, 0);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // Llenar la segunda fila (9 siguientes asientos)
        for (int col = 0; col < 9; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            gridAsientos.add(botonAsiento, col, 1);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // La tercera fila está vacía, no hacemos nada

        // Llenar la cuarta fila (9 siguientes asientos)
        for (int col = 0; col < 9; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            gridAsientos.add(botonAsiento, col, 3);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // Llenar la quinta fila (9 siguientes asientos)
        for (int col = 0; col < 9; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            gridAsientos.add(botonAsiento, col, 4);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // La sexta fila está vacía, no hacemos nada

        // Llenar la séptima fila (5 primeros asientos)
        for (int col = 0; col < 5; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            gridAsientos.add(botonAsiento, col, 6);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // Llenar la octava fila (5 primeros asientos)
        for (int col = 0; col < 5; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            gridAsientos.add(botonAsiento, col, 7);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // La novena fila está vacía, no hacemos nada

        // Llenar la décima fila con los asientos restantes
        for (int col = 0; col < 9 && index < asientos.size(); col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            gridAsientos.add(botonAsiento, col, 9);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }
    }

    private Button crearBotonAsiento(AsientoModel asiento, int numeroAsiento) {
        // Crear el botón del asiento
        Button botonAsiento = new Button("A " + numeroAsiento);
        botonAsiento.getStyleClass().add("asiento-disponible");

        // Acción al hacer clic en el botón
        botonAsiento.setOnAction(event -> {
            if (botonAsiento.getStyleClass().contains("asiento-disponible")) {
                botonAsiento.getStyleClass().remove("asiento-disponible");
                botonAsiento.getStyleClass().add("asiento-ocupado");
            } else {
                botonAsiento.getStyleClass().remove("asiento-ocupado");
                botonAsiento.getStyleClass().add("asiento-disponible");
            }
        });

        return botonAsiento;
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

    public List<AsientoModel> generarAsientosDisponibles(int idBus, int idTipoAsiento, double precioBase) {
        List<AsientoModel> asientos = new ArrayList<>();

        for (int i = 1; i <= 52; i++) {
            AsientoModel asiento = new AsientoModel();
            asiento.setIdBus(idBus);  // Asignar el ID del bus
            asiento.setNumeroAsiento(i);  // Asignar el número de asiento (1 a 52)
            asiento.setEstado(Estado.DISPONIBLE);  // Asignar estado DISPONIBLE
            asiento.setPrecio(precioBase);  // Asignar el precio base
            asiento.setIdTipoAsiento(idTipoAsiento);  // Asignar el ID del tipo de asiento

            // Añadir asiento a la lista
            asientos.add(asiento);
        }

        return asientos;
    }
}
