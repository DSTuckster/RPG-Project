package sample;

import java.util.ArrayList;

public class CharacterGenerator{
    Character character;
    ArrayList<CharacterSubscribers> subs;

    public CharacterGenerator() {
        subs = new ArrayList<>();
    }


    public void generateRandom() {
        character = new Character();
        notifySubscribers();
    }

    public void generateCustom(ArrayList<String> custom){
        // New customer character
        character = new Character();

        // Set players chosen character stats
        character.characterStats.setCha(Integer.parseInt(custom.get(0)));
        character.characterStats.setCharacterLevel(1);
        character.characterStats.setHealth(12);
        character.characterStats.setCon(Integer.parseInt(custom.get(1)));
        character.characterStats.setWis(Integer.parseInt(custom.get(2)));
        character.characterStats.setDex(Integer.parseInt(custom.get(3)));
        character.characterStats.setInt(Integer.parseInt(custom.get(4)));
        character.characterStats.setStr(Integer.parseInt(custom.get(5)));

        // Set players chosen character features
        character.characterFeatures.setBodyType(custom.get(6));
        character.characterFeatures.setEyeColor(custom.get(7));
        character.characterFeatures.setHairColor(custom.get(8));
        character.characterFeatures.setHairType(custom.get(9));
        character.characterFeatures.setRace(custom.get(10));
    }

    public void addSubscriber (CharacterSubscribers sub) {subs.add(sub);}

    public void notifySubscribers(){
        for (CharacterSubscribers sub : subs){
            sub.modelChanged();
        }
    }
}
