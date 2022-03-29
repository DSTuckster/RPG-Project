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
    protected CreditsModel creditsModel;
    protected Entity player;
    protected Music nextMusic= new Music("next");

    /**
     * Sets models for controller
     * @param cm combat model
     * @param gM traversal mode
     * @param characterModel character generation model
     * @param newCreditsModel credits model
     */
    public void setModels(CombatModel cm, gameModel gM, CharacterGenerator characterModel, CreditsModel newCreditsModel){
        combatModel = cm;
        g = gM;
        charModel = characterModel;
        creditsModel = newCreditsModel;
    }

    /**
     * Initializes entities if they don't already exist (player, enemies) for traversal scene
     */
    public void initPlayer() {
        if(this.player ==null) {
            this.player = g.entities.get(0);
        }
    }

    /**
     * Moves the given entity up
     * @param e the entity to move
     */
    public void moveUp(Entity e){
        e.setImage("up");
        if(g.subs.get(0).checkTile(e,"up")) {
            e.setY(e.getY() - e.getSpeed());
        }
    }

    /**
     * Move the given entity down
     * @param e the entity to move
     */
    public void moveDown(Entity e) {
        e.setImage("down");
        if(g.subs.get(0).checkTile(e,"down")) {
            e.setY(e.getY() + e.getSpeed());
        }
    }

    /**
     * Moves the given entity left
     * @param e the entity to move
     */
    public void moveLeft(Entity e) {
        e.setImage("left");
        if (g.subs.get(0).checkTile(e, "left")) {
            e.setX(player.getX() - e.getSpeed());
        }
    }

    /**
     * Moves the given entity right
     * @param e the entity to move
     */
    public void moveRight(Entity e) {
        e.setImage("right");
        if(g.subs.get(0).checkTile(e,"right")) {
        e.setX(player.getX()+e.getSpeed());
        }}


    /**
     * Switches scene from traversal to combat view with proper enemy (boss or minion) when
     * player and enemy collision occurs in traversal
     * @param event the collision
     */
    public void switchScene(KeyEvent event) {
        nextMusic.stopMusic();
        Monster m = (Monster) g.getClosest();

        if(m.isBoss){
            combatModel.setCombatScenario(combatModel.getCombatScenarios().get(3));
            nextMusic= new Music("boss");
            nextMusic.playMusic(nextMusic.file);
            combatModel.setCombatScenario(combatModel.getCombatScenarios().get(3));
        }else{
            combatModel.setCombatScenario(combatModel.getCombatScenarios().get(g.entities.indexOf(m)-1));
            nextMusic= new Music("combat");
            nextMusic.playMusic(nextMusic.file);

            combatModel.setCombatScenario(combatModel.getCombatScenarios().get(g.entities.indexOf(m)-1));
        }
        Scene scene = (Scene) event.getSource();
        Stage stage = (Stage) scene.getWindow();
        CombatView c = (CombatView) combatModel.subs.get(0);
        combatModel.createEnemy();
        Scene sceneCombat =c.getScene();
        stage.setScene(sceneCombat);
        g.isCurrent =false;
        g.isInvincible=true;
        stage.show();
    }

    /**
     * Switches scene from character generator view to the traversal view
     * @param scene The scene the user is currently in (Generator View)
     * @param character The character the user wants to play with
     */
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

    /**
     * Changes scene from Welcome View to Character Generator View
     * @param scene The users current scene (Welcome View)
     * @param music The music being played
     */
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

    /**
     * Handles going to next phase in combat (handling player turn vs enemy turn)
     * @param e the event to trigger the next phase (Mouse click)
     */
    public void nextPhase(MouseEvent e) {
        if(combatModel.phase != combatModel.playerTurnPhase){
            combatModel.nextPhase();
        }
    }

    /**
     * Handles player using arrow keys to move in traversal scene
     * @param event The key that was pressed
     */
    public void handleKeys(KeyEvent event) {
        initPlayer();
        if(!g.isInvincible && g.checkEncounter()) {
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

    /**
     * Calls to create a new random character when generate random button is pressed
     */
    public void handleGenerateRandom(){
        charModel.generateRandom();
    }

    /**
     * Calls to save the character currently in the generator when user clicks Save
     * @param custom The list of attributes to set to the new character
     */
    public void handleSave(ArrayList<String> custom) throws FileNotFoundException {
        charModel.generateCustom(custom);
        SaveSystem.SaveToFile("save.txt",charModel.character);

    }

    /**
     * Handles telling combat model that the player chose to Attack the enemy
     */
    public void handleAttack(){
        combatModel.attack(combatModel.player, combatModel.enemy);
        combatModel.nextPhase();
    }

    /**
     * Handles telling combat model that the player clicked run in the combat
     */
    public void handleRun(){
        combatModel.runAway();
    }

    /**
     * Handles telling combat model that the player chose Magic in the combat
     */
    public void handleMagic(){
        combatModel.usedMagic(combatModel.player, combatModel.enemy);
        combatModel.nextPhase();
    }

    /**
     * Handles telling combat model that the player chose to Heal during combat
     */
    public void handleHeal(){
        combatModel.heal(combatModel.player);
        combatModel.nextPhase();
    }

    /**
     * Handles player choosing to retry combat with the enemy (reset the same combat)
     */
    public void handleCombatReset(){
        combatModel.restCombat();
    }

    /**
     * Switches scene back to traversal from combat when the player does not want to retry, or has beat the enemy
     * @param scene The current scene the player is in (Combat)
     */
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

    /**
     * Calls handleNoReset when player has won. Or calls handleGameEnd if player has won the game
     * @param scene The current scene the player is in (Combat)
     */
    public void handleWin(Scene scene){

        g.entities.remove(g.getClosest());

        if(g.entities.size()<=1) {
            this.handleGameEnd(scene);
        }
        else {
            this.handleNoReset(scene);
        }
    }

    /**
     * Switches scene to the ending credits once the game has been beat
     * @param scene the scene the player is currently in (Combat)
     */
    public void handleGameEnd(Scene scene) {

        nextMusic.stopMusic();

        Stage stage = (Stage) scene.getWindow();
        CreditsView credits = (CreditsView) creditsModel.subscribers.get(0);
        Scene sceneCredits = credits.getScene();
        stage.setScene(sceneCredits);
        stage.show();

        creditsModel.notifySubscribers();

        nextMusic= new Music("last");
        nextMusic.playMusic(nextMusic.file);
    }

    /**
     * Switches from welcome view to traversal when player wants to play with saved character
     * @param scene The scene the player is currently in (Welcome View)
     * @param character The saved character the player has chosen to play with
     * @param music The music playing
     */
    public void handlePlayWithSaved(Scene scene, Character character, Music music){

        music.stopMusic();

        charModel.setCharacter(character);
        combatModel.setAllCombatScenarios(charModel.character);
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

    /**
     * Switches from Welcome View to Character Generator with preset values from saved character to edit
     * @param scene The scene the user is currently in (Welcome View)
     * @param character The saved character the player wants to edit
     * @param music The music playing
     */
    public void handleEdit(Scene scene, Character character,Music music){

        welcomeToGen(scene,music);
        charModel.editChar(character);
    }

}
