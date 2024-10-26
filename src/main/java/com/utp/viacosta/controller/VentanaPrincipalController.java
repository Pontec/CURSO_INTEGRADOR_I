package com.utp.viacosta.controller;

import com.utp.viacosta.model.EmpleadoModel;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class VentanaPrincipalController implements Initializable {

    private  EmpleadoModel empleadoModel;

    @FXML
    private VBox asidePanel;

    @FXML
    private AnchorPane contentLogo;

    @FXML
    private BorderPane ventanaPrincipal;
    @FXML
    private Button btn_rutas;
    @FXML
    private Button btn_reportes;
    @FXML
    private Button btn_inicio;
    @FXML
    private Button btn_config;
    @FXML
    private Button btn_logout;
    @FXML
    private Button btn_clientes;
    @FXML
    private Button btn_empleados;
    @FXML
    private Button btn_facturacion;
    @FXML
    private Button btn_buses;
    @FXML
    private AnchorPane contentPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent vista = FxmlCargarUtil.load("/view/DashboardVista.fxml");
            ventanaPrincipal.setCenter(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEmpleadoModel(EmpleadoModel empleadoModel) {
        this.empleadoModel = empleadoModel;
        accesoRoles(); // Actualiza los accesos cuando se establece el empleado
    }

    public void accesoRoles() {
        if (empleadoModel != null) {
            Set<String> roles = empleadoModel.getRoles().stream()
                    .map(role -> role.getRole())
                    .collect(Collectors.toSet());
            btn_inicio.setVisible(roles.contains("ADMINISTRADOR") || roles.contains("VENTAS"));
            btn_clientes.setVisible(roles.contains("ADMINISTRADOR"));
            btn_empleados.setVisible(roles.contains("ADMINISTRADOR"));
            btn_buses.setVisible(roles.contains("ADMINISTRADOR"));
            btn_rutas.setVisible(roles.contains("ADMINISTRADOR"));
            btn_facturacion.setVisible(roles.contains("ADMINISTRADOR") || roles.contains("VENTAS"));
            btn_reportes.setVisible(roles.contains("ADMINISTRADOR"));
            btn_config.setVisible(roles.contains("ADMINISTRADOR"));
            btn_logout.setVisible(true);
        }
    }

    @FXML
    public void btnFacturacion(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/facturacionView.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btn_empleados(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/empleadosview.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btn_clientes(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/clientesView.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btn_rutas(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/rutasVista.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btn_inicio(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/DashboardVista.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btnReportes(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/reportesview.fxml");
        ventanaPrincipal.setCenter(vista);
    }
}