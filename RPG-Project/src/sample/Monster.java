package sample;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Monster implements Entity {
    Image image,image2,current;
    int x,y,speed;
    CombatScenario scenario;
    protected boolean isBoss = false;
    public Monster() {
        setDefaultValues();
    }

    public void setCombatScenario(CombatScenario scenario) {
        this.scenario = scenario;
    }
    public CombatScenario getCombat() {
        return this.scenario;
    }
    public int getHitBoxWidth() {
        return 0;
    }

    @Override
    public int getHitBoxHeight() {
        return 0;
    }

    @Override
    public int getHitBoxX() {
        return 0;
    }

    @Override
    public int getHitboxY() {
        return 0;
    }

    @Override
    public void setDefaultValues() {
        this.speed = 0;
        try {
            FileInputStream inputStream = new FileInputStream("IndividualTiles/Baby1.png");
            this.image = new Image(inputStream);
            inputStream = new FileInputStream("IndividualTiles/Baby2.png");
            this.image2 = new Image(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setX(int x) {
        this.x = x;

    }

    @Override
    public void setY(int y) {
        this.y = y;

    }

    @Override
    public void setImage(String direction) {
        if(this.current==this.image) {
            this.current = this.image2;
        }
        else {
            this.current = this.image;
        }

    }

    @Override
    public Image getImage() {
        return this.current;
    }
}
