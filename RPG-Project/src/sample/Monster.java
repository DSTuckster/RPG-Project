package sample;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Monster implements Entity {
    Image image;
    int x,y,speed;
    public Monster() {
        setDefaultValues();
    }

    @Override
    public void setDefaultValues() {
        this.x = 480;
        this.y =240;
        this.speed = 0;
        try {
            FileInputStream inputStream = new FileInputStream("IndividualTiles/Dino1.png");
            this.image = new Image(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }

    @Override
    public void setImage(String direction) {
        this.image = this.image;

    }

    @Override
    public Image getImage() {
        return this.image;
    }
}
