package com.utp.viacosta.controller;

import com.utp.viacosta.model.EmpleadoModel;
import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VentanaPrincipalController implements Initializable {


    private  EmpleadoModel empleadoModel;

    @FXML
    private VBox asidePanel;
    @FXML
    private AnchorPane contentLogo;
    @FXML
    private BorderPane ventanaPrincipal;

    @FXML
    private Button btn_rutas, btn_reportes, btn_inicio, btn_config, btn_logout;
    @FXML
    private Button btn_clientes, btn_empleados, btn_facturacion, btn_buses, btn_asiento, btn_buses_rutas;

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
        agregarMenusAlMapa();
        manejarBoton();
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
            btn_asiento.setVisible(roles.contains("ADMINISTRADOR"));
            btn_buses_rutas.setVisible(roles.contains("ADMINISTRADOR"));
            btn_rutas.setVisible(roles.contains("ADMINISTRADOR"));
            btn_facturacion.setVisible(roles.contains("ADMINISTRADOR") || roles.contains("VENTAS"));
            btn_reportes.setVisible(roles.contains("ADMINISTRADOR"));
            btn_config.setVisible(roles.contains("ADMINISTRADOR"));
            btn_logout.setVisible(roles.contains("ADMINISTRADOR"));
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
    public void btn_inicio(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/DashboardVista.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btn_buses(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/BusesVista.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btn_asiento(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/AsientosVista.fxml");
        ventanaPrincipal.setCenter(vista);
    }@FXML

    public void btn_buses_rutas(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/AsignacionBusesRutasVista.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btn_rutas(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/rutasVista.fxml");
        ventanaPrincipal.setCenter(vista);
    }

    @FXML
    public void btnReportes(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/reportesview.fxml");
        ventanaPrincipal.setCenter(vista);
    }
    @FXML
    public void btn_config(ActionEvent actionEvent) throws IOException {
        Parent vista = FxmlCargarUtil.load("/view/SedeVista.fxml");
        ventanaPrincipal.setCenter(vista);
    }


    @FXML
    private VBox primerSubVBox;
    @FXML
    private VBox listaSubMenuPrimer;
    @FXML
    private Button menuBuses;
    // Mapa para relacionar los menús y submenús
    Map<VBox, VBox> menuMapa = new HashMap<>();


    private void agregarMenusAlMapa() {
        agregarMenusAlMapaImpl();
    }

    private void agregarMenusAlMapaImpl() {
        menuMapa.put(primerSubVBox, listaSubMenuPrimer);
        eliminarComponentesDeVBox();
    }

    private void eliminarComponentesDeVBox() {
        for (Map.Entry<VBox, VBox> entrada : menuMapa.entrySet()) {
            entrada.getKey().getChildren().remove(entrada.getValue());
        }
    }

    private void deslizarMenu(VBox menu, VBox subMenu) {
        deslizarMenuImpl(menu, subMenu);
    }

    // Método para deslizar el menú
    private void deslizarMenuImpl(VBox menu, VBox subMenu) {
        final FadeTransition transicion = new FadeTransition(Duration.millis(500), menu);
        transicion.setFromValue(0.5);
        transicion.setToValue(1.0);
        transicion.setInterpolator(Interpolator.EASE_IN);

        if (menu.getChildren().contains(subMenu)) {
            menu.getChildren().remove(subMenu);
        } else {
            menu.getChildren().add(subMenu);
        }
        transicion.play();
    }

    private void handleButtonAction(ActionEvent event) {
        // Cerrar el menú si está abierto
        if (primerSubVBox.getChildren().contains(listaSubMenuPrimer)) {
            deslizarMenu(primerSubVBox, listaSubMenuPrimer);
        }

        // Handle button actions
        Button source = (Button) event.getSource();
        try {
            Parent vista = null;
            if (source == btn_inicio) {
                vista = FxmlCargarUtil.load("/view/DashboardVista.fxml");
            } else if (source == btn_clientes) {
                vista = FxmlCargarUtil.load("/view/clientesView.fxml");
            } else if (source == btn_empleados) {
                vista = FxmlCargarUtil.load("/view/empleadosview.fxml");
            } else if (source == btn_rutas) {
                vista = FxmlCargarUtil.load("/view/rutasVista.fxml");
            } else if (source == btn_facturacion) {
                vista = FxmlCargarUtil.load("/view/facturacionView.fxml");
            } else if (source == btn_reportes) {
                vista = FxmlCargarUtil.load("/view/reportesview.fxml");
            }  else if (source == btn_config) {
                vista = FxmlCargarUtil.load("/view/SedeVista.fxml");
            } else if (source == btn_logout) {

            }
            if (vista != null) {
                ventanaPrincipal.setCenter(vista);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manejarBoton(){
        btn_inicio.setOnAction(this::handleButtonAction);
        btn_clientes.setOnAction(this::handleButtonAction);
        btn_empleados.setOnAction(this::handleButtonAction);
        btn_rutas.setOnAction(this::handleButtonAction);
        btn_facturacion.setOnAction(this::handleButtonAction);
        btn_reportes.setOnAction(this::handleButtonAction);
        btn_config.setOnAction(this::handleButtonAction);
        btn_logout.setOnAction(this::handleButtonAction);
        menuBuses.setOnAction(event -> {
            deslizarMenu(primerSubVBox, listaSubMenuPrimer);
        });
    }

}