package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class WelcomeView extends Pane {
    Label welcome, optionNew, optionSaved;
    Button newChar, play, edit;
    ChoiceBox<ArrayList<String>> saved;

    WelcomeView(){

        HBox top = new HBox();
        VBox middle= new VBox();
        HBox playEdit = new HBox();
        VBox bottom = new VBox();
        VBox main = new VBox();

        welcome = new Label("Welcome!!");
        welcome.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 35));
        top.getChildren().addAll(welcome);
        top.setAlignment(Pos.CENTER);

        optionNew = new Label("Create a new character?");
        optionNew.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        newChar = new Button("Create New");
        newChar.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        newChar.setStyle("-fx-background-color: WHITE");
        middle.getChildren().addAll(optionNew, newChar);
        middle.setAlignment(Pos.CENTER);

        optionSaved = new Label("Use a saved character?");
        optionSaved.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        saved = new ChoiceBox<>();
        saved.setStyle("-fx-background-color: WHITE");
        saved.setPrefSize(15,15);

        play = new Button("Play");
        play.setFont((Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12)));
        play.setStyle("-fx-background-color: WHITE");
        edit = new Button("Edit Character");
        edit.setFont((Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12)));
        edit.setStyle("-fx-background-color: WHITE");
        playEdit.getChildren().addAll(play,edit);
        playEdit.setSpacing(10);
        playEdit.setAlignment(Pos.CENTER);

        bottom.getChildren().addAll(optionSaved,saved, playEdit);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.setSpacing(15);

        main.getChildren().addAll(top,middle,bottom);
        main.setSpacing(100);
        main.setPrefSize(500,500);
        this.getChildren().addAll(main);
    }

}
