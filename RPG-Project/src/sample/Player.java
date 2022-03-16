package sample;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Player{
    private int x, y, speed;
    private Image up, down, left, right,current;

    public Player(){
        /**Simple constructor to initialize default values to the player
         */
        setDefaultValues();
    }


    private void setDefaultValues() {
        /** Sets the default x,y coordinates and speed values for our character so the view can draw
         * the character at its starting position, also loads all required images beforehand to make
         * running more efficient
         *  can cause a filenotfoundException if the png files are not loaded correctly
         */
        //initializing default values
        this.x = 100;
        this.y = 100;
        this.speed = 4;
        //loading images
        try {
            FileInputStream inputStream = new FileInputStream("boy_down_1.png");
            this.down = new Image(inputStream);
            inputStream = new FileInputStream("boy_up_1.png");
            this.up = new Image(inputStream);
            inputStream = new FileInputStream("boy_left_1.png");
            this.left = new Image(inputStream);
            inputStream = new FileInputStream("boy_right_1.png");
            this.right= new Image(inputStream);
            this.current = down;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // getters and setters for player attributes, all self-descriptive
    public int getX() { return x; }

    public int getY() { return y; }

    public int getSpeed() { return speed; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public void setPlayerImage(String direction) {
        /** This function changes the player image based on which string has been passed into the function
         * mainly used in conjunction with a key handler so that each picture is used when moving in the corresponding direction
         * String direction: a lowercase string of the direction the player is facing
         *
         */
        switch(direction){
            case "up" -> this.current = up;
            case "down" -> this.current = down;
            case "left" -> this.current =left;
            case "right" ->this.current=right;
            default -> this.current=down;
        }
    }

    public Image getPlayerImage() { //self-descriptive
        return this.current;
    }
}
