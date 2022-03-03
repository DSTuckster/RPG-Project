package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    protected Controller controller;

    protected CombatModel combatModel;
    protected CombatView combatView;

    protected gameModel g;
    protected gameView gView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controller();
        combatModel = new CombatModel();
        combatView = new CombatView();
        g = new gameModel();
        gView = new gameView();

        g.startThread();

        gView.setController(controller);

        combatView.setModel(combatModel);
        controller.setModels(combatModel, g);

        combatView.setController(controller);

        combatModel.addSubscriber(combatView);

        //NOTE: This is for testing the view, delete it later
        //combatModel.setCombatScenario(new CombatScenario(new Character(), new Character()));


        HBox root = new HBox();
        Scene scene = new Scene(gView, 800, 800);
        scene.setFill(Color.BLACK);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        System.out.println("yes");
                        controller.moveUp();
                        break;
                    case DOWN:
                        controller.moveDown();
                        break;
                    case LEFT:
                        controller.moveLeft();
                        break;
                    case RIGHT:
                        controller.moveRight();
                        break;
                    default:
                        break;

                }

            }
        });

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
