package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.AsignacionBusRutaModelo;
import com.utp.viacosta.modelo.DetalleBoletaModelo;
import com.utp.viacosta.modelo.enums.EstadoAsignacion;
import com.utp.viacosta.servicio.AsignacionBusRutaServicio;
import com.utp.viacosta.servicio.DetalleBoletaServicio;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    boolean isButtonSelected = false;
    @FXML
    private TableColumn colAsiento;
    @FXML
    private TableColumn colDNI;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colVendedor;
    @FXML
    private TableView tablaPasajeros;
    @FXML
    private TableColumn colApellido;
    @FXML
    private TableColumn colRuta;
    @FXML
    private TableColumn colBus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mostrarViajesProgramados();
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
    }

    public void mostrarViajesProgramados() {
        isButtonSelected = false;
        LocalDate fecha = LocalDate.now();
        List<AsignacionBusRutaModelo> listaAsignaciones = asignacionBusRutaService.buscarAsignacionesDelDia(fecha, EstadoAsignacion.PROGRAMADO);
        setearBus(listaAsignaciones);
    }

    private void setearBus(List<AsignacionBusRutaModelo> listaItinerario) {
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
            listarPasajeros();
            isButtonSelected = false;
            gridBuses.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    node.getStyleClass().remove("boton-itinerario-active");
                }
            });
            botonBus.getStyleClass().add("boton-itinerario-active");
        });
        return botonBus;
    }

    public void listarPasajeros(){
            colNombre.setCellValueFactory(new PropertyValueFactory<>("cliente"));
            colAsiento.setCellValueFactory(new PropertyValueFactory<>("asiento"));
            colRuta.setCellValueFactory(new PropertyValueFactory<>("ruta"));
            colVendedor.setCellValueFactory(new PropertyValueFactory<>("responsable"));
            tablaPasajeros.getItems().setAll(boletaService.getAllReporteVentas());
    }
}
