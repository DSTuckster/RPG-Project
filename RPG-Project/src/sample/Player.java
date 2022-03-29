package sample;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Player implements Entity {
    private int x, y, speed;
    private Image current;
    ArrayList<Image> images;
    private int playerCount;
    private boolean defaultImg;
    public Rectangle solidArea;

    /**
     * Simple constructor to initialize default values to the player
     */
    public Player(){
        setDefaultValues();
    }

    /** Sets the default x,y coordinates and speed values for our character so the view can draw
     * the character at its starting position, also loads all required images beforehand to make
     * running more efficient
     * can cause a filenotfoundException if the png files are not loaded correctly
     */
    public void setDefaultValues() {
        //initializing default values
        images = new ArrayList<>();
        this.x = 200;
        this.y = 272;
        this.speed = 4;
        solidArea = new Rectangle();
        solidArea.setWidth(32);
        solidArea.setHeight(32);
        solidArea.setX(8);
        solidArea.setY(16);

        //loading images
        try {
            FileInputStream inputStream = new FileInputStream("IndividualTiles/Player1.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Player7.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Player3.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Player5.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Player2.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Player8.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Player4.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Player6.png");
            images.add(new Image(inputStream));
            this.current = images.get(0);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public int getHitBoxWidth() {
        return (int)solidArea.getWidth();
    }
    public int getHitBoxHeight() {
        return (int)solidArea.getHeight();
    }
    public int getHitBoxX(){ return (int)this.solidArea.getX();}
    public int getHitboxY(){ return (int)this.solidArea.getY(); }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public int getSpeed() { return this.speed; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }




    /** This function changes the player image based on which string has been passed into the function
     * mainly used in conjunction with a key handler so that each picture is used when moving in the corresponding direction
     * @param direction: a lowercase string of the direction the player is facing
     */
    public void setImage(String direction) {

        if(playerCount<15 && defaultImg) {
            switch (direction) {
                case "up" -> this.current = images.get(1);
                case "left" -> this.current = images.get(2);
                case "right" -> this.current = images.get(3);
                default -> this.current = images.get(0);
            }
            playerCount++;
        }
        else if(playerCount<15) {
            switch (direction) {
                case "up" -> this.current = images.get(5);
                case "left" -> this.current = images.get(6);
                case "right" -> this.current = images.get(7);
                default -> this.current = images.get(4);
            }
            playerCount++;
        }
        else {
            defaultImg = !defaultImg;
            playerCount=0;

        }
    }

    public Image getImage() { //self-descriptive
        return this.current;
    }
}
