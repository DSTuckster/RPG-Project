package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

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
        g.setPlayerY(g.getPlayerY()-g.getPlayerSpeed());
    }
    public void moveDown() {
        g.setPlayerY(g.getPlayerY() + g.getPlayerSpeed());
    }
    public void moveLeft() {
        g.setPlayerX(g.getPlayerX() - g.getPlayerSpeed());
    }
    public void moveRight() {
        g.setPlayerX(g.getPlayerX() + g.getPlayerSpeed());
    }
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

    public void handleSave() throws FileNotFoundException {transData newSave = new transData(charModel.character);}


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
