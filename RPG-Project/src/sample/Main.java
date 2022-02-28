package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    protected Controller controller;

    protected CombatModel combatModel;
    protected CombatView combatView;

    //test classes
    protected CombatTests combatTests;

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controller();
        combatModel = new CombatModel();
        combatView = new CombatView();

        combatView.setModel(combatModel);
        controller.setModels(combatModel);

        combatView.setController(controller);

        combatModel.addSubscriber(combatView);

        //test classes
        combatTests = new CombatTests();
        combatTests.setCombatModel(combatModel);
        combatTests.runTests();


        HBox root = new HBox();
        Scene scene = new Scene(combatView, 800, 800);
        scene.setFill(Color.BLACK);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
