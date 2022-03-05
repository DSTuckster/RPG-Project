package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    protected Controller controller;

    protected CombatModel combatModel;
    protected CombatView combatView;

    protected gameModel g;
    protected gameView gView;

    protected int canHeight;
    protected int canWidth;

    @Override
    public void start(Stage primaryStage) throws Exception{
        canHeight=800;
        canWidth=800;
        controller = new Controller();
        combatModel = new CombatModel();
        combatView = new CombatView();
        g = new gameModel();
        gView = new gameView(canHeight,canWidth);

        //enable this line if you are running the traversal mechanic
        g.startThread();
        g.addSubscriber(gView);

        gView.setController(controller);

        combatView.setModel(combatModel);
        controller.setModels(combatModel, g);

        combatView.setController(controller);

        combatModel.addSubscriber(combatView);

        //NOTE: This is for testing the combatView, delete it later
        combatModel.setCombatScenario(new CombatScenario(new Character(), new Character()));


        HBox root = new HBox();

        //Change gView to combatView (or vice versa). If you want to see combat
            //we will have to find a way to do scene transitions
        Scene scene = new Scene(combatView, 800, 800);
        scene.setFill(Color.BLACK);
        scene.setOnKeyPressed(controller::handleKeys);


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent -> {
            controller.dispose(); // close thread
            Platform.exit(); // close gui thread
            System.exit(0); //kill JVM
        });


    }


    public static void main(String[] args) {
        launch(args);
    }
}
