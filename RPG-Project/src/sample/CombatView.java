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
    Image background;
    Button attack, run, magic, retryYes, retryNo, attackOne, attackTwo, attackThree, attackFour;
    ProgressBar playerXPBar, playerHealthBar, enemyXPBar, enemyHealthBar;
    Label Enemy, Player;

    public CombatView() throws FileNotFoundException {

        // Background Picture
        FileInputStream inputStream = new FileInputStream("background.png");
        background = new Image(inputStream);
        ImageView imageView = new ImageView();
        imageView.setImage(background);
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);

        // XP and Health Bars
        playerXPBar = new ProgressBar(1);
        playerHealthBar = new ProgressBar(1);
        playerHealthBar.setStyle("-fx-accent: RED");
        //enemyXPBar = new ProgressBar();
        enemyHealthBar = new ProgressBar(1);
        enemyHealthBar.setStyle("-fx-accent: RED");

        Enemy = new Label("Enemy");
        Enemy.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        Player = new Label("Player");
        Player.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        VBox enemy = new VBox();
        VBox player = new VBox();
        enemy.getChildren().addAll(Enemy, enemyHealthBar);
        player.getChildren().addAll(Player, playerHealthBar, playerXPBar);

        // All Buttons
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);

        attack = new Button("Attack");
        attack.setFont(font);
        attack.setStyle("-fx-background-color: WHITE");

        run = new Button("Run");
        run.setFont(font);
        run.setStyle("-fx-background-color: WHITE");

        magic = new Button("Magic");
        magic.setFont(font);
        magic.setStyle("-fx-background-color: WHITE");

        retryYes = new Button("Yes");
        retryNo = new Button("No");
        attackOne = new Button("Attack One");
        attackTwo = new Button("Attack Two");
        attackThree = new Button("Attack Three");
        attackFour = new Button("Attack Four");

        VBox buttons = new VBox();
        HBox bottom = new HBox();
        buttons.getChildren().addAll(attack, magic);
        buttons.setSpacing(25);
        bottom.getChildren().addAll(buttons, run);
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(350);


        HBox top = new HBox();
        top.getChildren().addAll(enemy,player);
        top.setSpacing(350);

        VBox main = new VBox();
        main.getChildren().addAll(top, bottom);
        main.setSpacing(350);
        main.setPrefSize(500,500);

        this.getChildren().addAll(imageView, main);
        this.setPrefHeight(500);
        this.setPrefWidth(500);
    }

    public void setModel(CombatModel model){}

    public void setController(Controller controller){}

    public void update(){}

    @Override
    public void modelChanged() {

    }
}
