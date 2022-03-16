package sample;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Player implements Entity{
    private int x, y, speed;
    private Image current;
    ArrayList<Image> images;

    public Player(){
        /**Simple constructor to initialize default values to the player
         */
        setDefaultValues();
    }


        public void setDefaultValues() {
        /** Sets the default x,y coordinates and speed values for our character so the view can draw
         * the character at its starting position, also loads all required images beforehand to make
         * running more efficient
         *  can cause a filenotfoundException if the png files are not loaded correctly
         */
        //initializing default values
        images = new ArrayList<>();
        this.x = 0;
        this.y = 0;
        this.speed = 4;
        //loading images
        try {
            FileInputStream inputStream = new FileInputStream("boy_down_1.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("boy_up_1.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("boy_left_1.png");
            images.add(new Image(inputStream));
            inputStream = new FileInputStream("boy_right_1.png");
            images.add(new Image(inputStream));
            this.current = images.get(0);
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

    public void setImage(String direction) {
        /** This function changes the player image based on which string has been passed into the function
         * mainly used in conjunction with a key handler so that each picture is used when moving in the corresponding direction
         * String direction: a lowercase string of the direction the player is facing
         *
         */
        switch(direction){
            case "down" -> this.current = images.get(0);
            case "up" -> this.current = images.get(1);
            case "left" -> this.current =images.get(2);
            case "right" ->this.current=images.get(3);
            default -> this.current=images.get(0);
        }
    }

    public Image getImage() { //self-descriptive
        return this.current;
    }
}
