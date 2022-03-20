package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WelcomeView extends StackPane {
    protected Label welcome, optionNew, optionSaved;
    protected Button newChar, play, edit;
    protected ChoiceBox<String> saved;
    protected ObservableList<String> choices;
    protected Character character;
    protected Music music;
    protected ImageView imageView;
    protected FileInputStream inputStream;
    protected Image background;

    /**
     * Constructor for welcome view
     */
    WelcomeView() throws FileNotFoundException {
        //Open the background music
        music = new Music("welcome");
        music.playMusic(music.file);

        // Background
        inputStream = new FileInputStream("WelcomeBackground.png");
        background = new Image(inputStream);
        imageView = new ImageView();
        imageView.setImage(background);
        imageView.setFitWidth(1296);
        imageView.setFitHeight(720);

        // Boxes for view structure
        HBox top = new HBox();
        VBox middle= new VBox();
        HBox playEdit = new HBox();
        VBox bottom = new VBox();
        VBox main = new VBox();


        // Welcome label
        welcome = new Label("Blue Boy and the Dino's");
        welcome.setTextFill(Color.BLUE);
        welcome.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 35));
        welcome.setStyle("-fx-padding: 100 0 0 425");
        top.getChildren().addAll(welcome);


        // New character selection
        optionNew = new Label("Create a new character?");
        optionNew.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        newChar = new Button("Create New");
        newChar.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        newChar.setStyle("-fx-background-color: WHITE");
        middle.getChildren().addAll(optionNew, newChar);
        middle.setAlignment(Pos.CENTER);
        middle.setSpacing(15);



        // Use saved selection (add saved character from save.txt)
        choices = FXCollections.observableArrayList();
        character = new Character();
        SaveSystem.LoadFile("save.txt", character);
        choices.add(character.name);
        optionSaved = new Label("Use a saved character?");
        optionSaved.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        saved = new ChoiceBox<>(choices);
        saved.setStyle("-fx-background-color: WHITE");

        // Play with saved character selection
        play = new Button("Play");
        play.setFont((Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12)));
        play.setStyle("-fx-background-color: WHITE");
        edit = new Button("Edit Character");
        edit.setFont((Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12)));
        edit.setStyle("-fx-background-color: WHITE");
        playEdit.getChildren().addAll(play,edit);
        playEdit.setSpacing(10);
        playEdit.setAlignment(Pos.CENTER);


        // Gather everything together to fill view
        bottom.getChildren().addAll(optionSaved,saved, playEdit);
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(15);


        main.getChildren().addAll(top,middle,bottom);
        main.setSpacing(150);
        this.getChildren().addAll(imageView, main);
    }


    /**
     * Sends user interaction to controller to handle
     * @param controller the controller to handle user interaction
     */
    public void setController(Controller controller){
        newChar.setOnAction(e -> controller.welcomeToGen(this.getScene(),music));
        play.setOnAction(e -> controller.handlePlayWithSaved(this.getScene(), character,music));
        edit.setOnAction(e -> controller.handleEdit(this.getScene(), character,music));
    }
}
