package sample;

import javafx.scene.image.Image;

public interface GameSubscriber {

    public void modelChanged(int x, int y, Image image);
}
