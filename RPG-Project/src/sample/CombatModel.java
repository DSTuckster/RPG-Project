package sample;

import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

public class CombatModel {

    ArrayList<CombatSubscriber> subs = new ArrayList<>();

    protected Character player;
    protected Character enemy;

    protected CombatScenario scenario;

    public int phase;
    public int playerTurnPhase;
    public int enemyTurnPhase;
    public boolean playerTurn;

    protected int playerTotalWisdom;
    protected int playerTotalHealth;

    protected boolean runAway;

    public Hashtable<Integer, String> combatDialogue;
    public String currentDialogue;

    public CombatModel(){
        combatDialogue = new Hashtable<>();
    }

    /**
     * When the player triggers a combat scenario, then the traversal system will call this method, which triggers combat
     * NOTE: this method is incomplete
     * @param s: a new combat scenario
     */
    public void setCombatScenario(CombatScenario s){
        scenario = s;

        player = scenario.player;
        enemy = scenario.enemy;

        playerTotalWisdom = player.characterStats.getMana();
        playerTotalHealth = player.characterStats.getHealth();

        //NOTE: The middle portion of this string should not be in double quotes. remove when name generation is implemented
        combatDialogue.put(0 ,"A wild " + enemy.name + " has appeared!");
        currentDialogue = combatDialogue.get(0);
        whoGoesFirst();
    }

    /**
     * subtract damage from character health
     */
    public void attack(){
        int extraDamage = (int) (Math.random() * 5 + 1);
        if(playerTurn){
            //player attacks
            int damage = player.characterStats.getStr() + extraDamage;
            int newHealth = enemy.characterStats.getHealth() - damage;
            enemy.characterStats.setHealth(newHealth);
            combatDialogue.replace(phase+1, "The player did " + damage + " damage");
            playerTurn = false;
        }else{
            //enemy attacks
            int damage = enemy.characterStats.getStr() + extraDamage;
            int newHealth = player.characterStats.getHealth() - damage;
            player.characterStats.setHealth(newHealth);
            combatDialogue.replace(phase+1, "The enemy did " + damage + " damage");
            playerTurn = true;
        }
        phase += 1;
        notifySubscribers();
    }

    /**
     * when player or enemy uses magic,
     * then subtract cost of spell from magic points stat
     */
    public void usedMagic(){
        if(playerTurn && player.characterStats.getMana() >= 10){
            int newHealth = enemy.characterStats.getHealth() - player.characterStats.getInt();
            enemy.characterStats.setHealth(newHealth);

            // subtract magic points from player
            player.characterStats.setMana(player.characterStats.getMana()-10);
            combatDialogue.replace(phase+1, "The player used a spell and did " + player.characterStats.getInt() + " damage");
            playerTurn=false;

        }else if(enemy.characterStats.getMana() >= 10){
            int newHealth = player.characterStats.getHealth() - enemy.characterStats.getInt();
            player.characterStats.setHealth(newHealth);

            // subtract magic points from enemy
            enemy.characterStats.setMana(enemy.characterStats.getMana()-10);
            combatDialogue.replace(phase+1, "The enemy used a spell and did " + enemy.characterStats.getInt() + " damage");
            playerTurn=true;
        }
        notifySubscribers();
    }

    /**
     * called when player wins
     * adds to player exp and levels up player (adds to player stats)
     * formula = ?
     */
    public void expGain(){
        //add exp to player, and if player has enough to level up, then increment player level
        player.characterStats.addExp(enemy.characterStats.getExp());
        if (player.characterStats.getExp() > 100){
            player.characterStats.levelUp();
            player.characterStats.addExp(-100);
        }
        notifySubscribers();
    }

    /**
     * go to next phase of battle
     * NOTE: this method is likely to change
     */
    public void nextPhase() throws InterruptedException {
        if(phase == 7){
            endCombat();
        }
        if(!endCombatChecks() && phase >= 4){
            phase = 0;
        }
        if(phase == playerTurnPhase){
            playerTurn = true;
            playerPhase();
        }else if(phase == enemyTurnPhase){
            playerTurn = false;
            enemyPhase();
        }else{
            playerTurn = false;
        }
        phase += 1;
        currentDialogue = combatDialogue.get(phase);
        notifySubscribers();
    }

