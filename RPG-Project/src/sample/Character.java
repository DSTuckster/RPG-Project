package sample;

public class Character {
    Stats characterStats;
    Features characterFeatures;
    Story characterStory;
    String name;

    public Character() {
        // When a character class is created, each attribute is generated from a unique set of algorithms
        characterStats = generateStats();
        characterFeatures = generateFeatures();
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

    private String generateName() {
        return null;
    }

    private Story generateStory() {
        return null;
    }

}
