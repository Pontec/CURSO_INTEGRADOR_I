package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.respuesta.ReniecRespuesta;
import com.utp.viacosta.agregates.retrofit.ReniecService;
import com.utp.viacosta.agregates.retrofit.api.ReniecCliente;
import com.utp.viacosta.dao.AsientoDAO;
import com.utp.viacosta.modelo.*;
import com.utp.viacosta.modelo.enums.Estado;
import com.utp.viacosta.servicio.*;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtCargoExtra;
    @FXML
    private TextField txtHoraSalida;
    @FXML
    private TextField txtFechaSalida;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNumAsiento;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtEfectivo;
    @FXML
    private TextField txtSubtotal;
    @FXML
    private TextField txtIGV;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField txtCambio;
    @Autowired
    private AsientoServicio asientoServicio;
    @Autowired
    private RutaServicio rutaServicio;
    @Autowired
    private AsignacionBusRutaServicio asignacionBusRutaService;
    @Autowired
    private ComprobanteServicio comprobanteServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private DetalleBoletaServicio detalleBoletaServicio;
    @Autowired
    private CompraServicio compraServicio;
    @Value("${token.api}")
    private String tokenApi;
    private List<Button> botonesAsientos = new ArrayList<>();
    int idAsiento = 0;
    AsignacionBusRutaModelo asignacionAux = new AsignacionBusRutaModelo();
    @FXML
    private ComboBox cmbMetodoPago;
    @FXML
    private TextField txtTipoBoleta;
    @Autowired
    private AsientoDAO asientoDAO;
    @Autowired
    private AsientoEstadoFechaServicio asientoEstadoFechaServicio;


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

    private void setearAsientos(List<AsientoModelo> asientos, GridPane gridAsientos, int cantAsientos, AsignacionBusRutaModelo asignacion) {
        LocalDate fechaViaje = asignacion.getFechaSalida();
        LocalTime horaViaje = asignacion.getHoraSalida();

        int index = 0;
        int totalColumnas = 9;
        int totalFilas = 4;
        for (int col = 0; col < totalColumnas ; col++) {
            for (int fila = 0; fila <= totalFilas; fila++) {
                if(index >= cantAsientos) return;
                if(fila == 2) continue;

                AsientoModelo asientoActual = asientos.get(index);

                boolean estaOcupado = asientoEstadoFechaServicio.estaAsientoOcupado(
                        asientoActual.getIdAsiento(),
                        fechaViaje,
                        horaViaje
                );
                Button botonAsiento = crearBotonAsiento(asientoActual, index + 1, asignacion, estaOcupado);
                gridAsientos.add(botonAsiento, col, fila);
                GridPane.setMargin(botonAsiento, new Insets(3));
                index++;
            }
        }
    }

    private Button crearBotonAsiento(AsientoModelo asiento, int numeroAsiento, AsignacionBusRutaModelo asignacion, boolean estaOcupado) {
        // Crear el botón del asiento con icono
        Button botonAsiento = new Button(String.valueOf(numeroAsiento));
        botonAsiento.setPrefSize(60, 40);

        Image imagen = new Image(getClass().getResourceAsStream("/img/icon-chair.png"));
        ImageView imageView = new ImageView(imagen);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        botonAsiento.setGraphic(imageView);

        // Añadir el botón a la lista de botones
        botonesAsientos.add(botonAsiento);

        // Almacenar la asignación en el botón
        botonAsiento.setUserData(asignacion);

        // Verificar el estado del asiento
        if (estaOcupado) {
            botonAsiento.getStyleClass().add("asiento-ocupado");
            botonAsiento.setDisable(true);
        } else {
            botonAsiento.getStyleClass().add("asiento-disponible");
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

                    // Capturar la información de la asignación
                    AsignacionBusRutaModelo asignacionSeleccionada = (AsignacionBusRutaModelo) botonAsiento.getUserData();
                    txtNumAsiento.setText(String.valueOf(numeroAsiento));
                    txtCargoExtra.setText(String.valueOf(asiento.getPrecio()));
                    txtFechaSalida.setText(asignacionSeleccionada.getFechaSalida().toString());
                    txtHoraSalida.setText(asignacionSeleccionada.getHoraSalida().toString());
                    idAsiento = asiento.getIdAsiento();
                    asignacionAux = asignacionSeleccionada;
                    setearDatosPago(asiento.getPrecio());

                } else {
                    // Cambiar el estado del botón seleccionado a disponible
                    botonAsiento.getStyleClass().remove("asiento-ocupado");
                    botonAsiento.getStyleClass().add("asiento-disponible");
                }
            });
        }

        return botonAsiento;
    }

    private void setearDatosPago(double precio){
        precio = 50;
        txtSubtotal.setText(String.format("%.2f", precio / 1.18));
        txtIGV.setText(String.format("%.2f", precio - (precio / 1.18)));
        txtTotal.setText(String.format("%.2f", precio));
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
            setearAsientos(asientos, gridPrimerPiso, asignacion.getBusAsignado().getPrimerPiso(), asignacion);
            setearAsientos(asientos, gridSegundoPiso, asignacion.getBusAsignado().getSegundoPiso(), asignacion);
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
        txtTipoBoleta.setText("Boleta");
        int numeroDocumento = comprobanteServicio.countByTipoComprobante("Boleta") + 1;
        dateFechaBoleto.setValue(LocalDate.now());
        embarque.setText("Terminal Terrestre de Chimbote");
        numeroDoc.setText("B001 - "+ numeroDocumento);
        cargarMetodosPago();
    }

    public void cargarMetodosPago(){
        ObservableList<String> metodosPago = FXCollections.observableArrayList("Efectivo", "Tarjeta de crédito");
        FxmlCargarUtil.cargarComboBox(metodosPago, cmbMetodoPago);
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

    @FXML
    public void datosClienteReniec(Event event) {
        if (txtDni.getText().length() < 8) {
            txtNombre.setText("");
            txtApellido.setText("");
            return;
        }

        if (txtDni.getText().length() == 8) {
            Retrofit retrofit = ReniecCliente.getClient();
            ReniecService reniecService = retrofit.create(ReniecService.class);
            String token = "Bearer " + tokenApi;
            Call<ReniecRespuesta> call = reniecService.getDatosPersona(token, txtDni.getText());
            call.enqueue(new Callback<ReniecRespuesta>() {
                @Override
                public void onResponse(Call<ReniecRespuesta> call, Response<ReniecRespuesta> response) {
                    if (response.isSuccessful()) {
                        ReniecRespuesta datosPersona = response.body();
                        txtNombre.setText(datosPersona.getNombres());
                        txtApellido.setText(datosPersona.getApellidoPaterno() + " " + datosPersona.getApellidoMaterno());
                    } else {
                        mostrarAlerta("Error en la respuesta: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<ReniecRespuesta> call, Throwable t) {
                    mostrarAlerta("Error en la llamada: " + t.getMessage());
                }
            });
        }

    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.show();
    }

    @FXML
    public void calcularCambio(ActionEvent actionEvent) {
        if (Double.parseDouble(txtEfectivo.getText()) < Double.parseDouble(txtTotal.getText())) {
            mostrarAlerta("El efectivo no puede ser menor al total");
            return;
        }
        double efectivo = Double.parseDouble(txtEfectivo.getText());
        double total = Double.parseDouble(txtTotal.getText());
        txtCambio.setText(String.valueOf(efectivo - total));
    }

    @FXML
    public void procesarVenta(Event event) {
        ClienteModelo cliente = clienteServicio.guardarCliente(txtNombre.getText(), txtApellido.getText(), txtDni.getText(), txtTelefono.getText(), txtDireccion.getText());
        CompraModelo compra = compraServicio.saveCompra(cliente.getIdCliente(), 1);
        ComprobanteModelo comprobante = comprobanteServicio.guardarComprobante("Boleta", numeroDoc.getText(), dateFechaBoleto.getValue());
        AsientoModelo asiento = asientoDAO.findById(idAsiento).orElseThrow();
        asientoEstadoFechaServicio.marcarAsientoOcupado(asiento,asignacionAux.getFechaSalida(),asignacionAux.getHoraSalida(),asignacionAux);
        DetalleBoletaModelo detalleBoleta = detalleBoletaServicio.save("Boleto de viaje",asignacionAux.getFechaSalida(), asignacionAux.getHoraSalida(), "Efectivo", Double.parseDouble(txtTotal.getText()), comprobante.getIdComprobante(), asiento.getNumeroAsiento(), 1, compra.getIdCompra());
    }
}

