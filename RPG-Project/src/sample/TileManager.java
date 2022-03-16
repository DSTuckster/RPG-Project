package sample;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TileManager {
    ArrayList<Image> tileImages;
    public TileManager(){
        tileImages = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream("IndividualTiles/grass.png");
            tileImages.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Mountain.png");
            tileImages.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Trees1.png");
            tileImages.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Trees2.png");
            tileImages.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Pathway1.png");
            tileImages.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Pathway2.png");
            tileImages.add(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Pathway3.png");
            tileImages.add(new Image(inputStream));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getTileImage(int index){
        return tileImages.get(index);
    }
}
