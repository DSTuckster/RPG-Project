module RPG.Project {
    requires javafx.graphics;
    requires javafx.fxml;

    opens sample to javafx.fxml;
    exports sample;
}