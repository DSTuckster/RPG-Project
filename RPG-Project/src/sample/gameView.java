package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class gameView extends StackPane implements GameSubscriber {
    protected Controller controller;
    protected Canvas canvas;
    private GraphicsContext gc;
    TileManager t;

    final static int originalTileSize = 16;
    final static int scale = 3;
    final static int maxRow = 15;
    final static int maxCol =27;
    final int scaledTileSize = originalTileSize*scale;



    public gameView(int width, int height) {
        /**Constructor that initializes a new canvas so the player object can be drawn
         * onto the Scene,
         * height: canvas height
         * width: canvas width*/
        canvas = new Canvas(width,height);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        this.getChildren().addAll(canvas);
        t = new TileManager();



    }

    public void drawMap() {
        int col =0;
        int row =0;
        int x=0;
        int y=0;

        while (col < maxCol && row < maxRow) {
            gc.drawImage(t.getTileImage(),x,y,scaledTileSize,scaledTileSize);
            col ++;
            x+=scaledTileSize;
            if(col == maxCol){
                col = 0;
                x=0;
                row++;
                y+=scaledTileSize;

            }
        }

    }



    public void setController(Controller c){
        //self-descriptive
        controller = c;
    }


    public void modelChanged(){
        /**This function clears previous images of the entity and draws the new image of the entity at its new x,y coordinates and
         * possibly with a new picture depicting it facing a new direction*/
            drawMap();
            for (Entity entity :controller.g.entities){
                gc.drawImage(entity.getImage(),entity.getX(),entity.getY(),scaledTileSize,scaledTileSize);
            }

    }


}




