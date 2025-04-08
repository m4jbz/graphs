module iti.informatica.graph {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens iti.informatica.graph to javafx.graphics, javafx.fxml;
}
