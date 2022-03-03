package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;



public class gameView extends Application{
    Stage window;
    @Override
    public void start(Stage primaryStage) {
        Rectangle rectangle = new Rectangle(0,0,100,100);
        Group root = new Group(rectangle);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml")); //MUST CHANGE TO LOCALFXML FILE
        Controller controller =loader.getController();
        Scene scene = new Scene(root,1280,720);

        window = primaryStage;
        window.setScene(scene);
        window.setTitle("RPG Game");
        window.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
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
    }
}




