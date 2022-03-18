package sample;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
// Map Legend index0: grass
//index 1: Mountain
//index 2: Trees1
//index 3: Trees2
//index 4; Pathway 1
//index 5: Pathway 2
//index 6: Pathway 3
//index 7: Water
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
            inputStream = new FileInputStream("IndividualTiles/Water.png");
            tileImages.add(new Image(inputStream));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public boolean getTileCollision(int index) {
        boolean collision;
        switch (index) {
            case 1 -> collision=true;
            case 2 -> collision=true;
            case 3 -> collision=true;
            case 7 -> collision=true;
            default -> collision=false;
        }
        return collision;
    }

    public Image getTileImage(int index){
        return tileImages.get(index);
    }
}
