package iti.informatica.graph;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

public class Graph extends Application {
    // Variable para almacenar la ruta del archivo CSV seleccionado (null si no se ha seleccionado)
    private String csvFilePath = null;

    @Override
    public void start(Stage stage) {
	BorderPane root = new BorderPane();
        // Selector para elegir el tipo de gráfico
        ComboBox<String> chartSelector = new ComboBox<>();
        chartSelector.getItems().addAll("De lineas", "Barras", "Pastel", "Dispersion", "Histograma");
        chartSelector.setValue("Pastel"); // gráfico por defecto

        // Botón para cargar archivo CSV
        Button loadCsvButton = new Button("Cargar CSV");
        loadCsvButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");
            // Filtro para archivos .csv
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Archivos CSV", "*.csv");
            fileChooser.getExtensionFilters().add(filter);
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                csvFilePath = selectedFile.getAbsolutePath();
                // Actualiza el gráfico usando los datos del archivo CSV seleccionado
                Node newChart = ChartFactory.createChart(chartSelector.getValue(), csvFilePath);
                root.setCenter(newChart);
            }
        });

        // Crear una barra de herramientas que contenga el ComboBox y el botón de cargar CSV
        ToolBar toolBar = new ToolBar(chartSelector, loadCsvButton);
        toolBar.setPadding(new Insets(10));

        // Se crea el gráfico inicial
        Node chart = ChartFactory.createChart(chartSelector.getValue(), csvFilePath);
        root.setCenter(chart);
        root.setTop(toolBar);

        // Al cambiar la selección se actualiza el gráfico 
        chartSelector.setOnAction(e -> {
            Node newChart = ChartFactory.createChart(chartSelector.getValue(), csvFilePath);
            root.setCenter(newChart);
        });

        // Ajusta el tamaño de la ventana basado en la resolución de pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() / 2.5);
        int height = (int) (screenSize.getHeight() / 1.8);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Ejemplo de JavaFX con Múltiples Gráficos y CSV");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
