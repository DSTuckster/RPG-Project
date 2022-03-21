package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Controller {

    protected CombatModel combatModel;
    protected CharacterGenerator charModel;
    protected gameModel g;
    protected Entity player;
    protected Music nextMusic= new Music("next");

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
        e.setImage("up");
        if(g.subs.get(0).checkTile(e,"up")) {
            e.setY(e.getY() - e.getSpeed());
        }
    }
    public void moveDown(Entity e) {
        //self-descriptive
        e.setImage("down");
        if(g.subs.get(0).checkTile(e,"down")) {
            e.setY(e.getY() + e.getSpeed());

        }
        }

    public void moveLeft(Entity e) {
        //self-descriptive
        e.setImage("left");
        if(g.subs.get(0).checkTile(e,"left")) {
        e.setX(player.getX()-e.getSpeed());
        }}

    public void moveRight(Entity e) {
        //self-descriptive
        e.setImage("right");
        if(g.subs.get(0).checkTile(e,"right")) {
        e.setX(player.getX()+e.getSpeed());
        }}


    public void dispose(){

        //closes the thread
        g.closeThread();
    }

    public void switchScene(KeyEvent event) {
        // I assume logic for which combat scenario to set goes here (for now it is random; delete and change as needed) - Dylan
            //4th scenario is boss fight
        Random r = new Random();
        combatModel.setCombatScenario(combatModel.getCombatScenarios().get(r.nextInt(3)));

        nextMusic.stopMusic();
        Scene scene = (Scene) event.getSource();
        Stage stage = (Stage) scene.getWindow();
        CombatView c = (CombatView) combatModel.subs.get(0);
        Scene sceneCombat =c.getScene();
        stage.setScene(sceneCombat);
        g.isCurrent =false;
        g.isInvincible=true;
        stage.show();
        nextMusic= new Music("combat");
        nextMusic.playMusic(nextMusic.file);
    }

    public void genToTraversal(Scene scene, ArrayList<String> character){
        nextMusic.stopMusic();
        charModel.generateCustom(character);
        combatModel.setAllCombatScenarios(charModel.character);
        Stage stage = (Stage) scene.getWindow();
        gameView traversal = (gameView) g.subs.get(0);
        Scene sceneTraversal = traversal.getScene();
        stage.setScene(sceneTraversal);
        g.startThread();
        g.isCurrent=true;
        traversal.drawMap();
        stage.show();
        nextMusic= new Music("traversal");
        nextMusic.playMusic(nextMusic.file);
    }

    public void welcomeToGen(Scene scene, Music music){
        music.stopMusic();
        Stage stage = (Stage) scene.getWindow();
        CharacterGeneratorView charGen = (CharacterGeneratorView) charModel.subs.get(0);
        Scene sceneCharGen = charGen.getScene();
        stage.setScene(sceneCharGen);
        stage.show();
        nextMusic= new Music("generator");
        nextMusic.playMusic(nextMusic.file);
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
        if(!g.isInvincible && g.checkEncounter()) {
            switchScene(event);
        }
        switch (event.getCode()) {
            case UP -> moveUp(player);
            case DOWN -> moveDown(player);
            case LEFT -> moveLeft(player);
            case RIGHT -> moveRight(player);
            case A -> System.out.println(player.getX() + "_"+player.getY());
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
        combatModel.attack(combatModel.player, combatModel.enemy);
        combatModel.nextPhase();
    }

    public void handleRun(){
        combatModel.runAway();
    }

    public void handleMagic(){
        combatModel.usedMagic(combatModel.player, combatModel.enemy);
        combatModel.nextPhase();
    }

    public void handleHeal(){
        combatModel.heal(combatModel.player);
        combatModel.nextPhase();
    }

    public void handleCombatRest(){
        combatModel.restCombat();
    }

    public void handleNoReset(Scene scene) {
        nextMusic.stopMusic();
        g.isCurrent = true;
        g.isInvincible = true;
        Stage stage = (Stage) scene.getWindow();
        gameView traversal = (gameView) g.subs.get(0);
        Scene sceneTraverse = traversal.getScene();
        stage.setScene(sceneTraverse);
        stage.show();
        combatModel.restCombat();
        nextMusic= new Music("traversal");
        nextMusic.playMusic(nextMusic.file);
    }

    public void handleWin(Scene scene){
        g.entities.remove(1);
        this.handleNoReset(scene);
    }

    public void handlePlayWithSaved(Scene scene, Character character, Music music){
        music.stopMusic();
        charModel.setCharacter(character);
        combatModel.setCombatScenario(new CombatScenario(charModel.character, combatModel.createEnemy()));
        Stage stage = (Stage) scene.getWindow();
        gameView traversal = (gameView) g.subs.get(0);
        Scene sceneTraversal = traversal.getScene();
        stage.setScene(sceneTraversal);
        g.isCurrent=true;
        g.startThread();
        traversal.drawMap();
        stage.show();
        nextMusic= new Music("traversal");
        nextMusic.playMusic(nextMusic.file);
    }

    public void handleEdit(Scene scene, Character character,Music music){
        welcomeToGen(scene,music);
        charModel.editChar(character);
    }

}
