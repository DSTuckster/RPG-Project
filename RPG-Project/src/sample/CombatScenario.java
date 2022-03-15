package sample;

public class CombatScenario {

    protected Character player;
    protected Character enemy;

    public CombatScenario(Character p, Character e){
        player = p;
        enemy = e;
    }

    /**
     * calling this version of the constructor will default to the boss fight
     * @param p: The player character
     */
    public CombatScenario(Character p){
        player = p;
        enemy = null;
    }
}
