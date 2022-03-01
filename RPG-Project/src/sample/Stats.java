package sample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Stats {
    protected int health;
    protected int Strength;
    protected int Dexterity;
    protected int Constitution;
    protected int Wisdom;
    protected int Intelligence;
    protected int Charisma;

    private Random r;

    public Stats() {
        r = new Random();

        // In 'Dungeons & Dragons 5e', the standard method of rolling stats is "Roll 4d6, drop the lowest"
        // The sum of those values is then assigned as the stat value
        // To simulate this, we will 'roll' 4 dice using the Random package, then take the top value to use as a stat

        Strength = roll4toss1();
        Dexterity = roll4toss1();
        Constitution = roll4toss1();
        Wisdom = roll4toss1();
        Intelligence = roll4toss1();
        Charisma = roll4toss1();
    }

    // health get & set methods
    public int getHealth(){ return health; }
    public void setHealth(int newHealth){ health = newHealth; }

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
     * @return: max of 4 d6 rolls
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

        // return sum of the first 3 items (highest values)
        return list.get(0)+list.get(1)+list.get(2);
    }

    public static void main(String[] args) {
        System.out.println("This is the Stats Test Suite\n");
    }

    // TODO: Finish the Stats tests
}
