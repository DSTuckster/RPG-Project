package sample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Stats {
    protected int CharacterLevel;
    protected int exp;
    protected int Health;
    protected int Mana;

    protected int Strength;
    protected int Dexterity;
    protected int Constitution;
    protected int Wisdom;
    protected int Intelligence;
    protected int Charisma;

    private static Random r;

    public Stats() {
        r = new Random();

        CharacterLevel = 1;
        exp = 0;

        // In 'Dungeons & Dragons 5e', the standard method of rolling stats is "Roll 4d6, drop the lowest"
        // The sum of those values is then assigned as the stat value
        // To simulate this, we will 'roll' 4 dice using the Random package, then take the top value to use as a stat
        Strength = roll4toss1();
        Dexterity = roll4toss1();
        Constitution = roll4toss1();
        Wisdom = roll4toss1();
        Intelligence = roll4toss1();
        Charisma = roll4toss1();

        // Health is calculated from the CharacterLevel as 'Hit Dice'
        // For instance, a character at level 5 would have "5d12" max hp, or 60hp
        //
        //      * For now, only adds max value, 100 *
        //
        Health = CharacterLevel * 100;
        Mana = 100;

    }

    // Character Level get & set methods
    public int getCharacterLevel(){ return CharacterLevel; }
    public void setCharacterLevel(int level){ CharacterLevel = level; }

    /**
     * levelUp()
     *  Increments the Character Level by 1
     *  Increases stats by 5 each
     */
    public void levelUp(){
        exp -= getMaxExp();
        CharacterLevel++;
        while(exp >= getMaxExp()){
            exp -= getMaxExp();
            CharacterLevel++;
        }
        Health = getMaxHealth();
        Strength += 5;
        Dexterity += 5;
        Constitution += 5;
        Wisdom += 5;
        Intelligence += 5;
        Charisma += 5;
    }

    /**get players current experience points
     * when player gets enough exp they will level up
     */
    public int getExp(){
        return exp;
    }
    public int getMaxExp(){
        return (int)Math.pow(2, CharacterLevel);
    }

    //add points to exp
    public void addExp(int enemyLevel){
        exp += (int)Math.pow(2, enemyLevel)+10;
    }

    // Health get & set methods
    public int getHealth(){ return Health; }
    public int getMaxHealth() { return getCharacterLevel() * 100; }
    public void setHealth(int newHealth){ Health = newHealth; }

    // Mana get & set methods
    public int getMana() { return Mana; }
    public int getMaxMana() { return 100; }
    public void setMana(int newMana) { Mana = newMana; }

    // Strength get & set methods
    public int getStr() { return Strength; }
    public void setStr(int newStrength) { Strength = newStrength; }

    // Dexterity get & set methods
    public int getDex() { return Dexterity; }
    public void setDex(int newDexterity) { Dexterity = newDexterity; }

    // Constitution get & set methods
    public int getCon() { return Constitution; }
    public void setCon(int newConstitution) { Constitution = newConstitution; }

    // Wisdom get & set methods
    public int getWis() { return Wisdom; }
    public void setWis(int newWisdom) { Wisdom = newWisdom; }

    // Intelligence get & set methods
    public int getInt() { return Intelligence; }
    public void setInt(int newIntelligence) { Intelligence = newIntelligence; }

    // Charisma get & set methods
    public int getCha() { return Charisma; }
    public void setCha(int newCharisma) { Charisma = newCharisma; }


    /**
     * roll4Toss1()
     *  Simply rolls 4 values from 1-6, returning the top value
     * return: max of 4 d6 rolls
     */
    private int roll4toss1() {
        // First, initializes an array list to hold the rolls
        ArrayList<Integer> list = new ArrayList<>();
        // Then, add each roll to the list
        for (int i=0; i<4; i++) {
            list.add(r.nextInt(7));
        }
        // Finally, sort the list in ascending order,
        // and reverse it to get a descending list
        Collections.sort(list);
        Collections.reverse(list);

        // return sum of the first 3 items (the highest values)
        return list.get(0)+list.get(1)+list.get(2);
    }
    
    @Override
    public String toString() {
        return "Stats{" +
                "CharacterLevel=" + CharacterLevel +
                ", exp=" + exp +
                ", Health=" + Health +
                ", Mana=" + Mana +
                ", Strength=" + Strength +
                ", Dexterity=" + Dexterity +
                ", Constitution=" + Constitution +
                ", Wisdom=" + Wisdom +
                ", Intelligence=" + Intelligence +
                ", Charisma=" + Charisma +
                '}';
    }

    // TEST METHODS
    private boolean TestRollsAccuracy() {
        return (Strength > 2 && Strength < 19) &&
                (Dexterity > 2 && Dexterity < 19) &&
                (Constitution > 2 && Constitution < 19) &&
                (Wisdom > 2 && Wisdom < 19) &&
                (Intelligence > 2 && Intelligence < 19) &&
                (Charisma > 2 && Charisma < 19);
    }

    protected void RunStatsTestSuite() {
        System.out.println("This is the Stats Test Suite");
        Stats stats = new Stats();
        boolean Expected;
        boolean Result;
        boolean ErrorDetected = false;

        // Test 1
        // Test to see that the values rolled range from 3 to 18 (max and min roll of 3 dice)
        // Run test 100 times to assert validity of rolls through iteration
        for (int i = 0; i < 1000; i++) {
            Result = stats.TestRollsAccuracy();

            if (!Result) {
                System.out.println("ERROR in Test 1, <Result> != Expected");
                ErrorDetected = true;
            }
        }
        if (!ErrorDetected) {
            System.out.println("Roll accuracy tests successful");
        }

        if (ErrorDetected) {
            System.out.println("Errors Detected in StatsTestSuite");
        } else {
            System.out.println("No errors in StatsTestSuite\n");
        }
    }

    public static void main(String[] args) {
        System.out.println("This is the Stats Test Suite\n");

        Stats stats = new Stats();
        boolean Expected;
        boolean Result;
        boolean ErrorDetected = false;


        // Test 1
        // Test to see that the values rolled range from 3 to 18 (max and min roll of 3 dice)
        // Run test 100 times to assert validity of rolls through iteration
        for (int i = 0; i < 100; i++) {
            Result = stats.TestRollsAccuracy();
//            System.out.println(stats.getStr());
//            System.out.println(stats.getDex());
//            System.out.println(stats.getCon());
//            System.out.println(stats.getWis());
//            System.out.println(stats.getInt());
//            System.out.println(stats.getCha());
            if (!Result) {
                System.out.println("ERROR in Test 1, <Result> != Expected");
                ErrorDetected = true;
            }
        }
        if (!ErrorDetected) {
            System.out.println("Roll accuracy tests successful\n");
        }


        if (ErrorDetected) {
            System.out.println("Errors Detected in Stats.java");
        } else {
            System.out.println("No errors in Stats.java");
        }

    }

}
