package sample;

import javafx.event.ActionEvent;

public class CharacterGenerator {
    Character character;

    public CharacterGenerator() {

        character = new Character();

    }


    public static void generateRandom(ActionEvent actionEvent) {
        System.out.println("GENERATED");
    }
}
