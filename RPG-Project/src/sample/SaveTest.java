package sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class SaveTest {
    public static void main(String[] args) throws FileNotFoundException {
        //testing for save/load function
        //Step one create a character, then save it to the file
        //Step two change every value of the character and exam it.
        //Step three load the file data to make the character back to the beginning

        Character character = new Character();
        transData transdata = new transData();
        System.out.println("This is the Save/load tests: ");
        System.out.println("Here is the initial character: ");
        System.out.println(character.characterStats);
        System.out.println(character.characterFeatures);
        System.out.println("Test start: ");
        System.out.println("Save the character to file 'save.txt': ");
        String path = "save.txt";
        SaveSystem.SaveToFile(path, character);

        System.out.println("Set race to: 'Human',hair color to: 'Brown',Hair type to: 'Long',eyeColors to: 'Brown',body type to: 'Tiny'");
        transdata.setRace(character, "Human");
        transdata.setHairColor(character, "Brown");
        transdata.setHairType(character, "Long");
        transdata.setEyeColor(character, "Brown");
        transdata.setBodyType(character, "Tiny");
        System.out.println("This is the character features now: " + character.characterFeatures);

        System.out.println("Set level to: '1000', and all other stats to :'9'");
        transdata.setCharacterLevel(character, 1000);
        transdata.setStr(character, 9);
        transdata.setDex(character, 9);
        transdata.setCon(character, 9);
        transdata.setWis(character, 9);
        transdata.setInt(character, 9);
        transdata.setCha(character, 9);
        transdata.setHealth(character, 9);
        System.out.println("This is the character stats now: " + character.characterStats);

        System.out.println("Now load the initial character from the file");
        SaveSystem.LoadFile(path,character);
        System.out.println("This is the character after load: ");
        System.out.println(character.characterStats);
        System.out.println(character.characterFeatures);

        //unit tests: test all types of the features can be set as any element of the feature type lists
        System.out.println("This is test 2:  Features tests");
        Character character1 = new Character();
        transData transdata1 = new transData();
        String path1 = "save.txt";
        SaveSystem.SaveToFile(path1, character);
        ArrayList<String> race = character.characterFeatures.races;
        ArrayList<String> hairColor = character.characterFeatures.hairColors;
        ArrayList<String> hairType = character.characterFeatures.hairTypes;
        ArrayList<String> eyeColor = character.characterFeatures.eyeColors;
        ArrayList<String> bodyType = character.characterFeatures.bodyTypes;
        int ralength = race.size();
        int i = 0;
        while (i < ralength) {
            transdata.setRace(character, race.get(i));
            if (character.characterFeatures.race != race.get(i)) {
                System.out.println("Change race to " + race.get(i) + " failed");
            }
            if(i==ralength-1){
                System.out.println("race test success.");
            }
            i++;
        }

        i = 0;
        int hclength = hairColor.size();
        while (i < hclength) {
            transdata.setHairColor(character, hairColor.get(i));
            if (character.characterFeatures.hairColor != hairColor.get(i)) {
                System.out.println("Change hair color to " + hairColor.get(i) + " failed");
            }
            if(i==hclength-1){
                System.out.println("hair color test success.");
            }
            i++;
        }
        i = 0;
        int htlength = hairType.size();
        while (i < htlength) {
            transdata.setHairType(character, hairType.get(i));
            if (character.characterFeatures.hairType != hairType.get(i)) {
                System.out.println("Change hair type to " + hairType.get(i) + " failed");
            }
            if(i==htlength-1){
                System.out.println("hair type test success.");
            }
            i++;
        }
        i = 0;
        int eclength = eyeColor.size();
        while (i < eclength) {
            transdata.setEyeColor(character, eyeColor.get(i));
            if (character.characterFeatures.eyeColor != eyeColor.get(i)) {
                System.out.println("Change eye color to " + eyeColor.get(i) + " failed");
            }
            if(i==eclength-1){
                System.out.println("eye color test success.");
            }
            i++;
        }
        i = 0;
        int btlength = bodyType.size();
        while (i < btlength) {
            transdata.setBodyType(character, bodyType.get(i));
            if (character.characterFeatures.bodyType != bodyType.get(i)) {
                System.out.println("Change body type to " + bodyType.get(i) + " failed");
            }
            if(i==btlength-1){
                System.out.println("Body type test success.");
            }
            i++;
        }
        System.out.println("end of features test.");

        //unit tests: test all types of the stats can be set as any integer
        Random rand = new Random();
        i=0;
        while(i<100){
            int integerRandom = rand.nextInt(100);
            transdata.setCharacterLevel(character,integerRandom);
            transdata.setStr(character,integerRandom);
            transdata.setDex(character,integerRandom);
            transdata.setWis(character,integerRandom);
            transdata.setCha(character,integerRandom);
            transdata.setInt(character,integerRandom);
            transdata.setHealth(character,integerRandom);
            transdata.setCon(character,integerRandom);
            if(character.characterStats.CharacterLevel!=integerRandom &&
                    character.characterStats.Intelligence!=integerRandom &&
                    character.characterStats.Wisdom!=integerRandom &&
                    character.characterStats.Strength!=integerRandom &&
                    character.characterStats.Dexterity!=integerRandom &&
                    character.characterStats.Charisma!=integerRandom &&
                    character.characterStats.Health!=integerRandom &&
                    character.characterStats.Constitution!=integerRandom){
                System.out.println("change to number "+integerRandom+"failed");
            }
            if (i==99){
                System.out.println("Stats test success!");
            }
            i++;

        }
    }

}
