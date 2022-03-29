package sample;

import java.util.ArrayList;

public class CharacterGenerator{
    Character character;
    ArrayList<CharacterSubscribers> subs;

    /**
     * Creates character to update view
     */
    public CharacterGenerator() {
        subs = new ArrayList<>();
    }


    /**
     * Generates a random character
     */
    public void generateRandom() {
        character = new Character();
        notifySubscribers();
    }

    /**
     * Sets users choices to the new character
     * @param custom A list of chosen attributes to set for the character
     */
    public void generateCustom(ArrayList<String> custom){
        // New customer character
        character = new Character();

        // Set players name
        character.name = custom.get(0);

        // Set players chosen character stats
        character.characterStats.setCha(Integer.parseInt(custom.get(1)));
        character.characterStats.setCon(Integer.parseInt(custom.get(2)));
        character.characterStats.setWis(Integer.parseInt(custom.get(3)));
        character.characterStats.setDex(Integer.parseInt(custom.get(4)));
        character.characterStats.setInt(Integer.parseInt(custom.get(5)));
        character.characterStats.setStr(Integer.parseInt(custom.get(6)));

        // Set players chosen character features
        character.characterFeatures.setBodyType(custom.get(7));
        character.characterFeatures.setEyeColor(custom.get(8));
        character.characterFeatures.setHairColor(custom.get(9));
        character.characterFeatures.setHairType(custom.get(10));
        character.characterFeatures.setRace(custom.get(11));

        // Update Character Story
        character.generateStory();

        // Notify Welcome Page that new saved character
        notifySubscribers();
    }

    /**
     * Set the current character to the given
     * @param c the given character
     */
    protected void setCharacter(Character c){
        character = c;
    }

    /**
     * Sets the character to edit
     * @param c the character to edit
     */
    protected void editChar(Character c){
        character = c;
        notifySubscribers();
    }

    /**
     * Adds new subscriber
     * @param sub the subscriber (view)
     */
    public void addSubscriber (CharacterSubscribers sub) {subs.add(sub);}

    /**
     * Call to notify subscribers (view)
     */
    public void notifySubscribers(){
        for (CharacterSubscribers sub : subs){
            sub.modelChanged();
        }
    }
}
