package com.utp.viacosta.controller;

import com.utp.viacosta.model.AsientoModel;
import com.utp.viacosta.model.BusModel;
import com.utp.viacosta.model.TipoAsientoModel;
import com.utp.viacosta.model.enums.Estado;
import com.utp.viacosta.model.enums.TipoAsiento;
import com.utp.viacosta.service.AsientoService;
import com.utp.viacosta.service.BusService;
import com.utp.viacosta.service.TipoAsientoService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class BusesControlador implements Initializable {

    @Autowired
    private BusService busService;
    @Autowired
    private TipoAsientoService tipoAsientoService;
    @Autowired
    private AsientoService asientoService;


    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btnGestionAsiento;

    @FXML
    private TableColumn<BusModel, String> columnPlaca;
    @FXML
    private TableColumn<BusModel, String> columnMarca;
    @FXML
    private TableColumn<BusModel, String> columnModelo;
    @FXML
    private TableColumn<BusModel, String> columnAsientoTotal;
    @FXML
    private TableColumn<BusModel, String> columnId;
    @FXML
    private TableColumn<BusModel, String> columnCarga;
    @FXML
    private TableView<BusModel> tablaBuses;

    @FXML
    private TextField txt_placa;
    @FXML
    private TextField txt_marca;
    @FXML
    private TextField txt_modelo;
    @FXML
    private TextField txt_asiento_vip;
    @FXML
    private TextField txt_asiento_econocimio;
    @FXML
    private TextField txt_carga_maxima;


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
        BusModel busModel = new BusModel();
        int capacidadAsiento =Integer.parseInt(txt_asiento_econocimio.getText()) + Integer.parseInt(txt_asiento_vip.getText());
        busModel.setPlaca(txt_placa.getText());
        busModel.setMarca(txt_marca.getText());
        busModel.setModelo(txt_modelo.getText());
        busModel.setCapacidadCarga(Double.parseDouble(txt_carga_maxima.getText()));

        List<AsientoModel> listaAsientos = new ArrayList<>();
        for (int i = 0; i < capacidadAsiento; i++) {
            AsientoModel asientoModel = new AsientoModel();
            asientoModel.setNumeroAsiento(i);
            asientoModel.setEstado(Estado.DISPONIBLE);
            asientoModel.setPrecio(0);
            asientoModel.setIdTipoAsiento(i < Integer.parseInt(txt_asiento_vip.getText()) ? 1 : 2);

            listaAsientos.add(asientoModel);
        }
        busModel.setCapacidadAsientos(listaAsientos.size());
        busService.save(busModel);
        listaAsientos.forEach(asientoModel -> {
            asientoModel.setIdBus(busModel.getIdBus());
            asientoService.save(asientoModel);
        });

        listarBuses();
        limpiarCampos();
    }

    //Método para mostrar en la tabla de los buses
    private void listarBuses(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idBus"));
        columnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnAsientoTotal.setCellValueFactory(new PropertyValueFactory<>("capacidadAsientos"));
        columnCarga.setCellValueFactory(new PropertyValueFactory<>("capacidadCarga"));
        tablaBuses.getItems().setAll(busService.findAll());
    }

    //Metodo para actualizar un bus
    @FXML
    void actActualizar(ActionEvent event) {
        BusModel busModel = tablaBuses.getSelectionModel().getSelectedItem();
        busModel.setPlaca(txt_placa.getText());
        busModel.setMarca(txt_marca.getText());
        busModel.setModelo(txt_modelo.getText());
        busModel.setCapacidadAsientos(Integer.parseInt(txt_asiento_econocimio.getText()) + Integer.parseInt(txt_asiento_vip.getText()));
        busModel.setCapacidadCarga(Double.parseDouble(txt_carga_maxima.getText()));

        List<AsientoModel> listaAsientos = asientoService.getAsientosPorBus(busModel.getIdBus());
        int capacidadAsiento =Integer.parseInt(txt_asiento_econocimio.getText()) + Integer.parseInt(txt_asiento_vip.getText());
        if (capacidadAsiento > listaAsientos.size()) {
            for (int i = listaAsientos.size(); i < capacidadAsiento; i++) {
                AsientoModel asientoModel = new AsientoModel();
                asientoModel.setNumeroAsiento(i);
                asientoModel.setEstado(Estado.DISPONIBLE);
                asientoModel.setPrecio(0);

                if(i < Integer.parseInt(txt_asiento_vip.getText())){
                    asientoModel.setIdTipoAsiento(1);
                }else{
                    asientoModel.setIdTipoAsiento(2);
                }
                asientoModel.setIdBus(busModel.getIdBus());
                asientoService.save(asientoModel);
            }
        } else if (capacidadAsiento < listaAsientos.size()) {
            for (int i = listaAsientos.size() - 1; i >= capacidadAsiento; i--) {
                asientoService.deleteById(listaAsientos.get(i).getIdAsiento());
            }
        }

        busService.update(busModel);
        listarBuses();
        limpiarCampos();
    }

    //Metodo para seleccionar un bus
    @FXML
    public void seleccionarBus(){
        BusModel busModel = tablaBuses.getSelectionModel().getSelectedItem();

        txt_placa.setText(busModel.getPlaca());
        txt_marca.setText(busModel.getMarca());
        txt_modelo.setText(busModel.getModelo());

        long vipCount = asientoService.getAsientosPorBus(busModel.getIdBus()).stream()
                .filter(asiento -> asiento.getTipoAsiento() != null && asiento.getTipoAsiento().getNombre().equals(TipoAsiento.VIP))
                .count();
        txt_asiento_vip.setText(String.valueOf(vipCount));
        txt_asiento_econocimio.setText(String.valueOf(busModel.getCapacidadAsientos() - vipCount));

        txt_carga_maxima.setText(String.valueOf(busModel.getCapacidadCarga()));
    }

    //Metodo para elimiar un bus
    @FXML
    void actEliminar(ActionEvent event) {
        BusModel busModel = tablaBuses.getSelectionModel().getSelectedItem();
        busService.deleteById(busModel.getIdBus());
        listarBuses();
    }




    private void limpiarCampos(){
        txt_placa.setText("");
        txt_marca.setText("");
        txt_modelo.setText("");
        txt_asiento_vip.setText("");
        txt_asiento_econocimio.setText("");
        txt_carga_maxima.setText("");
    }







    //Método para abrir la ventana de AsientoVista
    @FXML
    public void btnGestionAsiento(ActionEvent event) throws IOException {
        Parent fxmlLoader = FxmlCargarUtil.load("/view/AsientosVista.fxml");
        Stage stage = new Stage();
        stage.setTitle("Gestión de Asientos");
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    //Metodo para abrir la gestion de buses a rutas
    @FXML
    void btnAsignacionAsiento(ActionEvent event) throws IOException {
        Parent fxmlLoader = FxmlCargarUtil.load("/view/AsignacionRutasVista.fxml");
        Stage stage = new Stage();
        stage.setTitle("Asignacion de Asientos");
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


}
