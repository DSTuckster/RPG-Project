module RPG.Project {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;

    opens sample to javafx.fxml;
    exports sample;
}