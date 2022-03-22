package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class CombatView extends StackPane implements CombatSubscriber{
    protected Image background, bossBackground;
    protected Button attack, run, magic, heal, retryYes, retryNo, next;
    protected ProgressBar playerXPBar, playerHealthBar, playerManaBar, enemyHealthBar, enemyManaBar;
    protected Label Enemy, Player, HP, XP, Mana, Retry, Dialogue, playerLevel, enemyLevel, enemyHP, enemyMana;
    protected CombatModel model;
    protected HBox bottomMain,hp ,xp, mana, top, enemyHPBOX, enemyMANABOX;
    protected VBox main, retryBottom, buttonsMain, defenseButtons, retry, enemy, player,diaNext, dialogueMain;
    protected ImageView imageView;
    protected FileInputStream inputStreamBaby, inputStreamBoss;

    /**
     * CombatView controller
     * @throws FileNotFoundException for background image if image is not found
     */
    public CombatView() throws FileNotFoundException {

        // Main Background Picture
        inputStreamBaby = new FileInputStream("background.png");
        background = new Image(inputStreamBaby);
        imageView = new ImageView();
        imageView.setImage(background);
        imageView.setFitWidth(1300);
        imageView.setFitHeight(800);

        // Boss Background Picture
        inputStreamBoss = new FileInputStream("bossBackground.png");
        bossBackground = new Image(inputStreamBoss);

        // XP, Mana, and Health Bars
        playerXPBar = new ProgressBar(0);
        playerXPBar.setStyle("-fx-accent: YELLOW");
        playerHealthBar = new ProgressBar(1);
        playerHealthBar.setStyle("-fx-accent: RED");
        playerManaBar = new ProgressBar(1);
        enemyHealthBar = new ProgressBar(1);
        enemyHealthBar.setStyle("-fx-accent: RED");
        enemyManaBar = new ProgressBar(1);

        // Bars Labels
        HP = new Label("HP");
        HP.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 13));
        HP.setTextFill(Color.BLACK);
        XP = new Label("XP");
        XP.setTextFill(Color.BLACK);
        XP.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 13));
        Mana = new Label("Mana");
        Mana.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 13));
        Mana.setTextFill(Color.BLACK);
        enemyHP = new Label("HP");
        enemyHP.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 13));
        enemyHP.setTextFill(Color.BLACK);
        enemyMana = new Label("Mana");
        enemyMana.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 13));
        enemyMana.setTextFill(Color.BLACK);


        hp = new HBox(playerHealthBar, HP);
        xp = new HBox(playerXPBar, XP);
        mana = new HBox(playerManaBar, Mana);
        enemyHPBOX = new HBox(enemyHealthBar, enemyHP);
        enemyMANABOX = new HBox(enemyManaBar, enemyMana);

        Enemy = new Label();
        Enemy.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));
        Enemy.setWrapText(true);
        Enemy.setTextFill(Color.BLACK);
        enemyLevel = new Label();
        enemyLevel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));
        enemyLevel.setTextFill(Color.BLACK);
        Player = new Label();
        Player.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));
        Player.setWrapText(true);
        Player.setTextFill(Color.BLACK);
        playerLevel = new Label();
        playerLevel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));
        playerLevel.setTextFill(Color.BLACK);
        enemy = new VBox();
        player = new VBox();
        enemy.getChildren().addAll(Enemy, enemyLevel, enemyHPBOX, enemyMANABOX);
        player.getChildren().addAll(Player, playerLevel, hp, xp, mana);
        player.setSpacing(5);
        enemy.setSpacing(5);
        player.setStyle("-fx-padding: 200 0 0 100");
        enemy.setStyle("-fx-padding: 30 0 0 800");

        // Dialogue Label
        Dialogue = new Label();
        Dialogue.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));
        Dialogue.setTextFill(Color.BLACK);

        // All Buttons
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);

        attack = new Button("Attack");
        attack.setFont(font);
        attack.setStyle("-fx-background-color: WHITE; -fx-padding: 10, 10, 10, 10");

        run = new Button("Run");
        run.setFont(font);
        run.setStyle("-fx-background-color: WHITE; -fx-padding: 10 10 10 10");

        magic = new Button("Magic");
        magic.setDisable(false);
        magic.setFont(font);
        magic.setStyle("-fx-background-color: WHITE; -fx-padding: 10 10 10 10");

        heal = new Button("Heal");
        heal.setDisable(false);
        heal.setFont(font);
        heal.setStyle("-fx-background-color: WHITE; -fx-padding: 10 10 10 10");

        Retry = new Label("Would you like to retry?");
        Retry.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        retryYes = new Button("Yes");
        retryYes.setFont(font);
        retryYes.setStyle("-fx-background-color: WHITE");
        retryNo = new Button("No");
        retryNo.setFont(font);
        retryNo.setStyle("-fx-background-color: WHITE");


        // Coordinate all top boxes together to fit screen properly
        top = new HBox();
        top.getChildren().addAll(player, enemy);


        // Will be used for smoother dialogue in the place of other buttons
        next = new Button("Head Back");
        next.setFont(font);
        next.setStyle("-fx-background-color: WHITE");
        diaNext = new VBox(Dialogue, next);
        diaNext.setAlignment(Pos.BOTTOM_CENTER);
        diaNext.setSpacing(50);


        // Retry bottom of screen for CombatView
        retryBottom = new VBox(Retry, retryYes, retryNo);
        retryBottom.setSpacing(25);
        retryBottom.setAlignment(Pos.BOTTOM_CENTER);
        retry = new VBox();
        retry.getChildren().addAll(top, retryBottom);

        // Main bottom of screen for CombatView
        buttonsMain = new VBox();
        bottomMain = new HBox();
        defenseButtons = new VBox();
        buttonsMain.getChildren().addAll(attack, magic);
        buttonsMain.setSpacing(100);
        defenseButtons.getChildren().addAll(run, heal);
        defenseButtons.setSpacing(100);
        bottomMain.getChildren().addAll(buttonsMain, defenseButtons);
        bottomMain.setAlignment(Pos.CENTER);
        bottomMain.setSpacing(200);
        dialogueMain = new VBox(Dialogue, bottomMain);
        dialogueMain.setAlignment(Pos.CENTER);
        main = new VBox();
        main.getChildren().addAll(top, dialogueMain);
        main.setSpacing(90);


        this.getChildren().addAll(imageView, main);
    }

    /**
     * Notifies controller that a button has been pressed
     * @param controller The views controller to handle user interaction
     */
    protected void setController(Controller controller){
        attack.setOnAction(e -> controller.handleAttack());
        run.setOnAction((e -> controller.handleRun()));
        magic.setOnAction(e -> controller.handleMagic());
        heal.setOnAction(e -> controller.handleHeal());
        retryYes.setOnAction(e -> {controller.handleCombatRest();this.reset();});
        retryNo.setOnAction(e -> {controller.handleNoReset(this.getScene());this.reset();});
        next.setOnAction(e -> {controller.handleWin(this.getScene());this.reset();});
        main.setOnMousePressed(controller::nextPhase);
    }

    /**
     * The views model to receive updates from (and to subscribe too)
     * @param comModel the model to set as the views model
     */
    protected void setModel(CombatModel comModel){
        model = comModel;
    }

    /**
     * Set player and enemy names/character level
     */
    protected void setNames(){
        Player.setText(model.player.getName());
        playerLevel.setText("Level: " + model.player.characterStats.getCharacterLevel());
        Enemy.setText(model.enemy.getName());
        enemyLevel.setText("Level: " + model.enemy.characterStats.getCharacterLevel());
    }

    protected void setBossBackground() {
        imageView.setImage(bossBackground);
    }

    /**
     * Update view according to what has changed in the model
     */
    @Override
    public void modelChanged() {

        //setBossBackground();
        Dialogue.setText(model.getCurrentDialogue());

        setNames();

        this.setBars();
        this.checkTurn();

        // If player loses, option to replay
        if (model.player.characterStats.getHealth() <= 0) {
            playerHealthBar.setProgress(0); // So it doesn't look like player has negative health
            this.retryButtons();
        }
        // If player wins, go back to traversal scene
        else if (model.enemy.characterStats.getHealth() <= 0) {
            enemyHealthBar.setProgress(0);
            this.end();
        }
        // If run is successful, go back to traversal scene
        if (model.runAway) {
            this.retryNo.fire();
        }

        // If mana bar is empty then player can no longer use magic button
        if (model.player.characterStats.getMana() <= 0 || model.player.characterStats.getMana() < model.costPerSpell) {
            magic.setDisable(true);
            heal.setDisable(true);
        }

    }

    private void setBars() {
        // Get current health, xp, and mana for the progress bars
        // Normalized for progress bar needing number between 0-1
        playerManaBar.setProgress((float) model.player.characterStats.getMana() / model.player.characterStats.getMaxMana());
        playerHealthBar.setProgress((float) model.player.characterStats.getHealth() / model.player.characterStats.getMaxHealth());
        playerXPBar.setProgress((float) model.player.characterStats.getExp() / model.player.characterStats.getMaxExp());
        enemyHealthBar.setProgress((float) model.enemy.characterStats.getHealth() / model.enemy.characterStats.getMaxHealth());
        enemyManaBar.setProgress((float) model.enemy.characterStats.getMana() / model.enemy.characterStats.getMaxMana());
    }

    private void checkTurn() {
        if (model.playerTurnPhase == model.phase) {
            if (model.player.characterStats.getMana() >= model.costPerSpell) {
                magic.setDisable(false);
                heal.setDisable(false);
            }
            attack.setDisable(false);
            run.setDisable(false);
        } else {
            magic.setDisable(true);
            heal.setDisable(true);
            attack.setDisable(true);
            run.setDisable(true);
        }
    }

    private void reset() {
        this.getChildren().retainAll();
        this.getChildren().addAll(imageView, Dialogue, main);
        model.reset = false;
    }


    private void retryButtons(){
        this.getChildren().remove(1);
        this.getChildren().add(1, retry);
    }

    private void end(){
        Dialogue.setText("Player Wins!");
        this.getChildren().clear();
        this.getChildren().addAll(imageView, Dialogue, diaNext);
    }
}
