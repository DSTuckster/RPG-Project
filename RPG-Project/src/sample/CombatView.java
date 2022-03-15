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
    protected Image background;
    protected Button attack, run, magic, retryYes, retryNo, next;
    protected ProgressBar playerXPBar, playerHealthBar, playerManaBar, enemyHealthBar, enemyManaBar;
    protected Label Enemy, Player, HP, XP, Mana, Retry, Dialogue;
    protected CombatModel model;
    protected HBox bottomMain,hp ,xp, mana, top;
    protected VBox main, retryBottom, buttonsMain, retry, enemy, player,diaNext, dialogueMain;
    protected ImageView imageView;
    protected FileInputStream inputStream;

    /**
     * CombatView controller
     * @throws FileNotFoundException for background image if image is not found
     */
    public CombatView() throws FileNotFoundException {

        // Background Picture
        inputStream = new FileInputStream("background.png");
        background = new Image(inputStream);
        imageView = new ImageView();
        imageView.setImage(background);
        imageView.setFitWidth(1000);
        imageView.setFitHeight(1000);

        // XP, Mana, and Health Bars
        playerXPBar = new ProgressBar(1);
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
        hp = new HBox(playerHealthBar, HP);
        xp = new HBox(playerXPBar, XP);
        mana = new HBox(playerManaBar, Mana);

        Enemy = new Label();
        Enemy.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        Enemy.setWrapText(true);
        Enemy.setTextFill(Color.BLACK);
        Player = new Label();
        Player.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        Player.setWrapText(true);
        Player.setTextFill(Color.BLACK);
        enemy = new VBox();
        player = new VBox();
        enemy.getChildren().addAll(Enemy, enemyHealthBar, enemyManaBar);
        player.getChildren().addAll(Player, hp, xp, mana);

        // Dialogue Label
        Dialogue = new Label();
        Dialogue.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));

        // All Buttons
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);

        attack = new Button("Attack");
        attack.setFont(font);
        attack.setStyle("-fx-background-color: WHITE");

        run = new Button("Run");
        run.setFont(font);
        run.setStyle("-fx-background-color: WHITE");

        magic = new Button("Magic");
        magic.setDisable(false);
        magic.setFont(font);
        magic.setStyle("-fx-background-color: WHITE");

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
        top.setSpacing(550);

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
        buttonsMain.getChildren().addAll(attack, magic);
        buttonsMain.setSpacing(100);
        bottomMain.getChildren().addAll(buttonsMain, run);
        bottomMain.setAlignment(Pos.CENTER);
        bottomMain.setSpacing(650);
        dialogueMain = new VBox(Dialogue, bottomMain);
        dialogueMain.setAlignment(Pos.CENTER);
        main = new VBox();
        main.getChildren().addAll(top, dialogueMain);
        main.setSpacing(700);
        main.setPrefSize(1000,1000);


        this.getChildren().addAll(imageView, main);
        this.setPrefHeight(1000);
        this.setPrefWidth(1000);
    }

    /**
     * Notifies controller that a button has been pressed
     * @param controller The views controller to handle user interaction
     */
    protected void setController(Controller controller){
        attack.setOnAction(e -> controller.handleAttack());
        run.setOnAction((e -> controller.handleRun()));
        magic.setOnAction(e -> controller.handleMagic());
        retryYes.setOnAction(e -> {controller.handleCombatRest();this.reset();});
        retryNo.setOnAction(e -> {controller.handleNoReset(this.getScene());this.reset();});
        next.setOnAction(e -> {controller.handleWin(this.getScene());this.reset();});
        main.setOnMousePressed(controller::nextPhase);
    }

    private void reset() {
        this.getChildren().retainAll();
        this.getChildren().addAll(imageView, main);
        model.reset = false;
    }

    /**
     * The views model to receive updates from (and to subscribe too)
     * @param comModel the model to set as the views model
     */
    protected void setModel(CombatModel comModel){

        model = comModel;
        Player.setText(model.player.name + " /Level: " + model.player.characterStats.getCharacterLevel());
        Enemy.setText(model.enemy.name + " /Level: " + model.enemy.characterStats.getCharacterLevel());
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


    /**
     * Update view according to what has changed in the model
     */
    @Override
    public void modelChanged() {

        Dialogue.setText(model.getCurrentDialogue());

        // Get current health, xp, and mana for the progress bars
        // Normalized for progress bar needing number between 0-1
        playerManaBar.setProgress((float)model.player.characterStats.getMana()/model.player.characterStats.getMaxMana());
        playerHealthBar.setProgress((float)model.player.characterStats.getHealth()/model.player.characterStats.getMaxHealth());
        enemyHealthBar.setProgress((float)model.enemy.characterStats.getHealth()/model.enemy.characterStats.getMaxHealth());
        enemyManaBar.setProgress((float)model.enemy.characterStats.getMana()/model.enemy.characterStats.getMaxMana());

        // If player loses, option to replay
        if(model.player.characterStats.getHealth() <= 0){
            playerHealthBar.setProgress(0); // So it doesn't look like player has negative health
            this.retryButtons();
        }
        // If player wins, go back to traversal scene
        else if (model.enemy.characterStats.getHealth() <= 0){
            enemyHealthBar.setProgress(0);
            this.end();
        }
        // If run is successful, go back to traversal scene
        if (model.runAway){
            this.retryNo.fire();
        }

        // If mana bar is empty then player can no longer use magic button
        if (model.player.characterStats.getMana() <= 0 || model.player.characterStats.getMana() < model.costPerSpell){
            magic.setDisable(true);
        }

        //if it is not the players turn, then disable action buttons. Enable otherwise
        if(model.playerTurnPhase == model.phase){
            if(model.player.characterStats.getMana() >= model.costPerSpell){
                magic.setDisable(false);
            }
            attack.setDisable(false);
            run.setDisable(false);
        }else{
            magic.setDisable(true);
            attack.setDisable(true);
            run.setDisable(true);
        }
    }
}
