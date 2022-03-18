package sample;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


public interface Entity {



    int getHitBoxWidth();
    int getHitBoxHeight();
    int getHitBoxX();
    int getHitboxY();
    void setDefaultValues();

    int getX();

    int getY();

    int getSpeed();

    void setX(int x) ;

    void setY(int y);

    void setImage(String direction);

    Image getImage();

}
