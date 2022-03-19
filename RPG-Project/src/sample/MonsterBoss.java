package sample;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MonsterBoss  extends Monster implements Entity{

    public MonsterBoss() {
        setDefaultValues();
    }

    @Override
    public void setDefaultValues() {
        this.x = 1132;
        this.y = 444;
        this.speed = 0;
        try {
            FileInputStream inputStream = new FileInputStream("IndividualTiles/Dino1.png");
            this.image = new Image(inputStream);
            inputStream = new FileInputStream("IndividualTiles/Dino2.png");
            this.image2 = new Image(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
