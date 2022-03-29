package sample;

import java.util.Objects;

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
        return NamePool.fetchName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCharacterStory() {
        return characterStory;
    }

    public void setCharacterStory(String characterStory) {
        this.characterStory = characterStory;
    }

    /**
     * generateStory()
     *  The 'Story' of a character is just a description of their appearance.
     *  It is represented as a String value, where each feature is noted.
     *  The only things that changes in the Story String are the Character Features (generated randomly)
     */
    protected String generateStory() {
        characterStory = "You are a(n) "+ this.characterFeatures.race+". ";
        characterStory = characterStory.concat("Your hair is "+ this.characterFeatures.hairType+" and noticeably "+ this.characterFeatures.hairColor+". ");
        characterStory = characterStory.concat("Your eyes shine a bold "+ this.characterFeatures.eyeColor+" color. ");
        characterStory =  characterStory.concat("Your build is "+ this.characterFeatures.bodyType+"...and impressive. ");
        characterStory = characterStory.concat("You are brimming with vitality, and ripe for adventure!");

        return characterStory;
    }

    public static void main(String[] args) {

        Character character = new Character();

        boolean Expected;
        boolean Result;

        /**
         * THIS IS THE TEST DRIVER FOR THE USER STORY
         *
         *
         *          "Generate a freshly created Character with one button press"
         *
         *
         * * Tests will report errors to std output if detected *
         */

        System.out.println("\nCharacter Test Suite Begin\n\n\n");

        // Test 1 - TC-01
        //  Test that 'Stats' are generated appropriately
        // Runs 1000 tests to see that Character Stats are within reasonable bounds (3 - 18) for each stat
        TestStatsForAppropriateValues(character);

        // Test 2 - TC-02
        //  Test that 'Features' are generated appropriately
        // Runs 1000 tests to see that Features are generated uniquely, with no duplicates
        TestFeaturesForAppropriateValues(character);

        // Test 3 - TC-03
        //  Test that 'Name' is generated appropriately
        // Runs 1000 tests to see that 'Name' is generated properly each time
        TestNameIsGenerated(character);

        // Test 4 - TC-04
        //  Test that character 'Story' is acceptable
        TestStoryIsGenerated(character);


        System.out.println("\n\n\nCharacter Test Suite End");
    }

    private static void TestStatsForAppropriateValues(Character c) {
        c.characterStats.RunStatsTestSuite();
    }

    private static void TestFeaturesForAppropriateValues(Character c) {
        c.characterFeatures.RunFeaturesTestSuite();
    }

    private static void TestNameIsGenerated(Character c) {
        c.namePool.RunNamePoolTestSuite();
    }

    private static void TestStoryIsGenerated(Character c) {
        System.out.println("This is the StoryTestSuite");
        String TestStory = c.generateStory();
        if (TestStory.length() > 0) {
            System.out.println("Story generation successful");
            System.out.println("Story:\n  '"+TestStory+"'");
        } else {
            System.out.println("Error in TestStoryIsGenerated(): \n TestStory == "+TestStory);
        }
    }


}
