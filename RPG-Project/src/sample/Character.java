package sample;

public class Character {
    Stats characterStats;
    Features characterFeatures;
    String characterStory;
    String name;
    NamePool namePool;

    public Character() {
        // When a character class is created, each attribute is generated from a unique set of algorithms
        characterStats = generateStats();
        characterFeatures = generateFeatures();
        namePool = new NamePool();
        name = generateName();
        characterStory = generateStory();

    }

    private Stats generateStats() {
        // Stat calculation is done within the Stats class
        return new Stats();
    }

    private Features generateFeatures() {
        return new Features();
    }

    protected String generateName() {
        return namePool.fetchName();
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * generateStory()
     *  The 'Story' of a character is just a description of their appearance.
     *  It is represented as a String value, where each feature is noted.
     *  The only things that changes in the Story String are the Character Features (generated randomly)
     */
    private String generateStory() {
        characterStory = "You are a(n) "+characterFeatures.race+". ";
        characterStory = characterStory.concat("Your hair is "+characterFeatures.hairType+" and noticeably "+characterFeatures.hairColor+". ");
        characterStory = characterStory.concat("Your eyes shine a bold "+characterFeatures.eyeColor+" color. ");
        characterStory =  characterStory.concat("Your build is "+characterFeatures.bodyType+"...and impressive. ");
        characterStory = characterStory.concat("You are brimming with vitality, and ripe for adventure!");

        return characterStory;
    }

    public static void main(String[] args) {

        Character c = new Character();

        System.out.println(c.characterStory);
    }

}
