package iti.informatica.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataLoader {

    /**
     * Clase que representa los datos cargados desde un CSV junto con los encabezados.
     */
    public static class CSVResult<X, Y> {
        private final ObservableList<XYChart.Data<X, Y>> data;
        private final String xHeader;
        private final String yHeader;

        public CSVResult(ObservableList<XYChart.Data<X, Y>> data, String xHeader, String yHeader) {
            this.data = data;
            this.xHeader = xHeader;
            this.yHeader = yHeader;
        }

        public ObservableList<XYChart.Data<X, Y>> getData() {
            return data;
        }

        public String getXHeader() {
            return xHeader;
        }

        public String getYHeader() {
            return yHeader;
        }
    }

    /**
     * Carga datos para gráficos XY (por ejemplo, líneas o dispersión) esperando un CSV de dos columnas numéricas.
     * Retorna los encabezados junto con los datos.
     */
    public static CSVResult<Number, Number> loadXYDataWithHeaders(String csvFilePath) throws IOException {
        ObservableList<XYChart.Data<Number, Number>> dataList = FXCollections.observableArrayList();
        String xHeader = "X Axis";
        String yHeader = "Y Axis";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 2) continue;
                
                if (firstLine) {
                    try {
                        Double.parseDouble(tokens[0].trim());
                        // Si llegamos aquí, es un número y no un encabezado
                        Number x = Double.parseDouble(tokens[0].trim());
                        Number y = Double.parseDouble(tokens[1].trim());
                        dataList.add(new XYChart.Data<>(x, y));
                    } catch (NumberFormatException ex) {
                        // Es un encabezado
                        xHeader = tokens[0].trim();
                        yHeader = tokens[1].trim();
                        firstLine = false;
                        continue;
                    }
                } else {
                    try {
                        Number x = Double.parseDouble(tokens[0].trim());
                        Number y = Double.parseDouble(tokens[1].trim());
                        dataList.add(new XYChart.Data<>(x, y));
                    } catch (NumberFormatException ex) {
                        // Saltar líneas con datos mal formateados
                    }
                }
            }
        }
        
        return new CSVResult<>(dataList, xHeader, yHeader);
    }

    /**
     * Para mantener la compatibilidad con el código existente
     */
    public static ObservableList<XYChart.Data<Number, Number>> loadXYData(String csvFilePath) throws IOException {
        return loadXYDataWithHeaders(csvFilePath).getData();
    }

    /**
     * Carga datos para gráficos de barras o histogramas, incluyendo encabezados.
     */
    public static CSVResult<String, Number> loadCategoryDataWithHeaders(String csvFilePath) throws IOException {
        ObservableList<XYChart.Data<String, Number>> dataList = FXCollections.observableArrayList();
        String xHeader = "Categoría";
        String yHeader = "Valor";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 2) continue;
                
                if (firstLine) {
                    try {
                        Double.parseDouble(tokens[1].trim());
                        // Es un valor numérico, no un encabezado
                        String category = tokens[0].trim();
                        Number value = Double.parseDouble(tokens[1].trim());
                        dataList.add(new XYChart.Data<>(category, value));
                    } catch (NumberFormatException ex) {
                        // Es un encabezado
                        xHeader = tokens[0].trim();
                        yHeader = tokens[1].trim();
                        firstLine = false;
                        continue;
                    }
                } else {
                    try {
                        String category = tokens[0].trim();
                        Number value = Double.parseDouble(tokens[1].trim());
                        dataList.add(new XYChart.Data<>(category, value));
                    } catch (NumberFormatException ex) {
                        // Ignorar líneas con error en la conversión
                    }
                }
            }
        }
        
        return new CSVResult<>(dataList, xHeader, yHeader);
    }

    /**
     * Para mantener la compatibilidad con el código existente
     */
    public static ObservableList<XYChart.Data<String, Number>> loadCategoryData(String csvFilePath) throws IOException {
        return loadCategoryDataWithHeaders(csvFilePath).getData();
    }

    /**
     * Carga datos para gráficos de pastel, incluyendo un posible título del gráfico.
     */
    public static Pair<ObservableList<PieChart.Data>, String> loadPieDataWithTitle(String csvFilePath) throws IOException {
        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();
        String title = "Gráfico de Pastel";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 2) continue;
                
                if (firstLine) {
                    try {
                        Double.parseDouble(tokens[1].trim());
                        // Es un valor numérico, no un encabezado
                        String label = tokens[0].trim();
                        Number value = Double.parseDouble(tokens[1].trim());
                        dataList.add(new PieChart.Data(label, value.doubleValue()));
                    } catch (NumberFormatException ex) {
                        // Es un encabezado
                        title = tokens[0].trim();
                        firstLine = false;
                        continue;
                    }
                } else {
                    try {
                        String label = tokens[0].trim();
                        Number value = Double.parseDouble(tokens[1].trim());
                        dataList.add(new PieChart.Data(label, value.doubleValue()));
                    } catch (NumberFormatException ex) {
                        // Saltar registros mal formateados
                    }
                }
            }
        }
        
        return new Pair<>(dataList, title);
    }

    /**
     * Para mantener la compatibilidad con el código existente
     */
    public static ObservableList<PieChart.Data> loadPieData(String csvFilePath) throws IOException {
        return loadPieDataWithTitle(csvFilePath).getKey();
    }
}