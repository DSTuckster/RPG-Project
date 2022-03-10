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
    private Image image;

    public Player(){
        setDefaultValues();
    }


    private void setDefaultValues() {
        this.x = 100;
        this.y = 100;
        this.speed = 5;
        try {
            FileInputStream inputStream = new FileInputStream("boy_down_1.png");
            this.image = new Image(inputStream);
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

    public void setPlayerImage(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            this.image = new Image(inputStream);
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

    public Image getPlayerImage() {
        return image;
    }
}
