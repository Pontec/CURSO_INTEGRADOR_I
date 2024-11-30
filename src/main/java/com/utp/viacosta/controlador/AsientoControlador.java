package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.BusModelo;
import com.utp.viacosta.servicio.AsientoEstadoFechaServicio;
import com.utp.viacosta.servicio.AsientoServicio;
import com.utp.viacosta.servicio.BusServicio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AsientoControlador implements Initializable {

    @Autowired
    private AsientoServicio asientoServicio;
    @Autowired
    private BusServicio busServicio;
    @Autowired
    private AsientoEstadoFechaServicio asientoEstadoFechaServicio;

    @FXML
    private ComboBox<BusModelo> cbocBuses;
    @FXML
    private GridPane asientosGridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar los buses en el ComboBox
        cargarBuses();

        // Configurar el evento para cargar los asientos al seleccionar un bus
        cbocBuses.setOnAction(event -> {
            BusModelo busSeleccionado = cbocBuses.getValue();
            cargarAsientosPorBus(busSeleccionado);
        });
    }

    private void cargarBuses() {
        List<BusModelo> buses = busServicio.findAll();
        cbocBuses.setItems(FXCollections.observableArrayList(buses));
    }

    public void cargarAsientosPorBus(BusModelo busSeleccionado) {
        List<AsientoModelo> asientos = asientoServicio.getAsientosPorBus(busSeleccionado.getIdBus());
        // Limpiar el GridPane antes de agregar nuevos botones
        asientosGridPane.getChildren().clear();

        // Dibujar los asientos en el GridPane
        int column = 0;
        int row = 0;
        final int MAX_COLUMNS = 4; // Máximo número de columnas (asientos por fila)
        LocalDate fechaViaje = LocalDate.now(); // Replace with actual date
        LocalTime horaViaje = LocalTime.now(); // Replace with actual time

        for (AsientoModelo asiento : asientos) {
            boolean estaOcupado = asientoEstadoFechaServicio.estaAsientoOcupado(asiento.getIdAsiento(), fechaViaje, horaViaje);
            Button asientoButton = crearBotonAsiento(asiento, estaOcupado);

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

    private Button crearBotonAsiento(AsientoModelo asiento, boolean estaOcupado) {
        Button asientoButton = new Button("A " + asiento.getNumeroAsiento() + "-" + asiento.getTipoAsiento().getNombre());

        // Configurar el color del botón según el estado del asiento
        if (estaOcupado) {
            asientoButton.setStyle("-fx-background-color: red;");
            asientoButton.setDisable(true);
        } else {
            asientoButton.setStyle("-fx-background-color: green;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 20px;" +
                    "-fx-width: 200px;" +
                    "-fx-height: 100px;");
        }

        return asientoButton;
    }
}