    /**
     * NOTE: something is wrong with this method, not sure what - Dylan
     * use dexterity stat to find who goes first
     * calls playerPhase() if player dex is higher, enemyPhase() if not
     */
    public void whoGoesFirst(){
        if(player.characterStats.getDex() > enemy.characterStats.getDex()){   //player's turn
            playerTurn = true;
        }else if (player.characterStats.getDex() < enemy.characterStats.getDex()){    //enemies turn
            playerTurn = false;
        }else{  // playerDex == enemyDex, so roll dice
            int decision = (int) (Math.random() * 101 + 1);

            if(decision >= 50){
                playerTurn = true;
            }else{
                playerTurn = false;
            }
        }
        setCombatDialogue();
        notifySubscribers();
    }

    public void setCombatDialogue(){
        if(playerTurn){
            playerTurnPhase = 0;
            enemyTurnPhase = 2;
            combatDialogue.put(1, "It is the players turn!");
            combatDialogue.put(2, "The player did " + player.characterStats.getStr() + " damage");
            combatDialogue.put(3, "It is the enemies turn!");
            combatDialogue.put(4, "The enemy did " + enemy.characterStats.getStr() + " damage");
        }else{
            playerTurnPhase = 2;
            enemyTurnPhase = 0;
            combatDialogue.put(1, "It is the enemies turn!");
            combatDialogue.put(2, "The enemy did " + enemy.characterStats.getStr() + " damage");
            combatDialogue.put(3, "It is the players turn!");
            combatDialogue.put(4, "The player did " + player.characterStats.getStr() + " damage");
        }
    }

    /**
     * use character generator to create an enemy for battle
     */
    public Character createEnemy(){
        Character c = new Character();
        return c;
    }

    /**
     * enemies turn
     */
    public void enemyPhase(){
        if(enemy.characterStats.getInt() >= enemy.characterStats.getStr()){
            usedMagic();
        }else{
            attack();
        }
    }

    /**
     * players turn
     */
    public void playerPhase(){
        return;
    }

    //end combat if random number coin toss is in players favor
    public void runAway(){
        int check = (int) (Math.random() * 101 + 1);
        if(check > 51){
            runAway = true;
            combatDialogue.put(6, "The player escaped");
            currentDialogue = combatDialogue.get(6);
            phase = 6;
        }else{
            combatDialogue.put(phase+1, "The player tried to run away but could not escape.");
        }
        notifySubscribers();
    }

    /**
     * check for end combat conditions, if conditions are true, then end combat
     * conditions:
     * run away
     * player health below zero
     * enemy Health below zero
     */
    public boolean endCombatChecks(){
        boolean end = false;
        if(player.characterStats.getHealth() <= 0){
            combatDialogue.put(5, "The player wins!");
            end = true;
        }else if(enemy.characterStats.getHealth() <= 0){
            combatDialogue.put(5, "The player lost!");
            end = true;
        }else if(runAway){
            end = true;
        }
        phase = 5;
        currentDialogue = combatDialogue.get(phase);

        return end;
    }

    //end combat
    public void endCombat(){
        player.characterStats.setWis(playerTotalWisdom);
        player.characterStats.setHealth(playerTotalHealth);
        combatDialogue.clear();
    }

    /**
     * if the user selects yes, to retry combat
     * this method will completely restart the same combat scenario combat
     */
    public void restCombat(){
        player.characterStats.setMana(player.characterStats.getMaxMana());
        player.characterStats.setHealth(player.characterStats.getMaxHealth());
        enemy.characterStats.setMana(enemy.characterStats.getMaxMana());
        enemy.characterStats.setHealth(enemy.characterStats.getMaxHealth());
        combatDialogue.clear();
        playerTurn = false;
        runAway = false;
        playerTurnPhase = 0;
        enemyTurnPhase = 0;
        currentDialogue = "";

        CombatScenario newCombatScenario = new CombatScenario(player, enemy);
        setCombatScenario(newCombatScenario);
        notifySubscribers();
    }

    /**
     * add a view to implement changes from model
     * @param sub: a view (in this case, combatView)
     */
    public void addSubscriber(CombatSubscriber sub){
        subs.add(sub);
    }

