package com.utp.viacosta.controlador;

import com.utp.viacosta.servicio.BusServicio;
import com.utp.viacosta.servicio.ClienteServicio;
import com.utp.viacosta.servicio.EmpleadoServicio;
import com.utp.viacosta.servicio.RutaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarRutasMasSolicitadas();
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


}
