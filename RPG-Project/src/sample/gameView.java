package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class gameView extends StackPane implements GameSubscriber {
    protected Controller controller;
    protected Canvas canvas;
    private GraphicsContext gc;
    final static int originalTileSize = 16;
    final static int scale = 3;


    public gameView(int width, int height) {
        /**Constructor that initializes a new canvas so the player object can be drawn
         * onto the Scene,
         * height: canvas height
         * width: canvas width*/
        canvas = new Canvas(width,height);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        this.getChildren().addAll(canvas);

    }



    public void setController(Controller c){
        //self-descriptive
        controller = c;
    }


    public void modelChanged(int x, int y, Image image){
        /**This function clears previous images of the entity and draws the new image of the entity at its new x,y coordinates and
         * possibly with a new picture depicting it facing a new direction*/
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(image, x, y,originalTileSize*scale,originalTileSize*scale);

    }


}




