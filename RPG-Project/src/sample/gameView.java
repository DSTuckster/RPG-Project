package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class gameView extends StackPane implements GameSubscriber {
    protected Controller controller;
    protected Canvas canvas;
    private GraphicsContext gc;
    private GraphicsContext gc2;
    TileManager t;

    final static int originalTileSize = 16;
    final static int scale = 3;
    final static int maxRow = 15;
    final static int maxCol =27;
    final int scaledTileSize = originalTileSize*scale;
    int[][] numberMap;



    public gameView(int width, int height) {
        /**Constructor that initializes a new canvas so the player object can be drawn
         * onto the Scene,
         * height: canvas height
         * width: canvas width*/
        Canvas canvas2 = new Canvas(width,height);
        canvas = new Canvas(width,height);
        gc = canvas.getGraphicsContext2D();
        gc2 = canvas2.getGraphicsContext2D();
        this.getChildren().addAll(canvas2,canvas);
        numberMap = new int[maxCol][maxRow];
        t = new TileManager();
        gc.setFill(Color.RED);
        loadMap();




    }

    public boolean checkTile(Entity entity,String direction) {
        int entityLeftX = entity.getX()+entity.getHitBoxX();
        int entityRightX = entity.getX()+entity.getHitBoxX()+entity.getHitBoxWidth();
        int entityTopY = entity.getY()+entity.getHitboxY();
        int entityBottomY = entity.getY()+entity.getHitboxY()+entity.getHitBoxHeight();

        int leftCol = entityLeftX/scaledTileSize;
        int rightCol = entityRightX/scaledTileSize;
        int topRow = entityTopY/scaledTileSize;
        int botRow = entityBottomY/scaledTileSize;

        int tile1,tile2;

        switch (direction){
            case "up":
                topRow = (entityTopY - entity.getSpeed())/scaledTileSize;
                tile1= numberMap[leftCol][topRow];
                tile2= numberMap[rightCol][topRow];
                if(t.getTileCollision(tile1) || t.getTileCollision(tile2)) {
                    return false;
                }
                else {
                    return true;
                }
            case "down":
                botRow = (entityBottomY + entity.getSpeed())/scaledTileSize;
                tile1= numberMap[leftCol][botRow];
                tile2= numberMap[rightCol][botRow];
                if(t.getTileCollision(tile1) || t.getTileCollision(tile2)) {
                    return false;
                }
                else {
                    return true;
                }
            case "left":
                leftCol = (entityLeftX - entity.getSpeed())/scaledTileSize;
                tile1= numberMap[leftCol][topRow];
                tile2= numberMap[leftCol][botRow];
                System.out.println(t.getTileCollision(tile1) || t.getTileCollision(tile2));;
                if(t.getTileCollision(tile1) || t.getTileCollision(tile2)) {

                    return false;
                }
                else {
                    return true;
                }
            case "right":
                rightCol = (entityRightX + entity.getSpeed())/scaledTileSize;
                tile1= numberMap[rightCol][topRow];
                tile2= numberMap[rightCol][botRow];
                if(t.getTileCollision(tile1) || t.getTileCollision(tile2)) {
                    return false;
                }
                else {
                    return true;
                }
        }
        return true;

    }


    

    public void loadMap() {
        try  {
            FileInputStream map = new FileInputStream("map.txt"); {
                Scanner scanner = new Scanner(map);
                int row=0,col=0;
                while (col < maxCol && row < maxRow) {
                    String newLine = scanner.nextLine();

                    while (col < maxCol) {
                        String chars[] = newLine.split( " ");
                        int num = Integer.parseInt(chars[col]);
                        numberMap[col][row] = num;
                        col++;
                    }
                    if(col == maxCol){
                        col =0;
                        row++;
                    }

                }
            scanner.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void drawMap() {
        int row=0,col=0,x=0,y =0;

        while (col < maxCol && row < maxRow) {
            int tile = numberMap[col][row];
            gc2.drawImage(t.getTileImage(tile),x,y,scaledTileSize,scaledTileSize);
            x+=scaledTileSize;
            col ++;
            if (col == maxCol) {
                x=0;
                col=0;
                row ++;
                y+= scaledTileSize;
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
            gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            for (Entity entity :controller.g.entities){
                gc.drawImage(entity.getImage(),entity.getX(),entity.getY(),scaledTileSize,scaledTileSize);
            }

    }




}




