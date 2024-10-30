package com.utp.viacosta.controller;

import com.utp.viacosta.model.AsientoModel;
import com.utp.viacosta.model.AsignacionBusRutaModel;
import com.utp.viacosta.model.RutaModel;
import com.utp.viacosta.model.enums.Estado;
import com.utp.viacosta.service.AsientoService;
import com.utp.viacosta.service.AsignacionBusRutaService;
import com.utp.viacosta.service.BusService;
import com.utp.viacosta.service.RutaService;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    @FXML
    private GridPane gridBuses;
    @FXML
    private ComboBox cmbOrigen;
    @FXML
    private ComboBox cmbDestino;
    @Autowired
    private AsientoService asientoService;
    @Autowired
    private RutaService rutaService;
    @Autowired
    private AsignacionBusRutaService asignacionBusRutaService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarRutas();
        setearBus();
    }

    private void cargarRutas(){
        List<RutaModel> rutas = rutaService.listarRutas();
        ObservableList<String> rutasOrigen = FXCollections.observableArrayList(
                rutas.stream()
                        .map(ruta -> ruta.getOrigen())
                        .distinct()
                        .collect(Collectors.toList())
        );
        ObservableList<String> rutasDestino = FXCollections.observableArrayList(
                rutas.stream()
                        .map(ruta -> ruta.getDestino())
                        .distinct()
                        .collect(Collectors.toList())
        );
        FxmlCargarUtil.cargarComboBox(rutasOrigen, cmbOrigen);
        FxmlCargarUtil.cargarComboBox(rutasDestino, cmbDestino);
    }

    private void setearBus(){
        List<AsignacionBusRutaModel> asignaciones = asignacionBusRutaService.findAll();
        int fila = 0;
        for (AsignacionBusRutaModel asignacion : asignaciones) {
            Button boton = generarBotonItinerario(asignacion);
            gridBuses.add(boton,0,fila);
            fila++;
        }
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

    private Button generarBotonItinerario(AsignacionBusRutaModel asignacion){
        List<AsientoModel> asientos = asientoService.getAsientosPorBus(asignacion.getIdBus());
        Button botonBus = new Button();
        botonBus.setPrefSize(Double.MAX_VALUE, 40);
        botonBus.getStyleClass().add("boton-itinerario");
        botonBus.setText(asignacion.getRutaAsignada().getOrigen() + " - " + asignacion.getRutaAsignada().getDestino()+"\n" + asignacion.getHoraSalida().toString());
        Image imagen = new Image(getClass().getResourceAsStream("/img/icon-bus.png"));
        ImageView imageView = new ImageView(imagen);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        botonBus.setGraphic(imageView);
        botonBus.setOnAction(event -> {
            gridAsientos.getChildren().clear();
            botonBus.getStyleClass().add("boton-itinerario-active");
            setearAsientos(asientos);
        });
        return botonBus;
    }
}
