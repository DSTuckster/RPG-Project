package sample;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        //self-descriptive
        g.player.setY(g.player.getY()-g.player.getSpeed());
        g.player.setPlayerImage("up");}
    public void moveDown() {
        //self-descriptive
        g.player.setY(g.player.getY()+g.player.getSpeed());
        g.player.setPlayerImage("down");}

    public void moveLeft() {
        //self-descriptive
        g.player.setX(g.player.getX()-g.player.getSpeed());
        g.player.setPlayerImage("left");}

    public void moveRight() {
        //self-descriptive
        g.player.setX(g.player.getX()+g.player.getSpeed());
        g.player.setPlayerImage("right");}


    public void dispose(){

        //closes the thread
        g.closeThread();
    }

    public void switchScene(KeyEvent event) {
        Scene node =  (Scene)event.getSource();
        Stage thisStage = (Stage) node.getWindow();
        CombatView c = (CombatView) combatModel.subs.get(0);
        Scene sceneCombat =c.getScene();
        thisStage.setScene(sceneCombat);
        thisStage.show();
    }

    public void nextPhase(MouseEvent e) {
        if(combatModel.phase != combatModel.playerTurnPhase){
            try {
                combatModel.nextPhase();
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void handleKeys(KeyEvent event) {
        /**This function handles key inputs from the view and determines which function to call and send the data to the model
         */
        switch (event.getCode()) {
            case UP -> moveUp();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            case A -> switchScene(event);
            default -> {
            }
        }
    }

    public void handleGenerateRandom(){
        charModel.generateRandom();
    }

    public void handleSave(ArrayList<String> custom) throws FileNotFoundException {
        charModel.generateCustom(custom);
        SaveSystem.SaveToFile("save.txt",charModel.character);
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

    public void handleCombatRest(){
        combatModel.restCombat();
    }

}
