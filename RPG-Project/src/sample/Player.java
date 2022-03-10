package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Player{
    private int x, y, speed;
    private Image up, down, left, right,current;

    public Player(){
        setDefaultValues();
    }


    private void setDefaultValues() {
        this.x = 100;
        this.y = 100;
        this.speed = 64;
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

    // getters and setters for player attributes
    public int getX() { return x; }

    public int getY() { return y; }

    public int getSpeed() { return speed; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public void setPlayerImage(String direction) {
        switch(direction){
            case "up" -> this.current = up;
            case "down" -> this.current = down;
            case "left" -> this.current =left;
            case "right" ->this.current=right;
            default -> this.current=down;
        }
    }

    public Image getPlayerImage() {
        return this.current;
    }
}
