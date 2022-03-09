package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Features {
    protected String race;
    protected String hairColor;
    protected String hairType;
    protected String eyeColor;
    protected String bodyType;
    //protected String height;

    protected Random r;

    // Each String Array represents possible preset values for character features
    // The ArrayList will hold those values once initialized
    protected ArrayList<String> races;
    protected String[] racePresets = {"Orc", "Human", "Dwarf", "High Elf", "Dunmer", "Gnome", "Draconian", "Porci", "Spoodle"};

    protected ArrayList<String> hairColors;
    protected String[] colorPresets = {"Brown", "Auburn", "Red", "Orange", "Black", "Blonde", "Platinum"};

    protected ArrayList<String> hairTypes;
    protected String[] hairTypePresets = {"Long", "Medium", "Short", "Braided", "Dreadlocks", "Buzzed", "Bald", "Horned"};

    protected ArrayList<String> eyeColors;
    protected String[] eyeColorPresets = {"Brown", "Blue", "Green", "Gold", "Hazel", "Gray", "Red", "Indigo", "Black", "Faded", "White"};

    protected ArrayList<String> bodyTypes;
    protected String[] bodyTypePresets = {"Tiny", "Petite", "Stout", "Thin", "Sturdy", "Average", "Tall", "Round", "Girthy", "Gargantuan"};

    public Features() {
        // TODO: Write methods to add user inputs to the presets!
        //       Paige needs those methods to do the user story:
        //          "Add new values to pre-existing ones"
        r = new Random();

        races = new ArrayList<>();
        hairColors = new ArrayList<>();
        hairTypes = new ArrayList<>();
        eyeColors = new ArrayList<>();
        bodyTypes = new ArrayList<>();

        // These just put the presets into our ArrayLists
        // (They need to be ArrayLists because we have to be able to add user input to them)
        races.addAll(Arrays.asList(racePresets));
        hairColors.addAll(Arrays.asList(colorPresets));
        hairTypes.addAll(Arrays.asList(hairTypePresets));
        eyeColors.addAll(Arrays.asList(eyeColorPresets));
        bodyTypes.addAll(Arrays.asList(bodyTypePresets));

        race = races.get(r.nextInt(racePresets.length));
        hairColor = hairColors.get(r.nextInt(colorPresets.length));
        hairType = hairTypes.get(r.nextInt(hairTypePresets.length));
        eyeColor = eyeColors.get(r.nextInt(eyeColorPresets.length));
        bodyType = bodyTypes.get(r.nextInt(bodyTypePresets.length));

        //height = ;
    }

    // Race get, set, and add methods
    protected String getRace() {
        return race;
    }
    protected void setRace(String newRace) {
        race = newRace;
    }
    protected void addRace(String addedRace) {
        races.add(addedRace);
    }

    // Hair color get, set, and add methods
    protected String getHairColor() {
        return hairColor;
    }
    protected void setHairColor(String newHairColor) {
        hairColor = newHairColor;
    }
    protected void addHairColor(String addedHairColor) {
        hairColors.add(addedHairColor);
    }

    // Hair type get, set, and add methods
    protected String getHairType() {
        return hairType;
    }
    protected void setHairType(String newHairType) {
        hairType = newHairType;
    }
    protected void addHairType(String addedHairType) {
        hairTypes.add(addedHairType);
    }

    // Eye color get, set, and add methods
    protected String getEyeColor() {
        return eyeColor;
    }
    protected void setEyeColor(String newEyeColor) {
        eyeColor = newEyeColor;
    }
    protected void addEyeColor(String addedEyeColor) {
        eyeColors.add(addedEyeColor);
    }

    // Body type get, set, and add methods
    protected String getBodyType() {
        return bodyType;
    }
    protected void setBodyType(String newBodyType) {
        bodyType = newBodyType;
    }
    protected void addBodyType(String addedBodyType) {
        bodyTypes.add(addedBodyType);
    }

    /**
     * randomize()
     * Randomizes the Character features based on the available features in each ArrayList
     */
    protected void randomize() {
        setRace(races.get(r.nextInt(races.size())));
        setHairType(hairTypes.get(r.nextInt(hairTypes.size())));
        setHairColor(hairColors.get(r.nextInt(hairColors.size())));
        setEyeColor(eyeColors.get(r.nextInt(eyeColors.size())));
        setBodyType(bodyTypes.get(r.nextInt(bodyTypes.size())));
    }

    // TEST METHODS
    //
    // Test 1
    private static boolean TestRandomFeatures(Features f1, Features f2) {

        // If any of the values for either feature <f1> or <f2> are matching, test fails
        // else, test passes
        if (f1.race.equalsIgnoreCase(f2.race) ||
                f1.hairColor.equalsIgnoreCase(f2.hairColor) ||
                f1.hairType.equalsIgnoreCase(f2.hairType) ||
                f1.eyeColor.equalsIgnoreCase(f2.eyeColor) ||
                f1.bodyType.equalsIgnoreCase(f2.bodyType)) {
            return false;
        } else {
            return true;
        }
    }


    public static void main(String[] args) {
        System.out.println("This is the Features Test Suite\n");

        Features f1 = new Features();
        Features f2 = new Features();
        boolean Expected;
        boolean Result;
        Boolean ErrorDetected = false;

        // Test 1
        // Ensure that features are randomly generated with randomize()
        f1.randomize();
        f2.randomize();
        Expected = true;
        Result = Features.TestRandomFeatures(f1, f2);

        if (Result != Expected) {
            System.out.println("Error in Test 1: Result != Expected");


        }


    }
}
