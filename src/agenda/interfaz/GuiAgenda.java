package agenda.interfaz;

import agenda.modelo.AgendaContactos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiAgenda extends Application {
	private AgendaContactos agenda;
	private MenuItem itemImportar;
	private MenuItem itemExportarPersonales;
	private MenuItem itemSalir;

	private MenuItem itemBuscar;
	private MenuItem itemFelicitar;

	private MenuItem itemAbout;

	private TextArea areaTexto;

	private RadioButton rbtListarTodo;
	private RadioButton rbtListarSoloNumero;
	private Button btnListar;

	private Button btnPersonalesEnLetra;
	private Button btnPersonalesOrdenadosPorFecha;

	private TextField txtBuscar;

	private Button btnClear;
	private Button btnSalir;

	@Override
	public void start(Stage stage) {
		agenda = new AgendaContactos(); // el modelo

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 1100, 700);
		stage.setScene(scene);
		stage.setTitle("Agenda de contactos");
		scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		stage.show();

	}

	private BorderPane crearGui() {
		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		return panel;
	}

	private BorderPane crearPanelPrincipal() {
		BorderPane panel = new BorderPane();
		panel.setPadding(new Insets(10));
		panel.setTop(crearPanelLetras());

		areaTexto = new TextArea();
		areaTexto.getStyleClass().add("textarea");
		panel.setCenter(areaTexto);

		panel.setLeft(crearPanelBotones());
		return panel;
	}

	private VBox crearPanelBotones() {
		VBox panel = new VBox(10);
		panel.setPadding(new Insets(10));

		// Buscar
		txtBuscar = new TextField();
		txtBuscar.setPromptText("Buscar");
		txtBuscar.setMinHeight(40);
		VBox.setMargin(txtBuscar, new Insets(0, 0, 40, 0));

		// Botones de radio
		rbtListarTodo = new RadioButton();
		rbtListarSoloNumero = new RadioButton();
		ToggleGroup grupo = new ToggleGroup();
		grupo.getToggles().addAll(rbtListarTodo, rbtListarSoloNumero);
		rbtListarTodo.setText("Listar toda la agenda");
		rbtListarTodo.setSelected(true);
		rbtListarSoloNumero.setText("Listar nº contactos");

		// btnListar
		btnListar = new Button();
		btnListar.setText("Listar");
		btnListar.setAlignment(Pos.CENTER);
		btnListar.setPrefWidth(250);
		btnListar.getStyleClass().add("botones");
		VBox.setMargin(btnListar, new Insets(0, 0, 40, 0));

		// btnPersonalesEnLetra
		btnPersonalesEnLetra = new Button();
		btnPersonalesEnLetra.getStyleClass().add("botones");
		btnPersonalesEnLetra.setText("Contactos personales en letra");
		btnPersonalesEnLetra.setAlignment(Pos.CENTER);
		btnPersonalesEnLetra.setPrefWidth(250);

		// btnPersonalesOrdenadosPorFecha
		btnPersonalesOrdenadosPorFecha = new Button();
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		btnPersonalesOrdenadosPorFecha.setText("Contactos personales\nordenados por fecha");
		btnPersonalesOrdenadosPorFecha.setAlignment(Pos.CENTER);
		btnPersonalesOrdenadosPorFecha.setPrefWidth(250);
		
		// clear
		btnClear = new Button();
		btnClear.getStyleClass().add("botones");
		btnClear.setText("Clear");
		btnClear.setAlignment(Pos.CENTER);
		btnClear.setPrefWidth(250);
		VBox.setMargin(btnClear, new Insets(40, 0, 0, 0));
		
		// salir
		btnSalir = new Button();
		btnSalir.getStyleClass().add("botones");
		btnSalir.setText("Salir");
		btnSalir.setAlignment(Pos.CENTER);
		btnSalir.setPrefWidth(250);
		
		panel.getChildren().addAll(txtBuscar, rbtListarTodo, rbtListarSoloNumero, btnListar, btnPersonalesEnLetra,
				btnPersonalesOrdenadosPorFecha, btnClear, btnSalir);
		return panel;
	}

	private GridPane crearPanelLetras() {
		// a completar
		GridPane panel = new GridPane();

		return panel;
	}

	private MenuBar crearBarraMenu() {

		MenuBar barra = new MenuBar();
		// Menu 1
		Menu menuArchivo = new Menu();
		menuArchivo.setText("Archivo");
		itemImportar = new MenuItem();
		itemImportar.setText("Importar agenda");
		itemExportarPersonales = new MenuItem();
		itemExportarPersonales.setText("Exportar Personales");
		itemExportarPersonales.setDisable(true);
		itemSalir = new MenuItem();
		itemSalir.setText("Salir");
		itemSalir.setOnAction(e -> salir());
		menuArchivo.getItems().addAll(itemImportar, itemExportarPersonales, itemSalir);

		// Menu 2
		Menu menuOpciones = new Menu();
		menuOpciones.setText("Opciones");
		itemBuscar = new MenuItem();
		itemFelicitar = new MenuItem();
		itemBuscar.setText("Buscar");
		itemFelicitar.setText("Felicitar");
		menuOpciones.getItems().addAll(itemBuscar, itemFelicitar);

		// Menu 3
		Menu menuHelp = new Menu();
		menuHelp.setText("Help");
		itemAbout = new MenuItem();
		itemAbout.setText("About");
		menuHelp.getItems().addAll(itemAbout);

		barra.getMenus().addAll(menuArchivo, menuOpciones, menuHelp);
		return barra;
	}

	private void importarAgenda() {
		// a completar

	}

	private void exportarPersonales() {
		// a completar

	}

	/**
	 *  
	 */
	private void listar() {
		clear();
		// a completar

	}

	private void personalesOrdenadosPorFecha() {
		clear();
		// a completar

	}

	private void contactosPersonalesEnLetra() {
		clear();
		// a completar

	}

	private void contactosEnLetra(char letra) {
		clear();
		// a completar
	}

	private void felicitar() {
		clear();
		// a completar

	}

	private void buscar() {
		clear();
		// a completar

		cogerFoco();

	}

	private void about() {
		// a completar

	}

	private void clear() {
		areaTexto.setText("");
	}

	private void salir() {
		Platform.exit();
	}

	private void cogerFoco() {
		txtBuscar.requestFocus();
		txtBuscar.selectAll();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
