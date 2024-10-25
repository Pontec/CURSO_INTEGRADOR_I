package com.utp.viacosta.controller;

import com.utp.viacosta.model.AsientoModel;
import com.utp.viacosta.model.BusModel;
import com.utp.viacosta.model.RolModel;
import com.utp.viacosta.service.AsientoService;
import com.utp.viacosta.service.BusService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AsientosControlador implements Initializable {

    @Autowired
    private AsientoService asientoService;
    @Autowired
    private BusService busService;

    @FXML
    private ComboBox<BusModel> cbocBuses;
    @FXML
    private Button btnAsientos;
    @FXML
    private GridPane asientosGridPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar los buses en el ComboBox
        cargarBuses();

        // Configurar el evento para cargar los asientos al seleccionar un bus
        cbocBuses.setOnAction(event -> {
            BusModel busSeleccionado = cbocBuses.getValue();
            cargarAsientosPorBus(busSeleccionado);
        });
    }

    private  void cargarBuses(){
        List<BusModel> buses = busService.findAll();
        cbocBuses.setItems(FXCollections.observableArrayList(buses));
    }





    public void cargarAsientosPorBus(BusModel busSeleccionado) {
        List<AsientoModel> asientos = asientoService.getAsientosPorBus(busSeleccionado.getIdBus());
        // Limpiar el GridPane antes de agregar nuevos botones
        asientosGridPane.getChildren().clear();

        // Dibujar los asientos en el GridPane
        int column = 0;
        int row = 0;
        final int MAX_COLUMNS = 4; // Máximo número de columnas (asientos por fila)
        for (AsientoModel asiento : asientos) {
            Button asientoButton = new Button("A " + asiento.getNumeroAsiento() + "-" + asiento.getTipoAsiento().getNombre());



            // Configurar el color del botón según el estado del asiento
            switch (asiento.getEstado()) {
                case DISPONIBLE:
                    asientoButton.setStyle("-fx-background-color: green;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 20px;" +
                            "-fx-width: 200px;" +
                            "-fx-height: 100px;");
                    break;
                case OCUPADO:
                    asientoButton.setStyle("-fx-background-color: red;");
                    break;
            }

            // Agregar evento para cambiar el estado del asiento al hacer clic
            //asientoButton.setOnAction(event -> manejarClickEnAsiento(asiento));

            // Añadir el botón al GridPane
            asientosGridPane.add(asientoButton, column, row);

            // Aumentar la columna para el próximo asiento
            column++;
            if (column == MAX_COLUMNS) {
                column = 0;
                row++;
            }
        }
    }


}
