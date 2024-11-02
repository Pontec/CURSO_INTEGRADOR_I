package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.RutaModelo;
import com.utp.viacosta.servicio.AsientoServicio;
import com.utp.viacosta.servicio.AsignacionBusRutaServicio;
import com.utp.viacosta.servicio.RutaServicio;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class FacturacionControlador implements Initializable {
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
    private AsientoServicio asientoServicio;
    @Autowired
    private RutaServicio rutaServicio;
    @Autowired
    private AsignacionBusRutaServicio asignacionBusRutaService;
    @FXML
    private DatePicker dateFechaViaje;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fechaPredefinida();
        cargarRutas();
    }

    private void cargarRutas(){
        List<RutaModelo> rutas = rutaServicio.listarRutas();
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

    private void setearBus(List<AsignacionBusRutaModelo> listaItinerario){
        gridBuses.getChildren().clear();
        gridAsientos.getChildren().clear();
        List<AsignacionBusRutaModelo> asignaciones = listaItinerario;
        int fila = 0;
        for (AsignacionBusRutaModelo asignacion : asignaciones) {
            Button boton = generarBotonItinerario(asignacion);
            gridBuses.add(boton,0,fila);
            fila++;
        }
    }

    private void setearAsientos(List<AsientoModelo> asientos) {
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

    private Button generarBotonItinerario(AsignacionBusRutaModelo asignacion){
        List<AsientoModelo> asientos = asientoServicio.getAsientosPorBus(asignacion.getIdBus());
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

    private void fechaPredefinida(){
        if (dateFechaViaje != null) {
            dateFechaViaje.setValue(LocalDate.now());
            dateFechaViaje.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                        setStyle("-fx-background-color: #cccccc;");
                    }
                }
            });
        }
    }

    @FXML
    public void mostrarViajes(ActionEvent actionEvent) {
        if (dateFechaViaje.getValue() != null && cmbOrigen.getValue() != null && cmbDestino.getValue() != null) {
            LocalDate fecha = dateFechaViaje.getValue();
            String origen = cmbOrigen.getValue().toString();
            String destino = cmbDestino.getValue().toString();
            List<AsignacionBusRutaModelo> listaAsignaciones = asignacionBusRutaService.findByRutaAsignadaOrigenAndRutaAsignadaDestinoAndFechaSalida(origen, destino, fecha);
            setearBus(listaAsignaciones);
        }
    }


}

