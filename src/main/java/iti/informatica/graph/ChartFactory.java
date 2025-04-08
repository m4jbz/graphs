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
import javafx.util.Pair;

public class ChartFactory {

    /**
     * Método Factory que según el parámetro chartType crea y retorna un gráfico.
     * Si se proporciona un csvFilePath, se intentará cargar la información desde el archivo CSV.
     */
    public static Node createChart(String chartType, String csvFilePath) {
        switch (chartType.toLowerCase()) {
            case "de lineas":
                return createLineChart(csvFilePath);
            case "barras":
                return createBarChart(csvFilePath);
            case "pastel":
                return createPieChart(csvFilePath);
            case "dispersion":
                return createScatterChart(csvFilePath);
            case "histograma":
                return createHistogramChart(csvFilePath);
            default:
                // Si se solicita un tipo no implementado, se muestra un mensaje
                return new Label("Tipo de gráfico desconocido: " + chartType);
        }
    }
    
    /**
     * Crea un gráfico de líneas. Si csvFilePath no es null, se usan esos datos; de lo contrario se usan datos por defecto.
     */
    private static LineChart<Number, Number> createLineChart(String csvFilePath) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Gráfico de Líneas");
        
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Datos de Ejemplo");
        if (csvFilePath != null) {
            try {
                CSVDataLoader.CSVResult<Number, Number> result = CSVDataLoader.loadXYDataWithHeaders(csvFilePath);
                series.setData(result.getData());
                
                // Actualizar las etiquetas de los ejes con los encabezados del CSV
                xAxis.setLabel(result.getXHeader());
                yAxis.setLabel(result.getYHeader());
                
            } catch (Exception ex) {
                series.getData().add(new XYChart.Data<>(0, 0));
                lineChart.setTitle("Error al cargar CSV, datos por defecto");
            }
        } else {
            series.getData().add(new XYChart.Data<>(1, 5));
            series.getData().add(new XYChart.Data<>(2, 10));
            series.getData().add(new XYChart.Data<>(3, 15));
        }
        
        lineChart.getData().add(series);
        return lineChart;
    }
    
    /**
     * Crea un gráfico de barras. Si csvFilePath no es null, se cargan datos desde CSV; si no se usan datos por defecto.
     */
    private static BarChart<String, Number> createBarChart(String csvFilePath) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Categoría");
        yAxis.setLabel("Valor");
        
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Gráfico de Barras");
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Datos de Ejemplo");
        if (csvFilePath != null) {
            try {
                CSVDataLoader.CSVResult<String, Number> result = CSVDataLoader.loadCategoryDataWithHeaders(csvFilePath);
                series.setData(result.getData());
                
                // Actualizar las etiquetas de los ejes con los encabezados del CSV
                xAxis.setLabel(result.getXHeader());
                yAxis.setLabel(result.getYHeader());
                
            } catch (Exception ex) {
                series.getData().add(new XYChart.Data<>("Error", 0));
                barChart.setTitle("Error al cargar CSV, datos por defecto");
            }
        } else {
            series.getData().add(new XYChart.Data<>("A", 10));
            series.getData().add(new XYChart.Data<>("B", 20));
            series.getData().add(new XYChart.Data<>("C", 30));
        }
        
        barChart.getData().add(series);
        return barChart;
    }
    
    /**
     * Crea un gráfico de pastel. Si csvFilePath no es null, se cargan datos desde CSV; de lo contrario se usan datos por defecto.
     */
    private static PieChart createPieChart(String csvFilePath) {
        PieChart pieChart;
        if (csvFilePath != null) {
            try {
                Pair<ObservableList<PieChart.Data>, String> result = CSVDataLoader.loadPieDataWithTitle(csvFilePath);
                pieChart = new PieChart(result.getKey());
                
                // Establecer el título del gráfico según el encabezado del CSV si está disponible
                pieChart.setTitle(result.getValue());
                
            } catch (Exception ex) {
                // En caso de error se cargan datos por defecto
                pieChart = defaultPieChart();
                pieChart.setTitle("Error al cargar CSV, datos por defecto");
            }
        } else {
            pieChart = defaultPieChart();
            pieChart.setTitle("Gráfico de Pastel");
        }
        return pieChart;
    }
    
    /**
     * Método auxiliar para crear un gráfico de pastel con datos por defecto.
     */
    private static PieChart defaultPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Java", 30),
                new PieChart.Data("Python", 25),
                new PieChart.Data("C++", 20),
                new PieChart.Data("JavaScript", 25)
        );
        return new PieChart(pieChartData);
    }
    
    /**
     * Crea un gráfico de dispersión. Si csvFilePath no es null, se usan datos desde el CSV; de lo contrario, datos por defecto.
     */
    private static ScatterChart<Number, Number> createScatterChart(String csvFilePath) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Gráfico de Dispersión");
        
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Puntos de Datos");
        if (csvFilePath != null) {
            try {
                CSVDataLoader.CSVResult<Number, Number> result = CSVDataLoader.loadXYDataWithHeaders(csvFilePath);
                series.setData(result.getData());
                
                // Actualizar las etiquetas de los ejes con los encabezados del CSV
                xAxis.setLabel(result.getXHeader());
                yAxis.setLabel(result.getYHeader());
                
            } catch (Exception ex) {
                series.getData().add(new XYChart.Data<>(0, 0));
                scatterChart.setTitle("Error al cargar CSV, datos por defecto");
            }
        } else {
            series.getData().add(new XYChart.Data<>(5, 5));
            series.getData().add(new XYChart.Data<>(10, 15));
            series.getData().add(new XYChart.Data<>(15, 20));
        }
        
        scatterChart.getData().add(series);
        return scatterChart;
    }
    
    /**
     * Crea un "histograma" simulándolo con un gráfico de barras. Se usan datos desde CSV si csvFilePath es proporcionado.
     */
    private static BarChart<String, Number> createHistogramChart(String csvFilePath) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Intervalos");
        yAxis.setLabel("Frecuencia");
        
        BarChart<String, Number> histogram = new BarChart<>(xAxis, yAxis);
        histogram.setTitle("Histograma");
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Datos Histograma");
        if (csvFilePath != null) {
            try {
                CSVDataLoader.CSVResult<String, Number> result = CSVDataLoader.loadCategoryDataWithHeaders(csvFilePath);
                series.setData(result.getData());
                
                // Actualizar las etiquetas de los ejes con los encabezados del CSV
                xAxis.setLabel(result.getXHeader());
                yAxis.setLabel(result.getYHeader());
                
            } catch (Exception ex) {
                series.getData().add(new XYChart.Data<>("Error", 0));
                histogram.setTitle("Error al cargar CSV, datos por defecto");
            }
        } else {
            series.getData().add(new XYChart.Data<>("0-10", 5));
            series.getData().add(new XYChart.Data<>("10-20", 15));
            series.getData().add(new XYChart.Data<>("20-30", 10));
        }
        
        histogram.getData().add(series);
        return histogram;
    }
}