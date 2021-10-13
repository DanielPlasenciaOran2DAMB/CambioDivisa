package dad.javafx.cambiodivisa;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisa extends Application {

	private TextField monedaLocalText, cambioText;
	private ComboBox<Divisa> monedaLocalCombo, cambioCombo;
	private Button cambiarButton;

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		monedaLocalText = new TextField("0");
		monedaLocalText.setMaxWidth(50);

		cambioText = new TextField("0");
		cambioText.setMaxWidth(50);
		cambioText.setEditable(false);

		monedaLocalCombo = new ComboBox<>();
		monedaLocalCombo.getItems().addAll(new Divisa("Euro", 1.0), new Divisa("Libra", 0.8873),
				new Divisa("Dolar", 1.2007), new Divisa("Yen", 133.59));
		monedaLocalCombo.getSelectionModel().selectFirst();

		cambioCombo = new ComboBox<>();
		cambioCombo.getItems().addAll(new Divisa("Euro", 1.0), new Divisa("Libra", 0.8873), new Divisa("Dolar", 1.2007),
				new Divisa("Yen", 133.59));
		cambioCombo.getSelectionModel().select(3);

		cambiarButton = new Button("Cambiar");
		cambiarButton.setOnAction(e -> onCambiarAction());

		VBox root = new VBox(5, new HBox(5, monedaLocalText, monedaLocalCombo), new HBox(5, cambioText, cambioCombo),
				cambiarButton);
		root.setFillWidth(false);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Cambio de divisa");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void onCambiarAction() {
		try {
			Double cantidad = Double.parseDouble(monedaLocalText.getText());
			Divisa divisa = monedaLocalCombo.getSelectionModel().getSelectedItem();
			Divisa resultadoDivisa = cambioCombo.getSelectionModel().getSelectedItem();
			Double resultadoTotal = resultadoDivisa.fromEuro(divisa.toEuro(cantidad));
			cambioText.setText("" + resultadoTotal);
		} catch (NumberFormatException e1) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Cambio de divisa");
			errorAlert.setHeaderText("Error");
			errorAlert.setContentText("Solo se permite introducir numeros.");
			errorAlert.initOwner(primaryStage);

			errorAlert.showAndWait();

			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
