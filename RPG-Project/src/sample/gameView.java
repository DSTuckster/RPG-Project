package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;



public class gameView extends StackPane implements GameSubscriber {
    protected Controller controller;

    public gameView(){
        Rectangle rectangle = new Rectangle(0,0,100,100);
        Group root = new Group(rectangle);

        this.getChildren().add(root);
    }

    public void setController(Controller c){
        controller = c;
    }

    public void modelChanged(){

    }
}




