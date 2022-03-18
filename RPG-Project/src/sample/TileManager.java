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
    ArrayList<Tile> tiles;
    public TileManager(){
        try {
            tiles = new ArrayList<>();
            for(int i=0;i<8;i++){
                tiles.add(new Tile());
            }

            FileInputStream inputStream = new FileInputStream("IndividualTiles/grass.png");
            tiles.get(0).image =(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Mountain.png");
            tiles.get(1).image=(new Image(inputStream));
            tiles.get(1).collision=true;
            inputStream = new FileInputStream("IndividualTiles/Trees1.png");
            tiles.get(2).image=(new Image(inputStream));
            tiles.get(2).collision=true;
            inputStream = new FileInputStream("IndividualTiles/Trees2.png");
            tiles.get(3).image=(new Image(inputStream));
            tiles.get(3).collision=true;
            inputStream = new FileInputStream("IndividualTiles/Pathway1.png");
            tiles.get(4).image=(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Pathway2.png");
            tiles.get(5).image=(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Pathway3.png");
            tiles.get(6).image=(new Image(inputStream));
            inputStream = new FileInputStream("IndividualTiles/Water.png");
            tiles.get(7).image=(new Image(inputStream));
            tiles.get(7).collision=true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Image getTileImage(int index){
        return tiles.get(index).image;
    }
}
