package iti.informatica.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class ChartFactory {

    /**
     * Método Factory que según el parámetro chartType crea y retorna un gráfico.
     */
    public static Node createChart(String chartType) {
        switch (chartType.toLowerCase()) {
            case "de lineas":
                return createLineChart();
            case "barras":
                return createBarChart();
            case "pastel":
                return createPieChart();
            case "dispersion":
                return createScatterChart();
            case "histograma":
                return createHistogramChart();
            default:
                // Si se solicita un tipo no implementado, se muestra un mensaje
                return new Label("Tipo de gráfico desconocido: " + chartType);
        }
    }
    
    /**
     * Crea un gráfico de líneas con datos de ejemplo.
     */
    private static LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Gráfico de Líneas");
        
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Datos de Ejemplo");
        series.getData().add(new XYChart.Data<>(1, 5));
        series.getData().add(new XYChart.Data<>(2, 10));
        series.getData().add(new XYChart.Data<>(3, 15));
        
        lineChart.getData().add(series);
        return lineChart;
    }
    
    /**
     * Crea un gráfico de barras con datos de ejemplo.
     */
    private static BarChart<String, Number> createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Categoría");
        yAxis.setLabel("Valor");
        
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Gráfico de Barras");
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Datos de Ejemplo");
        series.getData().add(new XYChart.Data<>("A", 10));
        series.getData().add(new XYChart.Data<>("B", 20));
        series.getData().add(new XYChart.Data<>("C", 30));
        
        barChart.getData().add(series);
        return barChart;
    }
    
    /**
     * Crea un gráfico de pastel con datos de ejemplo.
     */
    private static PieChart createPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Java", 30),
                new PieChart.Data("Python", 25),
                new PieChart.Data("C++", 20),
                new PieChart.Data("JavaScript", 25)
        );
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Gráfico de Pastel");
        return pieChart;
    }
    
    /**
     * Crea un gráfico de dispersión (scatter) con datos de ejemplo.
     */
    private static ScatterChart<Number, Number> createScatterChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Gráfico de Dispersión");
        
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Puntos de Datos");
        series.getData().add(new XYChart.Data<>(5, 5));
        series.getData().add(new XYChart.Data<>(10, 15));
        series.getData().add(new XYChart.Data<>(15, 20));
        
        scatterChart.getData().add(series);
        return scatterChart;
    }
    
    /**
     * Crea un "histograma" simulándolo con un gráfico de barras.
     * En un histograma se agrupan valores en intervalos (bins) y se muestran las frecuencias.
     */
    private static BarChart<String, Number> createHistogramChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Intervalos");
        yAxis.setLabel("Frecuencia");
        
        BarChart<String, Number> histogram = new BarChart<>(xAxis, yAxis);
        histogram.setTitle("Histograma");
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Datos Histograma");
        // Datos de ejemplo: cada entrada representa la frecuencia de un intervalo
        series.getData().add(new XYChart.Data<>("0-10", 5));
        series.getData().add(new XYChart.Data<>("10-20", 15));
        series.getData().add(new XYChart.Data<>("20-30", 10));
        
        histogram.getData().add(series);
        return histogram;
    }
}
