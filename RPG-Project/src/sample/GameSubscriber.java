package sample;

import javafx.scene.image.Image;

public interface GameSubscriber {

    void modelChanged();

    boolean checkTile(Entity entity,String d);
}
