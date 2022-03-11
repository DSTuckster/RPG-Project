package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {

    protected CombatModel combatModel;
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

    public void genToTraversal(Scene scene){
        Stage stage = (Stage) scene.getWindow();
        gameView traversal = (gameView) g.subs.get(0);
        Scene sceneTraversal = traversal.getScene();
        stage.setScene(sceneTraversal);
        stage.show();
    }

    public void welcomeToGen(Scene scene){
        Stage stage = (Stage) scene.getWindow();
        CharacterGeneratorView charGen = (CharacterGeneratorView) charModel.subs.get(0);
        Scene sceneCharGen = charGen.getScene();
        stage.setScene(sceneCharGen);
        stage.show();
    }

    public void nextPhase(MouseEvent e) {
        if(combatModel.phase != combatModel.playerTurnPhase){
            combatModel.nextPhase();
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

    public void handleNoReset(Scene scene) {
        Stage stage = (Stage) scene.getWindow();
        gameView traversal = (gameView) g.subs.get(0);
        Scene sceneTraverse = traversal.getScene();
        stage.setScene(sceneTraverse);
        stage.show();

        combatModel.endCombat();
        combatModel.restCombat();
    }

    public void handleWin(Scene scene){
        this.handleNoReset(scene);
    }

}
