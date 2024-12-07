package com.utp.viacosta.controlador;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;
import com.utp.viacosta.servicio.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DashboardControlador implements Initializable {

    @Autowired
    private EmpleadoServicio empleadoServicio;
    @Autowired
    private BusServicio busServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private RutaServicio rutaServicio;

    @FXML
    private Label num_buses;
    @FXML
    private Label num_clientes;
    @FXML
    private Label num_rutas;
    @FXML
    private Label num_empleados;

    @FXML
    private PieChart rutasSolicitadas;
    @FXML
    private Pane paneBuses;
    @FXML
    private Pane paneEmpleados;
    @FXML
    private Pane paneRutas;
    @FXML
    private Pane paneClientes;
    @FXML
    private TableColumn columnRuta;
    @FXML
    private TableColumn columnTotal;
    @FXML
    private TableColumn columnFecha;
    @FXML
    private TableView tablaUltimasVentas;
    @FXML
    private TableColumn columnHora;
    @FXML
    private TableColumn columnName;
    @Autowired
    private DetalleBoletaServicio boletaService;

    List<DetalleBoletaDTO> tablaReportes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarRutasMasSolicitadas();
        listarReportes();
    }

    public void cargarDatos() {
        Long empleados = empleadoServicio.findAll().stream().count();
        num_empleados.setText(empleados.toString());

        Long buses = busServicio.findAll().stream().count();
        num_buses.setText(buses.toString());

        Long clientes = clienteServicio.listaClientes().stream().count();
        num_clientes.setText(clientes.toString());

        Long rutas = rutaServicio.listarRutas().stream().count();
        num_rutas.setText(rutas.toString());
    }


    private void cargarRutasMasSolicitadas() {
        List<Object[]> rutasMasVendidas = rutaServicio.obtenerRutasMasVendidas();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if (rutasMasVendidas.isEmpty()) {
            rutasSolicitadas.setTitle("No hay datos de ventas disponibles");
        } else {
            for (Object[] ruta : rutasMasVendidas) {
                String origen = (String) ruta[0];
                Long ventas = (Long) ruta[1];
                pieChartData.add(new PieChart.Data(origen, ventas));
            }
            rutasSolicitadas.setData(pieChartData);
        }
    }


    private List<DetalleBoletaDTO> obtenerReportes() {
        return tablaReportes;
    }
    private void establecerReportes(List<DetalleBoletaDTO> table) {
        tablaReportes = table;
    }
    private void listarReportes() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        columnRuta.setCellValueFactory(new PropertyValueFactory<>("ruta"));
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        columnHora.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        tablaUltimasVentas.getItems().setAll(boletaService.getAllReporteVentas());
        List<DetalleBoletaDTO> todasLasVentas = boletaService.getAllReporteVentas();
        todasLasVentas.sort((v1, v2) -> {
            int fechaComparacion = v2.getFechaSalida().compareTo(v1.getFechaSalida());
            if (fechaComparacion == 0) {
                return v2.getHoraSalida().compareTo(v1.getHoraSalida());
            }
            return fechaComparacion;
        });
        List<DetalleBoletaDTO> primerasDiezVentas = todasLasVentas.size() > 10 ? todasLasVentas.subList(0, 10) : todasLasVentas;
        tablaUltimasVentas.getItems().setAll(primerasDiezVentas);
        establecerReportes(primerasDiezVentas);
    }


}
