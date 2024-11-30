package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.BusModelo;
import com.utp.viacosta.modelo.TipoAsientoModelo;
import com.utp.viacosta.servicio.AsientoServicio;
import com.utp.viacosta.servicio.BusServicio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private Label lblNumeroAsiento;
    @FXML
    private TextField txtTipoAsiento;
    @FXML
    private TextField txtPrecioAsiento;
    @FXML
    private Button btnActualizar;



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

        int index = 0;

        // Llenar la primera fila (9 primeros asientos)
        for (int col = 0; col < 9; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            asientosGridPane.add(botonAsiento, col, 0);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // Llenar la segunda fila (9 siguientes asientos)
        for (int col = 0; col < 9; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            asientosGridPane.add(botonAsiento, col, 1);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // La tercera fila está vacía, no hacemos nada

        // Llenar la cuarta fila (9 siguientes asientos)
        for (int col = 0; col < 9; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            asientosGridPane.add(botonAsiento, col, 3);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // Llenar la quinta fila (9 siguientes asientos)
        for (int col = 0; col < 9; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            asientosGridPane.add(botonAsiento, col, 4);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // La sexta fila está vacía, no hacemos nada

        // Llenar la séptima fila (5 primeros asientos)
        for (int col = 0; col < 5; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            asientosGridPane.add(botonAsiento, col, 6);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }

        // Llenar la octava fila (5 primeros asientos)
        for (int col = 0; col < 5; col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            asientosGridPane.add(botonAsiento, col, 7);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }
        // La novena fila está vacía, no hacemos nada

        // Llenar la décima fila con los asientos restantes
        for (int col = 0; col < 9 && index < asientos.size(); col++) {
            Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
            asientosGridPane.add(botonAsiento, col, 9);
            GridPane.setMargin(botonAsiento, new Insets(3));
            index++;
        }


    }

    private Button crearBotonAsiento(AsientoModelo asiento, int numeroAsiento) {
        // Crear el botón del asiento con icono
        Button botonAsiento = new Button(String.valueOf(numeroAsiento));
        botonAsiento.getStyleClass().add("asiento-disponible");
        botonAsiento.setPrefSize(60, 40);

        Image imagen = new Image(getClass().getResourceAsStream("/img/icon-chair.png"));
        ImageView imageView = new ImageView(imagen);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        botonAsiento.setGraphic(imageView);

        // Acción al hacer clic en el botón
        botonAsiento.setOnAction(event -> {
            lblNumeroAsiento.setText(String.valueOf(numeroAsiento));
            // Mostrar el tipo de asiento en el TextField
            String tipoAsiento = String.valueOf(asiento.getTipoAsiento());
            txtTipoAsiento.setText(tipoAsiento);

            double precio = asiento.getPrecio();
            txtPrecioAsiento.setText(String.format("%.2f", precio)); // dos decimales


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
    private void guardarCambios() {
        // Verificamos si hay un asiento seleccionado
        AsientoModelo asientoSeleccionado = obtenerAsientoSeleccionado();
        if (asientoSeleccionado != null) {
            try {
                // Obtener el nuevo tipo de asiento desde el ComboBox o TextField
                String tipoAsientoStr = txtTipoAsiento.getText().trim().toUpperCase();
                TipoAsientoModelo tipoAsiento = obtenerTipoAsiento(tipoAsientoStr);

                // Obtener el nuevo precio del asiento desde el TextField
                double precio = Double.parseDouble(txtPrecioAsiento.getText().trim());

                // Actualizamos el asiento con los nuevos datos
                asientoSeleccionado.setTipoAsiento(tipoAsiento);
                asientoSeleccionado.setPrecio(precio);

                // Guardamos los cambios usando el servicio
                asientoServicio.actualizarAsiento(asientoSeleccionado);

                // Mostrar mensaje de éxito
                mostrarMensajeExito("Asiento actualizado correctamente.");

            } catch (NumberFormatException e) {
                mostrarMensajeError("El precio debe ser un valor numérico.");
            } catch (Exception e) {
                mostrarMensajeError("Error al actualizar el asiento: " + e.getMessage());
            }
        } else {
            mostrarMensajeError("Seleccione un asiento para actualizar.");
        }
    }

    private void mostrarMensajeExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actualización Exitosa");
        alert.setHeaderText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(mensaje);
        alert.showAndWait();
    }



}
