package com.utp.viacosta.controller;

import com.utp.viacosta.service.BusService;
import com.utp.viacosta.service.ClienteService;
import com.utp.viacosta.service.EmpleadoService;
import com.utp.viacosta.service.RutaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardControlador implements Initializable {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private BusService busService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private RutaService rutaService;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarRutasMasSolicitadas();
    }

    public void cargarDatos() {
        Long empleados = empleadoService.findAll().stream().count();
        num_empleados.setText(empleados.toString());

        Long buses = busService.findAll().stream().count();
        num_buses.setText(buses.toString());

        Long clientes = clienteService.listaClientes().stream().count();
        num_clientes.setText(clientes.toString());

        Long rutas = rutaService.listarRutas().stream().count();
        num_rutas.setText(rutas.toString());
    }

    private void cargarRutasMasSolicitadas() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Huaraz", 13),
                new PieChart.Data("Chimbote", 25),
                new PieChart.Data("Casma", 10),
                new PieChart.Data("Pariacoto", 22));

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Lugares más solicitados");

        rutasSolicitadas.setData(pieChartData);
    }


}
