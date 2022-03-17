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
    protected Entity player;


    //sets the combat model and characterGen model
    public void setModels(CombatModel cm, gameModel gM, CharacterGenerator characterModel){
        combatModel = cm;
        g = gM;
        charModel = characterModel;
    }

    public void initPlayer() {
        if(this.player ==null) {
            this.player = g.entities.get(0);
        }
    }

    public void moveUp(Entity e){
        //self-descriptive
        e.setY(e.getY()-e.getSpeed());
        e.setImage("up");}
    public void moveDown(Entity e) {
        //self-descriptive
        e.setY(e.getY()+e.getSpeed());
        e.setImage("down");}

    public void moveLeft(Entity e) {
        //self-descriptive
        e.setX(player.getX()-e.getSpeed());
        e.setImage("left");}

    public void moveRight(Entity e) {
        //self-descriptive
        e.setX(player.getX()+e.getSpeed());
        e.setImage("right");}


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

    public void genToTraversal(Scene scene, ArrayList<String> character){
        charModel.generateCustom(character);
        combatModel.setCombatScenario(new CombatScenario(charModel.character, combatModel.createEnemy()));
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
        initPlayer();
        if(g.checkEncounter()) {
            switchScene(event);
        }

        switch (event.getCode()) {
            case UP -> moveUp(player);
            case DOWN -> moveDown(player);
            case LEFT -> moveLeft(player);
            case RIGHT -> moveRight(player);
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
        combatModel.nextPhase();
    }

    public void handleRun(){
        combatModel.runAway();
    }

    public void handleMagic(){
        combatModel.usedMagic();
        combatModel.nextPhase();
    }

    public void handleHeal(){
        combatModel.heal();
        combatModel.nextPhase();
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

        combatModel.restCombat();
    }

    public void handleWin(Scene scene){
        this.handleNoReset(scene);
    }

    public void handlePlayWithSaved(Scene scene, Character character){
        charModel.setCharacter(character);
        combatModel.setCombatScenario(new CombatScenario(charModel.character, combatModel.createEnemy()));
        Stage stage = (Stage) scene.getWindow();
        gameView traversal = (gameView) g.subs.get(0);
        Scene sceneTraversal = traversal.getScene();
        stage.setScene(sceneTraversal);
        stage.show();
    }

    public void handleEdit(Scene scene, Character character){
        welcomeToGen(scene);
        charModel.editChar(character);
    }

}
