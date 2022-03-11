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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CombatView extends StackPane implements CombatSubscriber{
    protected Image background;
    protected Button attack, run, magic, retryYes, retryNo, attackOne, attackTwo, attackThree, attackFour;
    protected ProgressBar playerXPBar, playerHealthBar, playerManaBar, enemyHealthBar;
    protected Label Enemy, Player, HP, XP, Mana;
    protected CombatModel model;
    protected HBox bottom;
    protected VBox main;

    /**
     * CombatView controller
     * @throws FileNotFoundException for background image if image is not found
     */
    public CombatView() throws FileNotFoundException {

        // Background Picture
        FileInputStream inputStream = new FileInputStream("background.png");
        background = new Image(inputStream);
        ImageView imageView = new ImageView();
        imageView.setImage(background);
        imageView.setFitWidth(1000);
        imageView.setFitHeight(1000);

        // XP, Mana, and Health Bars
        playerXPBar = new ProgressBar(1);
        playerXPBar.setStyle("-fx-accent: GREEN");
        playerHealthBar = new ProgressBar(1);
        playerHealthBar.setStyle("-fx-accent: RED");
        playerManaBar = new ProgressBar(1);
        enemyHealthBar = new ProgressBar(1);
        enemyHealthBar.setStyle("-fx-accent: RED");

        // Bars Labels
        HP = new Label("HP");
        HP.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        XP = new Label("XP");
        XP.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        Mana = new Label("Mana");
        Mana.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        HBox hp = new HBox(playerHealthBar, HP);
        HBox xp = new HBox(playerXPBar, XP);
        HBox mana = new HBox(playerManaBar, Mana);

        Enemy = new Label("Enemy");
        Enemy.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        Player = new Label("Player");
        Player.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        VBox enemy = new VBox();
        VBox player = new VBox();
        enemy.getChildren().addAll(Enemy, enemyHealthBar);
        player.getChildren().addAll(Player, hp, xp, mana);

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

        // TODO: Implement buttons when battle is over
        //      and for a player to have more than one
        //      attack to choose from
        retryYes = new Button("Yes");
        retryNo = new Button("No");
        attackOne = new Button("Attack One");
        attackTwo = new Button("Attack Two");
        attackThree = new Button("Attack Three");
        attackFour = new Button("Attack Four");

        VBox buttons = new VBox();
        bottom = new HBox();
        buttons.getChildren().addAll(attack, magic);
        buttons.setSpacing(100);
        bottom.getChildren().addAll(buttons, run);
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(650);


        // Coordinate all boxes together to fit screen properly
        HBox top = new HBox();
        top.getChildren().addAll(enemy,player);
        top.setSpacing(550);

        main = new VBox();
        main.getChildren().addAll(top, bottom);
        main.setSpacing(550);
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
    }

    /**
     * The views model to receive updates from (and to subscribe too)
     * @param comModel the model to set as the views model
     */
    protected void setModel(CombatModel comModel){
        model = comModel;
    }

    private void retryButtons(){
        bottom.getChildren().addAll(retryYes,retryNo);
        main.getChildren().remove(1);
        main.getChildren().add(1,bottom);
    }

    /**
     * Update view according to what has changed in the model
     */
    @Override
    public void modelChanged() {

        // Get current health, xp, and mana for the progress bars
        // Divided by 10 to get a float between 0-1 for progress bar
        playerManaBar.setProgress((float)model.player.characterStats.getMana()/100);
        playerHealthBar.setProgress((float)model.player.characterStats.getHealth()/100);
        enemyHealthBar.setProgress((float)model.enemy.characterStats.getHealth()/100);

        // TODO: Handle closing out the combat view when game is over.
        //      Add game dialogue
        //      If statements only here to get feedback when user has lost or won (testing purposes)
        if(model.player.characterStats.getHealth() <= 0){
            playerHealthBar.setProgress(0);
            this.retryButtons();
        }
        else if (model.enemy.characterStats.getHealth() <= 0){
            System.out.println("YOU WIN!");
            enemyHealthBar.setProgress(0);
        }
        if (model.runAway){
            System.out.println("Ran Away");
        }

        // If mana bar is empty then player can no longer use magic button
        if (model.player.characterStats.getWis() <= 0){
            magic.setDisable(true);
        }
    }
}
