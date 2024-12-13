package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;
import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.BusModelo;
import com.utp.viacosta.modelo.DetalleBoletaModelo;
import com.utp.viacosta.modelo.enums.EstadoAsignacion;
import com.utp.viacosta.servicio.AsignacionBusRutaServicio;
import com.utp.viacosta.servicio.BusServicio;
import com.utp.viacosta.servicio.DetalleBoletaServicio;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    @Autowired
    private AsignacionBusRutaServicio asignacionBusRutaService;
    @Autowired
    private DetalleBoletaServicio boletaService;
    @Autowired
    private BusServicio busService;

    boolean isButtonSelected = false;
    @FXML
    private TableColumn colAsiento;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colVendedor;
    @FXML
    private TableView tablaPasajeros;
    @FXML
    private TableColumn colRuta;
    @FXML
    private TableColumn colBus;
    @FXML
    private TableColumn colFirma;
    @FXML
    private GridPane gridBusesEnMarcha;
    @FXML
    private ImageView imageBus;
    AsignacionBusRutaModelo asignacionSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mostrarViajesProgramados(EstadoAsignacion.PROGRAMADO, gridBuses);
    }

    @FXML
    public void btnBusesEnMarcha(Event event) {
        cambiarPanel(true);
        gridBuses.getChildren().clear();
        gridBuses.setVisible(false);
        gridBusesEnMarcha.setVisible(true);
        mostrarViajesProgramados(EstadoAsignacion.EN_CURSO, gridBusesEnMarcha);
    }

    @FXML
    public void fnExportarExcel(Event event) {
    }

    @FXML
    public void btnBusesEstacionados(Event event) {
        cambiarPanel(false);
        gridBusesEnMarcha.getChildren().clear();
        gridBusesEnMarcha.setVisible(false);
        gridBuses.setVisible(true);
        mostrarViajesProgramados(EstadoAsignacion.PROGRAMADO, gridBuses);
    }

    private void cambiarPanel(boolean isBusesEnMarcha) {
        if (isBusesEnMarcha) {
            panelSeguimiento.setVisible(isBusesEnMarcha);
            panelPlanillaPasajeros.setVisible(!isBusesEnMarcha);
        } else {
            panelSeguimiento.setVisible(isBusesEnMarcha);
            panelPlanillaPasajeros.setVisible(!isBusesEnMarcha);
        }
    }

    @FXML
    public void fnPonerEnMarcha(Event event) {
        if (isButtonSelected) {
            if (mostrarConfirmacion("\"¿Está seguro de poner en marcha el bus?\"")) {
                AsignacionBusRutaModelo asignacionUpdate = asignacionSeleccionada;
                asignacionUpdate.setEstado(EstadoAsignacion.EN_CURSO);
                asignacionBusRutaService.save(asignacionUpdate);
                mostrarViajesProgramados(EstadoAsignacion.PROGRAMADO, gridBuses);
                asignacionSeleccionada = null;
                isButtonSelected = false;
                tablaPasajeros.getItems().clear();
            }
        }
    }

    public void mostrarViajesProgramados(EstadoAsignacion estado, GridPane gridBuses) {
        isButtonSelected = false;
        LocalDate fecha = LocalDate.now();
        List<AsignacionBusRutaModelo> listaAsignaciones = asignacionBusRutaService.buscarAsignacionesDelDia(fecha, estado);
        setearBus(listaAsignaciones, gridBuses);
    }

    private void setearBus(List<AsignacionBusRutaModelo> listaItinerario, GridPane gridBuses) {
        gridBuses.getChildren().clear();
        List<AsignacionBusRutaModelo> asignaciones = listaItinerario;
        int fila = 0;
        for (AsignacionBusRutaModelo asignacion : asignaciones) {
            Button boton = generarBotonItinerario(asignacion);
            gridBuses.add(boton, 0, fila);
            fila++;
        }
    }

    private Button generarBotonItinerario(AsignacionBusRutaModelo asignacion) {

        Button botonBus = new Button();
        botonBus.setPrefSize(Double.MAX_VALUE, 40);
        botonBus.getStyleClass().add("boton-itinerario");
        botonBus.setText(asignacion.getRutaAsignada().getOrigen() + " - " + asignacion.getRutaAsignada().getDestino() + "\n" + asignacion.getHoraSalida().toString());
        Image imagen = new Image(getClass().getResourceAsStream("/img/icon-bus.png"));
        ImageView imageView = new ImageView(imagen);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        botonBus.setGraphic(imageView);
        botonBus.setOnAction(event -> {
            asignacionSeleccionada = asignacion;
            if (asignacion.getEstado() == EstadoAsignacion.PROGRAMADO) {
                listarPasajeros(asignacion);
            } else {
                imageBus.setVisible(true);
            }
            isButtonSelected = true;
            gridBuses.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    node.getStyleClass().remove("boton-itinerario-active");
                }
            });
            botonBus.getStyleClass().add("boton-itinerario-active");
        });
        return botonBus;
    }

    public void listarPasajeros(AsignacionBusRutaModelo asignacion) {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colAsiento.setCellValueFactory(new PropertyValueFactory<>("asiento"));
        colRuta.setCellValueFactory(new PropertyValueFactory<>("ruta"));
        colVendedor.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        colBus.setCellValueFactory(cellData -> {
            BusModelo busModelo = busService.findById(asignacion.getIdBus()).orElseThrow();
            System.out.println(asignacion);
            return new SimpleStringProperty(busModelo.getPlaca());
        });

        List<DetalleBoletaDTO> pasajeros = boletaService.getAllReporteVentas()
                .stream()
                .filter(p -> p.getRuta().equals(asignacion.getRutaAsignada().getOrigen() + " - " + asignacion.getRutaAsignada().getDestino())
                        && p.getFechaSalida().equals(asignacion.getFechaSalida())
                        && p.getHoraSalida().equals(asignacion.getHoraSalida()))
                .collect(Collectors.toList());

        tablaPasajeros.getItems().setAll(pasajeros);
    }

    private boolean mostrarConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE);

        return alert.showAndWait().filter(response -> response == buttonTypeYes).isPresent();
    }

    @FXML
    public void fnFinalizarViaje(Event event) {
        if (isButtonSelected) {
            if (mostrarConfirmacion("\"¿Está seguro de finalizar el viaje?\"")) {
                AsignacionBusRutaModelo asignacionUpdate = asignacionSeleccionada;
                asignacionUpdate.setEstado(EstadoAsignacion.COMPLETADO);
                asignacionBusRutaService.save(asignacionUpdate);
                mostrarViajesProgramados(EstadoAsignacion.EN_CURSO, gridBusesEnMarcha);
                asignacionSeleccionada = null;
                isButtonSelected = false;
                imageBus.setVisible(false);
            }
        }
    }

    @FXML
    public void fnCancelarMarcha(Event event) {
        if (isButtonSelected) {
            if (mostrarConfirmacion("\"¿Está seguro de cancelar la marcha del bus?\"")) {
                AsignacionBusRutaModelo asignacionUpdate = asignacionSeleccionada;
                asignacionUpdate.setEstado(EstadoAsignacion.PROGRAMADO);
                asignacionBusRutaService.save(asignacionUpdate);
                mostrarViajesProgramados(EstadoAsignacion.EN_CURSO, gridBusesEnMarcha);
                asignacionSeleccionada = null;
                isButtonSelected = false;
                imageBus.setVisible(false);
            }
        }
    }
}
