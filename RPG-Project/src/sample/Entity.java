package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;

public interface Entity {

    void setDefaultValues();

    int getX();

    int getY();

    int getSpeed();

    void setX(int x) ;

    void setY(int y);

    void setImage(String direction);

    Image getImage();

}
