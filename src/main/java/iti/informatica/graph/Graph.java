package iti.informatica.graph;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.awt.Dimension;
import java.awt.Toolkit;


public class Graph extends Application {
    @Override
    public void start(Stage stage) {
        // Selector para elegir el tipo de gráfico
        ComboBox<String> chartSelector = new ComboBox<>();
        chartSelector.getItems().addAll("De lineas", "Barras", "Pastel", "Dispersion", "Histograma");
        chartSelector.setValue("Pastel"); // gráfico por defecto

        // Panel principal para colocar el selector y el gráfico
        BorderPane root = new BorderPane();
        // Se crea el gráfico por defecto (líneas)
        Node chart = ChartFactory.createChart(chartSelector.getValue());
        root.setCenter(chart);
        root.setTop(chartSelector);

        // Al cambiar la selección se actualiza el gráfico
        chartSelector.setOnAction(e -> {
            Node newChart = ChartFactory.createChart(chartSelector.getValue());
            root.setCenter(newChart);
        });
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) (screenSize.getWidth() / 2.5);
	int height = (int) (screenSize.getHeight() / 1.8);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Ejemplo de JavaFX con Múltiples Gráficos");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
