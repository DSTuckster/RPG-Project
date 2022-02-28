module RPG.Project {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens sample to javafx.fxml;
    exports sample;
}