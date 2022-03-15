package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class CharacterGeneratorView extends Pane implements CharacterSubscribers{
    protected Button generateRandom, save, play;
    protected ChoiceBox<String> strengthList, dexterityList, constitutionList, wisdomList,
            intelligenceList, charismaList;
    protected ComboBox<String> raceList, hairColour, eyeColour, hairType, bodyType;
    protected TextField name;
    protected Label race, charName, strength, dexterity, constitution, wisdom, intelligence, charisma, hairC,
            eyeColourChoice, hairT, body, story;
    protected Image character;
    protected HBox bottom,above,mid, textField;
    protected VBox top,combo,vboxChoice,labels,choiceLabels, topMid;
    protected CharacterGenerator model;
    protected Features features;
    protected ObservableList<String> stats, races, hairColor, hairTypes, eyeColor, bodyTypes;


    /**
     * Constructor for character generator view
     * @throws FileNotFoundException for image of stickman in view
     */
    protected CharacterGeneratorView() throws FileNotFoundException {
        // Stats to add to some boxes
        stats = FXCollections.observableArrayList();
        stats.addAll("3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18");

        // Features to add to some boxes
        features = new Features();

        races = FXCollections.observableArrayList();
        races.addAll(features.racePresets);
        hairColor = FXCollections.observableArrayList();
        hairColor.addAll(features.colorPresets);
        hairTypes = FXCollections.observableArrayList();
        hairTypes.addAll(features.hairTypePresets);
        eyeColor = FXCollections.observableArrayList();
        eyeColor.addAll(features.eyeColorPresets);
        bodyTypes = FXCollections.observableArrayList();
        bodyTypes.addAll(features.bodyTypes);

        // Label to show story for generated character
        Font font = Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 15);
        story = new Label();
        story.setFont(font);
        story.setWrapText(true);


        // All Boxes for structure
        top = new VBox();
        above = new HBox();
        mid = new HBox();
        textField = new HBox();
        bottom = new HBox();
        combo = new VBox();
        vboxChoice = new VBox();
        labels = new VBox();
        choiceLabels = new VBox();
        topMid = new VBox();

        Font labelFont = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);


        // Name input/random name
        name = new TextField();
        name.setFont(labelFont);
        charName = new Label("Name: ");
        charName.setFont(labelFont);
        textField.getChildren().addAll(charName, name);
        textField.setAlignment(Pos.TOP_CENTER);

        // Generate random button. Located on top of everything
        generateRandom = new Button("Generate Random");
        generateRandom.setFont(labelFont);
        generateRandom.setStyle("-fx-border-color: BLACK; -fx-border-radius: 4px");
        top.getChildren().addAll(generateRandom, textField);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(25);
        above.getChildren().addAll(top);
        above.setAlignment(Pos.CENTER);
        above.setPadding(new Insets(50));

        // Save and play buttons. Located in bottom right corner
        save = new Button("Save");
        save.setFont(labelFont);
        play = new Button("Play");
        play.setFont(labelFont);
        bottom.getChildren().addAll(save,play);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.setSpacing(10);

        // All ComboBox Labels (far left)
        strength = new Label("Strength: ");
        strength.setFont(labelFont);
        dexterity = new Label("Dexterity: ");
        dexterity.setFont(labelFont);
        constitution = new Label("Constitution: ");
        constitution.setFont(labelFont);
        wisdom = new Label("Wisdom: ");
        wisdom.setFont(labelFont);
        intelligence = new Label("Intelligence: ");
        intelligence.setFont(labelFont);
        charisma = new Label("Charisma: ");
        charisma.setFont(labelFont);
        labels.getChildren().addAll(strength, dexterity, constitution, wisdom, intelligence, charisma);
        labels.setSpacing(21);

        // All choiceboxes (far left)
        strengthList = new ChoiceBox<>(stats);
        strengthList.setStyle("-fx-font: 12 Verdana");
        dexterityList = new ChoiceBox<>(stats);
        dexterityList.setStyle("-fx-font: 12 Verdana");
        constitutionList = new ChoiceBox<>(stats);
        constitutionList.setStyle("-fx-font: 12 Verdana");
        wisdomList = new ChoiceBox<>(stats);
        wisdomList.setStyle("-fx-font: 12 Verdana");
        intelligenceList = new ChoiceBox<>(stats);
        intelligenceList.setStyle("-fx-font: 12 Verdana");
        charismaList = new ChoiceBox<>(stats);
        charismaList.setStyle("-fx-font: 12 Verdana");
        combo.getChildren().addAll(strengthList, dexterityList, constitutionList, wisdomList, intelligenceList,
                charismaList);
        combo.setSpacing(12);


        // Random stickman just for fun (in middle)
        FileInputStream inputStream = new FileInputStream("stickfigure.jpg");
        character = new Image(inputStream);
        ImageView imageView = new ImageView();
        imageView.setImage(character);
        imageView.setFitHeight(350);
        imageView.setFitWidth(200);

        // Labels for combo boxes
        race = new Label("Race: ");
        race.setFont(labelFont);
        hairC = new Label("Hair Colour: ");
        hairC.setFont(labelFont);
        eyeColourChoice = new Label("Eye Colour: ");
        eyeColourChoice.setFont(labelFont);
        hairT = new Label("Hair Type: ");
        hairT.setFont(labelFont);
        body = new Label("Body Type: ");
        body.setFont(labelFont);
        choiceLabels.getChildren().addAll(race, hairC, eyeColourChoice, hairT, body);
        choiceLabels.setSpacing(22);

        // Combo boxes (far right) ALL SET TO EDITABLE so user can add unavailable feature choice

        raceList = new ComboBox<>(races);
        raceList.setEditable(true);
        raceList.setStyle("-fx-font: 12 Verdana");
        hairColour = new ComboBox<>(hairColor);
        hairColour.setStyle("-fx-font: 12 Verdana");
        hairColour.setEditable(true);
        eyeColour = new ComboBox<>(eyeColor);
        eyeColour.setStyle("-fx-font: 12 Verdana");
        eyeColour.setEditable(true);
        hairType = new ComboBox<>(hairTypes);
        hairType.setStyle("-fx-font: 12 Verdana");
        hairType.setEditable(true);
        bodyType = new ComboBox<>(bodyTypes);
        bodyType.setStyle("-fx-font: 12 Verdana");
        bodyType.setEditable(true);
        vboxChoice.getChildren().addAll(raceList, hairColour, eyeColour, hairType, bodyType);
        vboxChoice.setSpacing(12);

        // Mid section grouping (label -> combobox -> stickman -> label -> choicebox)
        mid.getChildren().addAll(labels,combo,imageView,choiceLabels, vboxChoice);
        mid.setSpacing(25);
        mid.setAlignment(Pos.CENTER);

        topMid.getChildren().addAll(top,mid);
        topMid.setSpacing(100);

        // All put together
        VBox main = new VBox();
        main.getChildren().addAll(topMid, story, bottom);
        main.setSpacing(75);
        main.setPrefSize(800,800);
        main.setPadding(new Insets(25));
        this.getChildren().addAll(main);

        BackgroundFill backgroundFill = new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        this.setBackground(background);
    }

    /**
     * Lets controller know a button was pushed, and which button
     * @param controller The views controller to handle all user interaction
     */
    public void setController(Controller controller){
        generateRandom.setOnAction(e -> controller.handleGenerateRandom());
        save.setOnAction(e -> {
            try {
                controller.handleSave(this.saveChoices());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        play.setOnAction(e -> controller.genToTraversal(this.getScene(), this.saveChoices()));
    }

    /**
     * Sets the model for the view to subscribe too
     * @param mod The model to set to the view
     */
    public void setModel(CharacterGenerator mod){
        model = mod;
    }

    /**
     * Gets accurate choices from view (User may have picked or just generated random) and returns
     * them as an array to send to controller to handle when user presses the save button
     * @return The array of choices user has made in the view
     */
    private ArrayList<String> saveChoices(){
        ArrayList<String> custom = new ArrayList<>();

        // If user leaves name field blank or deletes what was put there, generate random name
        if (name.getText() == null || name.getText().equals("")){
            name.setText(NamePool.fetchName());
        }
        custom.add(name.getText());
        custom.add(charismaList.getValue());
        custom.add(constitutionList.getValue());
        custom.add(wisdomList.getValue());
        custom.add(dexterityList.getValue());
        custom.add(intelligenceList.getValue());
        custom.add(strengthList.getValue());
        custom.add(bodyType.getValue());
        custom.add(eyeColour.getValue());
        custom.add(hairColour.getValue());
        custom.add(hairType.getValue());
        custom.add(raceList.getValue());

        return custom;
    }

    /**
     * Updates view when notified from model
     * Used when user presses generate random
     */
    @Override
    public void modelChanged() {
        strengthList.setValue(Integer.toString(model.character.characterStats.getStr()));
        dexterityList.setValue(Integer.toString(model.character.characterStats.getDex()));
        constitutionList.setValue(Integer.toString(model.character.characterStats.getCon()));
        wisdomList.setValue(Integer.toString(model.character.characterStats.getWis()));
        intelligenceList.setValue(Integer.toString(model.character.characterStats.getInt()));
        charismaList.setValue(Integer.toString(model.character.characterStats.getCha()));
        raceList.setValue(model.character.characterFeatures.race);
        hairColour.setValue(model.character.characterFeatures.hairColor);
        hairType.setValue(model.character.characterFeatures.hairType);
        eyeColour.setValue(model.character.characterFeatures.eyeColor);
        bodyType.setValue(model.character.characterFeatures.bodyType);
        name.setText(model.character.name);
        story.setText(model.character.characterStory);
    }
}
