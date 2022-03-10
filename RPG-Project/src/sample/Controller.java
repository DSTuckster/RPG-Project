package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {

    protected CombatModel combatModel;
    protected Text combatText;
    protected CharacterGenerator charModel;
    protected gameModel g;

    //sets the combat model and characterGen model
    public void setModels(CombatModel cm, gameModel gM, CharacterGenerator characterModel){
        combatModel = cm;
        g = gM;
        charModel = characterModel;
    }

    public void moveUp(){
        g.player.setY(g.player.getY()-g.player.getSpeed());
        g.player.setPlayerImage("up");}
    public void moveDown() {
        g.player.setY(g.player.getY()+g.player.getSpeed());
        g.player.setPlayerImage("down");}

    public void moveLeft() {
        g.player.setX(g.player.getX()-g.player.getSpeed());
        g.player.setPlayerImage("left");}
    public void moveRight() {
        g.player.setX(g.player.getX()+g.player.getSpeed());
        g.player.setPlayerImage("right");}


    public void dispose(){
        g.closeThread();
    }

    public void nextPhase(MouseEvent e) {
        if(combatModel.phase != combatModel.playerTurnPhase){
            try {
                combatModel.nextPhase();
                combatModel.typeOutDialogue(0, combatText);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void handleKeys(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> moveUp();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            default -> {
            }
        }
    }

    public void handleGenerateRandom(){
        charModel.generateRandom();
    }

    public void handleSave(ArrayList<String> custom) throws FileNotFoundException {
        charModel.generateCustom(custom);
        transData newSave = new transData(charModel.character);
    }


    public void handleAttack(){
        combatModel.attack();
    }

    public void handleRun(){
        combatModel.runAway();
    }

    public void handleMagic(){
        combatModel.usedMagic();
    }

}
