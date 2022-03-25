package sample;

import javafx.scene.image.Image;

public interface GameSubscriber {

    void modelChanged();

    boolean checkTile(Entity entity,String d);

    void drawText(int x,int y);

    void clearText();
}
