package sample;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TileManager {
    private Image tileImage;
    public TileManager(){
        try {
            FileInputStream inputStream = new FileInputStream("tile.png");
            tileImage = new Image(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getTileImage(){
        return tileImage;
    }
}
