package com.utp.viacosta;

import com.utp.viacosta.util.FxmlCargarUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunViaCosta extends Application{

	public static void main(String[] args) {
		launch(args);  // Inicializa la parte de JavaFX
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Inicializa el contexto de Spring
		var context = SpringApplication.run(RunViaCosta.class);

		// Pasar el contexto de Spring a la clase utilitaria
		FxmlCargarUtil.setApplicationContext(context);

		// Cargar la pantalla de login utilizando el metodo utilitario
		var fxml = new FXMLLoader(getClass().getResource("/view/Loginview.fxml"));
		fxml.setControllerFactory(context::getBean); // Para usar beans gestionados por Spring

		//Cargar archivo css
		Scene scene = new Scene(fxml.load());
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

		stage.setTitle("Sistema Pasajes Via Costa");
		stage.setScene(scene);
		stage.show();
	}
}
