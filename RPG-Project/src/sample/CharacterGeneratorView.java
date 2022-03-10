package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CharacterGeneratorView extends Pane implements CharacterSubscribers{
    protected Button generateRandom, save, play;
    protected ComboBox<String> raceList, strengthList, dexterityList, constitutionList, wisdomList,
            intelligenceList, charismaList;
    protected ChoiceBox<String> hairColour, eyeColour, hairType, bodyType;
    protected TextField name;
    protected Label race, charName, strength, dexterity, constitution, wisdom, intelligence, charisma, hairC,
            eyeColourChoice, hairT, body;
    protected Image character;
    protected HBox bottom,above,mid, textField;
    protected VBox top,combo,vboxChoice,labels,choiceLabels;
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
        bodyTypes.addAll(features.bodyTypePresets);

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

        // Name input/random name
        name = new TextField();
        charName = new Label("Name: ");
        textField.getChildren().addAll(charName, name);
        textField.setAlignment(Pos.TOP_CENTER);

        // Generate random button. Located on top of everything
        generateRandom = new Button("Generate Random");
        top.getChildren().addAll(generateRandom, textField);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(15);
        above.getChildren().addAll(top);
        above.setAlignment(Pos.CENTER);

        // Save and play buttons. Located in bottom right corner
        save = new Button("Save");
        play = new Button("Play");
        bottom.getChildren().addAll(save,play);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.setSpacing(10);

        // All ComboBox Labels (far left)
        race = new Label("Race: ");
        strength = new Label("Strength: ");
        dexterity = new Label("Dexterity: ");
        constitution = new Label("Constitution: ");
        wisdom = new Label("Wisdom: ");
        intelligence = new Label("Intelligence: ");
        charisma = new Label("Charisma: ");
        labels.getChildren().addAll(race, strength, dexterity, constitution, wisdom, intelligence, charisma);
        labels.setSpacing(20);

        // All comboboxes (far left)
        raceList = new ComboBox<>(races);
        strengthList = new ComboBox<>(stats);
        dexterityList = new ComboBox<>(stats);
        constitutionList = new ComboBox<>(stats);
        wisdomList = new ComboBox<>(stats);
        intelligenceList = new ComboBox<>(stats);
        charismaList = new ComboBox<>(stats);
        combo.getChildren().addAll(raceList, strengthList, dexterityList, constitutionList, wisdomList, intelligenceList,
                charismaList);
        combo.setSpacing(12);


        // Random stickman just for fun (in middle)
        FileInputStream inputStream = new FileInputStream("stickfigure.jpg");
        character = new Image(inputStream);
        ImageView imageView = new ImageView();
        imageView.setImage(character);
        imageView.setFitHeight(600);
        imageView.setFitWidth(300);


        hairC = new Label("Hair Colour: ");
        eyeColourChoice = new Label("Eye Colour: ");
        hairT = new Label("Hair Type: ");
        body = new Label("Body Type: ");
        choiceLabels.getChildren().addAll(hairC, eyeColourChoice, hairT, body);
        choiceLabels.setSpacing(20);

        // Choice boxes (far right)
        hairColour = new ChoiceBox<>(hairColor);
        eyeColour = new ChoiceBox<>(eyeColor);
        hairType = new ChoiceBox<>(hairTypes);
        bodyType = new ChoiceBox<>(bodyTypes);
        vboxChoice.getChildren().addAll(hairColour, eyeColour, hairType, bodyType);
        vboxChoice.setSpacing(12);

        // Mid section grouping (label -> combobox -> stickman -> label -> choicebox)
        mid.getChildren().addAll(labels,combo,imageView, choiceLabels, vboxChoice);
        mid.setSpacing(40);

        // All put together
        VBox main = new VBox();
        main.getChildren().addAll(above, mid, bottom);
        main.setSpacing(40);
        this.getChildren().addAll(main);
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
            name.setText(model.character.generateName());
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
    }
}
