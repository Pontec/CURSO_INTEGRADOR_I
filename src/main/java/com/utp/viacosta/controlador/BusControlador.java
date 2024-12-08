package com.utp.viacosta.controlador;

import com.utp.viacosta.modelo.AsientoEstadoFechaModelo;
import com.utp.viacosta.modelo.AsientoModelo;
import com.utp.viacosta.modelo.BusModelo;
import com.utp.viacosta.modelo.enums.Estado;
import com.utp.viacosta.modelo.enums.TipoAsiento;
import com.utp.viacosta.servicio.AsientoServicio;
import com.utp.viacosta.servicio.BusServicio;
import com.utp.viacosta.servicio.TipoAsientoServicio;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class BusControlador implements Initializable {

    @Autowired
    private BusServicio busServicio;
    @Autowired
    private TipoAsientoServicio tipoAsientoServicio;
    @Autowired
    private AsientoServicio asientoServicio;


    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_guardar;

    @FXML
    private TableColumn<BusModelo, String> columnPlaca;
    @FXML
    private TableColumn<BusModelo, String> columnMarca;
    @FXML
    private TableColumn<BusModelo, String> columnModelo;
    @FXML
    private TableColumn<BusModelo, String> columnAsientoTotal;
    @FXML
    private TableColumn<BusModelo, String> columnId;
    @FXML
    private TableColumn<BusModelo, String> columnCarga;
    @FXML
    private TableView<BusModelo> tablaBuses;

    @FXML
    private TextField txt_placa;
    @FXML
    private TextField txt_marca;
    @FXML
    private TextField txt_modelo;
    @FXML
    private TextField txt_carga_maxima;
    @FXML
    private TextField txtPiso2;
    @FXML
    private TextField txtPiso1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listarBuses();
        tablaBuses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarBus();
            }
        });
    }

    @FXML
    void actGuardar(ActionEvent event) {
        int capacidadAsiento = Integer.parseInt(txtPiso1.getText()) + Integer.parseInt(txtPiso2.getText());
        BusModelo busModelo = new BusModelo();
        busModelo.setPlaca(txt_placa.getText());
        busModelo.setMarca(txt_marca.getText());
        busModelo.setModelo(txt_modelo.getText());
        busModelo.setCapacidadAsientos(capacidadAsiento);
        busModelo.setPrimerPiso(Integer.parseInt(txtPiso1.getText()));
        busModelo.setSegundoPiso(Integer.parseInt(txtPiso2.getText()));
        busModelo.setCapacidadCarga(Double.parseDouble(txt_carga_maxima.getText()));
        BusModelo busGuardar = busServicio.save(busModelo);
        List<AsientoModelo> listaAsientos = new ArrayList<>();
        for (int i = 0; i < capacidadAsiento; i++) {
            AsientoModelo asientoModelo = new AsientoModelo();
            asientoModelo.setNumeroAsiento(i + 1);
            asientoModelo.setPrecio(0);
            asientoModelo.setIdTipoAsiento(i < Integer.parseInt(txtPiso2.getText()) ? 1 : 2);
            asientoModelo.setIdBus(busGuardar.getIdBus());

            AsientoEstadoFechaModelo estadoAsiento = new AsientoEstadoFechaModelo();
            estadoAsiento.setAsiento(asientoModelo);
            estadoAsiento.setFecha(LocalDate.now());
            estadoAsiento.setHora(LocalTime.now());
            estadoAsiento.setEstado(Estado.DISPONIBLE);
            listaAsientos.add(asientoModelo);
        }

        listaAsientos.forEach(asientoServicio::save);
        listarBuses();
        limpiarCampos();
    }

    private void listarBuses(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idBus"));
        columnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnAsientoTotal.setCellValueFactory(new PropertyValueFactory<>("capacidadAsientos"));
        columnCarga.setCellValueFactory(new PropertyValueFactory<>("capacidadCarga"));
        tablaBuses.getItems().setAll(busServicio.findAll());
    }

    @FXML
    void actActualizar(ActionEvent event) {
        BusModelo busModelo = tablaBuses.getSelectionModel().getSelectedItem();
        busModelo.setPlaca(txt_placa.getText());
        busModelo.setMarca(txt_marca.getText());
        busModelo.setModelo(txt_modelo.getText());
        busModelo.setCapacidadAsientos(Integer.parseInt(txtPiso1.getText()) + Integer.parseInt(txtPiso2.getText()));
        busModelo.setPrimerPiso(Integer.parseInt(txtPiso1.getText()));
        busModelo.setSegundoPiso(Integer.parseInt(txtPiso2.getText()));
        busModelo.setCapacidadCarga(Double.parseDouble(txt_carga_maxima.getText()));

        List<AsientoModelo> listaAsientos = asientoServicio.getAsientosPorBus(busModelo.getIdBus());
        int capacidadAsiento = Integer.parseInt(txtPiso1.getText()) + Integer.parseInt(txtPiso2.getText());
        if (capacidadAsiento > listaAsientos.size()) {
            for (int i = listaAsientos.size(); i < capacidadAsiento; i++) {
                AsientoModelo asientoModelo = new AsientoModelo();
                asientoModelo.setNumeroAsiento(i + 1);
                asientoModelo.setPrecio(0);
                asientoModelo.setIdTipoAsiento(i < Integer.parseInt(txtPiso2.getText()) ? 1 : 2);
                asientoModelo.setIdBus(busModelo.getIdBus());

                asientoModelo.setEstadosFecha(new ArrayList<>());

                AsientoEstadoFechaModelo estadoAsiento = new AsientoEstadoFechaModelo();
                estadoAsiento.setAsiento(asientoModelo);
                estadoAsiento.setFecha(LocalDate.now());
                estadoAsiento.setHora(LocalTime.now());
                estadoAsiento.setEstado(Estado.DISPONIBLE);

                asientoModelo.getEstadosFecha().add(estadoAsiento);
                asientoServicio.save(asientoModelo);
            }
        } else if (capacidadAsiento < listaAsientos.size()) {
            for (int i = listaAsientos.size() - 1; i >= capacidadAsiento; i--) {
                asientoServicio.deleteById(listaAsientos.get(i).getIdAsiento());
            }
        }

        busServicio.update(busModelo);
        listarBuses();
        limpiarCampos();
    }

    @FXML
    public void seleccionarBus(){
        BusModelo busModelo = tablaBuses.getSelectionModel().getSelectedItem();

        txt_placa.setText(busModelo.getPlaca());
        txt_marca.setText(busModelo.getMarca());
        txt_modelo.setText(busModelo.getModelo());

        long vipCount = asientoServicio.getAsientosPorBus(busModelo.getIdBus()).stream()
                .filter(asiento -> asiento.getTipoAsiento() != null && asiento.getTipoAsiento().getNombre().equals(TipoAsiento.VIP))
                .count();
        txtPiso2.setText(String.valueOf(vipCount));
        txtPiso1.setText(String.valueOf(busModelo.getCapacidadAsientos() - vipCount));

        txt_carga_maxima.setText(String.valueOf(busModelo.getCapacidadCarga()));
    }

    @FXML
    void actEliminar(ActionEvent event) {
        BusModelo busModelo = tablaBuses.getSelectionModel().getSelectedItem();
        busServicio.deleteById(busModelo.getIdBus());
        listarBuses();
    }

    private void limpiarCampos(){
        txt_placa.setText("");
        txt_marca.setText("");
        txt_modelo.setText("");
        txtPiso2.setText("");
        txtPiso1.setText("");
        txt_carga_maxima.setText("");
    }

    @Deprecated
    public void btnGestionAsiento(ActionEvent event) throws IOException {
        Parent fxmlLoader = FxmlCargarUtil.load("/vista/AsientoVista.fxml");
        Stage stage = new Stage();
        stage.setTitle("Gestión de Asientos");
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Deprecated
    void btnAsignacionAsiento(ActionEvent event) throws IOException {
        Parent fxmlLoader = FxmlCargarUtil.load("/vista/AsignacionRutasVista.fxml");
        Stage stage = new Stage();
        stage.setTitle("Asignacion de Asientos");
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


}
