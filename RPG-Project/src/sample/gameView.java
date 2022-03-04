package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;



public class gameView extends StackPane implements GameSubscriber {
    protected Controller controller;
    protected Canvas canvas;
    private GraphicsContext gc;

    public gameView(int height, int width) {
        canvas = new Canvas(height,width);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        this.getChildren().addAll(canvas);
    }



    public void setController(Controller c){
        controller = c;
    }


    public void modelChanged(int x, int y){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        gc.strokeRect(x,y, 100, 100);
        gc.fillRect(x,y,100,100);

    }


}