    /**
     * notify all subscribers that the model has changed
     * and the subscribers should update to reflect changes
     */
    public void notifySubscribers(){
        for(CombatSubscriber sub : subs){
            sub.modelChanged();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CombatModel model = new CombatModel();

        //set combat scenario test #1
        model.setCombatScenario(new CombatScenario(new Character(), new Character()));

        //nextPhase() test #1
        //model.nextPhase();
        int expected = 0;
        int result = model.phase;
        if(expected != result){
            System.out.println("nextPhase() test #1 failed! expected = " + expected + ", result = " + result);
        }

        //whoGoesFirst() test #1
        model.whoGoesFirst();

        if(model.player.characterStats.getDex() > model.enemy.characterStats.getDex()){
            result = 1;
        }else if(model.player.characterStats.getDex() < model.enemy.characterStats.getDex()){
            result = 0;
        }
        if(result == 0 && model.playerTurn){
            System.out.println("whoGoesFirst() test #1 failed! expected = playerTurn, result = enemyTurn");
        }else if(result == 1 && !model.playerTurn){
            System.out.println("whoGoesFirst() test #1 failed! expected = enemyTurn, result = PlayerTurn");
        }

        //expGain() test #1
        model.enemy.characterStats.addExp(50);
        model.expGain();
        expected = 50;
        result = model.player.characterStats.getExp();
        if(expected != result){
            System.out.println("expGain() test #1 failed! expected = " + expected + " result = " + result);
        }

        //test each combat phase
        //This variable is a safety net, I would occasionally get an infinite loop. I think I fixed it, but just in case...
        int count = 0;
        while(!model.endCombatChecks()){
            if(count == 10){
                System.out.println("break!!!!!!!!!!!!!!!!!!!!");
                break;
            }
            if(model.playerTurn){
                int enemyTotalHealth = model.enemy.characterStats.getHealth();
                model.attack();
                model.nextPhase();
                int enemyHealth = model.enemy.characterStats.getHealth();
                int playerDamage = enemyTotalHealth - enemyHealth;
                System.out.println("It is the player's turn. The enemies total health was " + enemyTotalHealth + ". The enemies health is now " + enemyHealth);
                System.out.println("total damage was " + playerDamage);
            }else if(model.enemyTurnPhase-1 == model.phase){
                model.nextPhase();
                //model.attack();
                int playerHealth = model.player.characterStats.getHealth();
                int playerTotalHealth = model.playerTotalHealth;
                int enemyDamage = playerTotalHealth - playerHealth;
                System.out.println("It is the enemies turn. The player's total health was " + playerTotalHealth + ". The player's health is now " + playerHealth);
                System.out.println("total damage was " + enemyDamage);
            }else{
                model.nextPhase();
            }
            count ++;
        }

        model.setCombatDialogue();


        //expGain() test #2
        model.enemy.characterStats.addExp(1);
        model.expGain();
        expected = 1;
        result = model.player.characterStats.getExp();
        if(expected != result){
            System.out.println("expGain() test #2 failed! expected = " + expected + " result = " + result);
        }

        // player level up test #1, expected = 2
        if(model.player.characterStats.getCharacterLevel() != 2){
            System.out.println("Player level up test #1 failed! expected = 2 result = " + model.player.characterStats.getCharacterLevel());
        }

        //create enemy test #1
        model.createEnemy();
        Character characterResult = model.enemy;
        if(characterResult == null){
        System.out.println("createEnemy() test #1 failed! result = " + result);
        }


        //attack() test #1
        expected = model.enemy.characterStats.getHealth();
        model.playerTurn = true;
        model.attack();
        result = model.enemy.characterStats.getHealth();
        if(expected <= result){
            System.out.println("attack() test # 1 failed! expected = " + expected + " result = " + result);
        }

        //check combatDialogue
        for(int i =  0; i < model.combatDialogue.size(); i++){
            System.out.println(model.combatDialogue.get(i));
        }

        //resetCombat() test
        expected = model.player.characterStats.getHealth();
        model.restCombat();
        result = model.player.characterStats.getHealth();
        if(expected == result){
            System.out.println("RestCombat() test failed! expected = " + expected + " result = " + result);
        }
    }
}
