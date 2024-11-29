package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.RutaModelo;
import com.utp.viacosta.servicio.AsientoServicio;
import com.utp.viacosta.servicio.AsignacionBusRutaServicio;
import com.utp.viacosta.servicio.ComprobanteServicio;
import com.utp.viacosta.servicio.RutaServicio;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private GridPane gridBuses;
    @FXML
    private ComboBox cmbOrigen;
    @FXML
    private ComboBox cmbDestino;
    @FXML
    private DatePicker dateFechaViaje;
    @FXML
    private GridPane gridPrimerPiso;
    @FXML
    private GridPane gridSegundoPiso;
    @FXML
    private DatePicker dateFechaBoleto;
    @FXML
    private TextField embarque;
    @FXML
    private TextField numeroDoc;
    @Autowired
    private AsientoServicio asientoServicio;
    @Autowired
    private RutaServicio rutaServicio;
    @Autowired
    private AsignacionBusRutaServicio asignacionBusRutaService;
    @Autowired
    private ComprobanteServicio comprobanteServicio;

    private List<Button> botonesAsientos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datosViajePredefinidos();
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
        gridPrimerPiso.getChildren().clear();
        gridSegundoPiso.getChildren().clear();
        List<AsignacionBusRutaModelo> asignaciones = listaItinerario;
        int fila = 0;
        for (AsignacionBusRutaModelo asignacion : asignaciones) {
            Button boton = generarBotonItinerario(asignacion);
            gridBuses.add(boton,0,fila);
            fila++;
        }
    }

    private void setearAsientos(List<AsientoModelo> asientos,  GridPane gridAsientos, int cantAsientos) {
        if (asientos == null || asientos.isEmpty()) {
            System.out.println("No hay asientos");
            return; // Salir si la lista es nula o está vacía
        }

        int index = 0;
        int totalColumnas = 9;
        int totalFilas = 4;
        for (int col = 0; col < totalColumnas ; col++) {
            for (int fila = 0; fila <= totalFilas; fila++) {
                if(index >= cantAsientos) return;
                if(fila == 2) continue;
                Button botonAsiento = crearBotonAsiento(asientos.get(index), index + 1);
                gridAsientos.add(botonAsiento, col, fila);
                GridPane.setMargin(botonAsiento, new Insets(3));
                index++;
            }
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

        // Añadir el botón a la lista de botones
        botonesAsientos.add(botonAsiento);

        botonAsiento.setOnAction(event -> {
            if (botonAsiento.getStyleClass().contains("asiento-disponible")) {
                // Cambiar el estado de todos los botones a disponible
                for (Button btn : botonesAsientos) {
                    btn.getStyleClass().remove("asiento-ocupado");
                    btn.getStyleClass().add("asiento-disponible");
                }
                // Cambiar el estado del botón seleccionado a ocupado
                botonAsiento.getStyleClass().remove("asiento-disponible");
                botonAsiento.getStyleClass().add("asiento-ocupado");
            } else {
                // Cambiar el estado del botón seleccionado a disponible
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
            gridBuses.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    node.getStyleClass().remove("boton-itinerario-active");
                }
            });
            botonBus.getStyleClass().add("boton-itinerario-active");
            gridPrimerPiso.getChildren().clear();
            gridSegundoPiso.getChildren().clear();
            botonesAsientos.clear();
            setearAsientos(asientos,gridPrimerPiso,asignacion.getBusAsignado().getPrimerPiso());
            setearAsientos(asientos,gridSegundoPiso,asignacion.getBusAsignado().getSegundoPiso());
        });
        return botonBus;
    }

    private void fechaPredefinida(){
        if (dateFechaViaje != null && dateFechaBoleto != null) {
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

    private void datosViajePredefinidos(){
        int numeroDocumento = comprobanteServicio.countByTipoComprobante("Boleta") + 1;
        dateFechaBoleto.setValue(LocalDate.now());
        embarque.setText("Terminal Terrestre de Chimbote");
        numeroDoc.setText("B001 - "+numeroDocumento);
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

