package agenda.interfaz;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import agenda.io.AgendaIO;
import agenda.modelo.AgendaContactos;
import agenda.modelo.Contacto;
import agenda.modelo.Personal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

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
		txtBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					buscar();
				}
			}
		});
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
		btnListar.setOnAction(e -> listar());
		VBox.setMargin(btnListar, new Insets(0, 0, 40, 0));

		// btnPersonalesEnLetra
		btnPersonalesEnLetra = new Button();
		btnPersonalesEnLetra.getStyleClass().add("botones");
		btnPersonalesEnLetra.setText("Contactos personales en letra");
		btnPersonalesEnLetra.setOnAction(e -> contactosPersonalesEnLetra());
		btnPersonalesEnLetra.setAlignment(Pos.CENTER);
		btnPersonalesEnLetra.setPrefWidth(250);

		// btnPersonalesOrdenadosPorFecha
		btnPersonalesOrdenadosPorFecha = new Button();
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		btnPersonalesOrdenadosPorFecha.setText("Contactos personales\nordenados por fecha");
		btnPersonalesOrdenadosPorFecha.setAlignment(Pos.CENTER);
		btnPersonalesOrdenadosPorFecha.setPrefWidth(250);
		btnPersonalesOrdenadosPorFecha.setOnAction(e -> personalesOrdenadosPorFecha());

		// clear
		btnClear = new Button();
		btnClear.getStyleClass().add("botones");
		btnClear.setText("Clear");
		btnClear.setAlignment(Pos.CENTER);
		btnClear.setPrefWidth(250);
		btnClear.setOnAction(e -> clear());
		VBox.setMargin(btnClear, new Insets(40, 0, 0, 0));

		// salir
		btnSalir = new Button();
		btnSalir.getStyleClass().add("botones");
		btnSalir.setText("Salir");
		btnSalir.setAlignment(Pos.CENTER);
		btnSalir.setPrefWidth(250);
		btnSalir.setOnAction(e -> salir());

		panel.getChildren().addAll(txtBuscar, rbtListarTodo, rbtListarSoloNumero, btnListar, btnPersonalesEnLetra,
				btnPersonalesOrdenadosPorFecha, btnClear, btnSalir);
		return panel;
	}
	
	/**
	 * Crea el panel de botones alfabéticos y lo devuelve
	 * @return el panel 
	 */
	@SuppressWarnings("static-access")
	private GridPane crearPanelLetras() {
		// Crea el panel con sus atributos de definición
		GridPane panel = new GridPane();
		panel.setPadding(new Insets(10));
		panel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		panel.setVgap(5);
		panel.setHgap(5);
		String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		Button[] btn = new Button[27];
		//Bucle que crea los botones
		for (int i = 0; i < letras.length; i++) {
			btn[i] = new Button(letras[i]);
			btn[i].setText(letras[i]);
			btn[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btn[i].getStyleClass().add("botonletra");
			final int valor = i;
			btn[i].setOnAction(e -> contactosEnLetra(btn[valor].getText().charAt(0)));
			if(i < 14) {
				panel.add(btn[i], i, 0);
				panel.setHgrow(btn[i], Priority.ALWAYS);
			}
			else {
				panel.add(btn[i], i-14, 1);
				panel.setHgrow(btn[i], Priority.ALWAYS);
			}
		}
		return panel;
	}

	private MenuBar crearBarraMenu() {

		MenuBar barra = new MenuBar();
		// Menu 1
		Menu menuArchivo = new Menu();
		menuArchivo.setText("_Archivo");
		menuArchivo.setMnemonicParsing(true);
		itemImportar = new MenuItem();
		itemImportar.setText("Importar agenda");
		itemImportar.setOnAction(e -> importarAgenda());
		itemImportar.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
		itemExportarPersonales = new MenuItem();
		itemExportarPersonales.setText("Exportar Personales");
		itemExportarPersonales.setDisable(true);
		itemExportarPersonales.setOnAction(e -> exportarPersonales());
		itemExportarPersonales.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		itemSalir = new MenuItem();
		itemSalir.setText("Salir");
		itemSalir.setOnAction(e -> salir());
		itemSalir.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		menuArchivo.getItems().addAll(itemImportar, itemExportarPersonales, itemSalir);

		// Menu 2
		Menu menuOpciones = new Menu();
		menuOpciones.setText("_Opciones");
		menuOpciones.setMnemonicParsing(true);
		itemBuscar = new MenuItem();
		itemFelicitar = new MenuItem();
		itemBuscar.setText("Buscar");
		itemBuscar.setOnAction(e -> buscar());
		itemBuscar.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
		itemFelicitar.setText("Felicitar");
		itemFelicitar.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
		itemFelicitar.setOnAction(e -> felicitar());
		menuOpciones.getItems().addAll(itemBuscar, itemFelicitar);

		// Menu 3
		Menu menuHelp = new Menu();
		menuHelp.setText("_Help");
		menuHelp.setMnemonicParsing(true);
		itemAbout = new MenuItem();
		itemAbout.setText("About");
		itemAbout.setOnAction(e -> about());
		itemAbout.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
		menuHelp.getItems().addAll(itemAbout);

		barra.getMenus().addAll(menuArchivo, menuOpciones, menuHelp);
		return barra;
	}

	private void importarAgenda() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir fichero csv");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
		fileChooser.setInitialDirectory(new File("."));
		AgendaIO IO = new AgendaIO();
		File csv = fileChooser.showOpenDialog(null);
		if (csv != null) {
			areaTexto.setText("Importada agenda\n\nLineas erroneas: " + AgendaIO.importar(agenda, csv.getName()));
		}
		itemImportar.setDisable(true);
		itemExportarPersonales.setDisable(false);
	}

	/**
	 * Crea una ventana que especifica el tipo de archivo a guardar (un txt)
	 * y llama al método exportarContactos() de la clase AgendaIO para guardar
	 */
	@SuppressWarnings("static-access")
	private void exportarPersonales() {
		FileChooser f = new FileChooser();
		Stage s = new Stage();
		f.setTitle("Exportados datos personales");
		f.setInitialDirectory(new File("."));
		f.getExtensionFilters().addAll(new ExtensionFilter("txt","*.txt"));
		File file = f.showSaveDialog(s);
		if(f != null) {
			AgendaIO io = new AgendaIO();
			try {
				io.exportarContactos(agenda, file.getPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 *  
	 */
	private void listar() {
		clear();
		if (agenda.totalContactos() != 0) {
			if (rbtListarTodo.isSelected() == true) {
				areaTexto.setText(agenda.toString());
			} else {
				areaTexto.setText("Total contactos en la agenda: " + agenda.totalContactos());
			}
		} else {
			areaTexto.setText("Importa antes la agenda");
		}
	}

	private void personalesOrdenadosPorFecha() {
		clear();
		if (agenda.totalContactos() != 0) {
			List<String> opciones = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
			ChoiceDialog<String> dialogo = new ChoiceDialog<>("A", opciones);
			dialogo.setTitle("Selector de letra");
			dialogo.setHeaderText(null);
			dialogo.setContentText("Elije letra");
			Optional<String> resul = dialogo.showAndWait();
			if (resul.isPresent()) {
				String cadena = "";
				String opcion = resul.get();
				List<Personal> resultado = agenda.personalesOrdenadosPorFechaNacimiento(opcion.charAt(0));
				if (resultado.isEmpty()) {
					cadena = "No hay contactos en la letra " + opcion;
				} else {
					cadena = "Contactos en la letra " + opcion + "\n\n";
					Iterator<Personal> it = resultado.iterator();
					while (it.hasNext()) {
						Personal personal = (Personal) it.next();
						cadena += personal.toString();
					}
				}
				areaTexto.setText(cadena);
			}

		} else {
			areaTexto.setText("Importa antes la agenda");
		}

	}

	/**
	 * Crea una ventana de selección con cada letra, dandole atributos,
	 * y esta se encarga de comprobar si se ha importado el fichero. Si no,
	 * avisa al usuario de que debe importar, y si ya lo está, comprueba que 
	 * este vacío (comunica que está vacío) o no (Lista los personales que empiecen por esa letra alfabéticamente) 
	 */
	private void contactosPersonalesEnLetra() {
		clear();
		if (agenda.totalContactos() != 0) {
			List<String> opciones = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
			ChoiceDialog<String> dialogo = new ChoiceDialog<>("A", opciones);
			dialogo.setTitle("Selector de letra");
			dialogo.setHeaderText(null);
			dialogo.setContentText("Elije letra");
			Optional<String> resul = dialogo.showAndWait();
			if (resul.isPresent()) {
				String linea = "";
				String opcion = resul.get();
				List<Personal> listado = agenda.personalesEnLetra(opcion.charAt(0));
				if (listado.isEmpty()) {
					linea = "No hay contactos en la letra " + opcion;
				} else {
					linea = "Contactos en la letra " + opcion + "\n\n";
					Iterator<Personal> it = listado.iterator();
					while (it.hasNext()) {
						Personal personal = (Personal) it.next();
						linea += personal.toString();
					}
				}
				areaTexto.setText(linea);
			}

		} else {
			areaTexto.setText("Importa antes la agenda");
		}

	}

	/**
	 * Comprubea primero si está importado el fichero, si no lo está avisa de que se
	 * debe importar, y si lo está, busca contactos que empiecen por esa letra.
	 * Si no hay nadie por esa letra, saca un mensaje avisando de que está vacío,
	 * y sino, los lista alfabéticamente
	 * @param letra inicial de los contactos a buscar
	 */
	private void contactosEnLetra(char letra) {
		clear();
		if (agenda.totalContactos() != 0) {
			Set<Contacto> listado = agenda.contactosEnLetra(letra);
			String linea = "";
			try {
					Iterator<Contacto> it = listado.iterator();
					while (it.hasNext()) {
						Contacto contacto = (Contacto) it.next();
						linea += contacto.toString();
					}
			}catch(NullPointerException n) {
				linea = "No hay nadie por esa letra";
			}
			areaTexto.setText(linea);
			
		} else {
			areaTexto.setText("Importa antes la agenda");
		}
	}

	private void felicitar() {
		clear();
		if (agenda.totalContactos() != 0) {
			String cadena = "";
			cadena = "Hoy es " + LocalDate.now() + "\n\n";
			List<Personal> resultado = agenda.felicitar();
			if (resultado.isEmpty()) {
				cadena += "Hoy no cumple nadie";
			} else {
				cadena += "Hoy hay que felicitar a ";
				Iterator<Personal> it = resultado.iterator();
				while (it.hasNext()) {
					Personal personal = (Personal) it.next();
					cadena += personal.toString();
				}
			}
			areaTexto.setText(cadena);
		} else {
			areaTexto.setText("Importa antes la agenda");
		}
	}

	private void buscar() {
		clear();
		if (agenda.totalContactos() != 0) {
			String cadena = "";
			List<Contacto> resultado = agenda.buscarContactos(txtBuscar.getText());
			;
			if (resultado.isEmpty()) {
				cadena = "No hay contactos que contengan " + txtBuscar.getText();
			} else {
				cadena = "Contactos en la agenda que contienen " + txtBuscar.getText() + "\n\n";
				Iterator<Contacto> it = resultado.iterator();
				while (it.hasNext()) {
					Contacto temp = (Contacto) it.next();
					cadena += temp.toString();
				}
			}
			areaTexto.setText(cadena);
		} else {
			areaTexto.setText("Importa antes la agenda");
		}
		cogerFoco();

	}

	private void about() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		alert.setHeaderText(null);
		alert.setTitle("About Agenda de Contactos");
		alert.setContentText("Mi agenda de\ncontactos");
		alert.showAndWait();
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
