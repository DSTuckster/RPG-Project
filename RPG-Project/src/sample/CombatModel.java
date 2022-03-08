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
    public boolean enemyTurn;

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

        playerTotalWisdom = player.characterStats.getWis();
        playerTotalHealth = player.characterStats.getHealth();

        //NOTE: The middle portion of this string should not be in double quotes. remove when name generation is implemented
        combatDialogue.put(0 ,"A wild" + "enemy.characterStats.getName()" + " has appeared!");
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
            combatDialogue.put(phase+1, "The player did " + damage + " damage");
            playerTurn = false;
        }else{
            //enemy attacks
            int damage = enemy.characterStats.getStr() + extraDamage;
            int newHealth = player.characterStats.getHealth() - damage;
            player.characterStats.setHealth(newHealth);
            combatDialogue.put(phase+1, "The enemy did " + damage + " damage");
            enemyTurn = false;
        }
        phase += 1;
        notifySubscribers();
    }

    /**
     * when player or enemy uses magic,
     * then subtract cost of spell from magic points stat
     */
    public void usedMagic(){
        if(playerTurn){
            int newHealth = enemy.characterStats.getHealth() - player.characterStats.getInt();
            enemy.characterStats.setHealth(newHealth);
            // subtract magic points from player
            player.characterStats.setWis(player.characterStats.getWis()-2);
            combatDialogue.put(phase+1, "The player used a spell and did " + player.characterStats.getInt() + " damage");

        }else{
            int newHealth = player.characterStats.getHealth() - enemy.characterStats.getInt();
            player.characterStats.setHealth(newHealth);
            // subtract magic points from enemy
            enemy.characterStats.setWis(enemy.characterStats.getWis()-2);
            combatDialogue.put(phase+1, "The enemy used a spell and did " + enemy.characterStats.getInt() + " damage");
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
        if(endCombatChecks()){
            return;
        }
        if(phase == 6){
            endCombat();
        }
        if(phase >= 4){
            phase = 0;
        }
        if(phase == playerTurnPhase-1){
            playerTurn = true;
            enemyTurn = false;
            playerPhase();
        }else if(phase == enemyTurnPhase-1){
            enemyTurn = true;
            playerTurn = false;
            enemyPhase();
        }else{
            playerTurn = false;
            enemyTurn = false;
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
            enemyTurn = false;
        }else if (player.characterStats.getDex() < enemy.characterStats.getDex()){    //enemies turn
            enemyTurn = true;
            playerTurn = false;
        }else{  // playerDex == enemyDex, so roll dice
            int decision = (int) (Math.random() * 101 + 1);

            if(decision >= 50){
                playerTurn = true;
                enemyTurn = false;
            }else{
                enemyTurn = true;
                playerTurn = false;
            }
        }
        setCombatDialogue();
        notifySubscribers();
    }

    public void setCombatDialogue(){
        if(playerTurn){
            playerTurnPhase = 1;
            enemyTurnPhase = 3;
            combatDialogue.put(1, "It is the players turn!");
            combatDialogue.put(2, "The player did " + player.characterStats.getStr() + " damage");
            combatDialogue.put(3, "It is the enemies turn!");
            combatDialogue.put(4, "The enemy did " + enemy.characterStats.getStr() + " damage");
        }else{
            playerTurnPhase = 3;
            enemyTurnPhase = 1;
            combatDialogue.put(1, "It is the enemies turn!");
            combatDialogue.put(2, "The enemy did " + enemy.characterStats.getStr() + " damage");
            combatDialogue.put(3, "It is the players turn!");
            combatDialogue.put(4, "The player did " + player.characterStats.getStr() + " damage");
        }
    }

    /**
     * type out dialogue automatically one letter at a time
     * (i.e. pokemon/final fantasy)
     * NOTE: can't get typeOutDialogue to work for some reason, I will come back to it later - Dylan
     */
    public void typeOutDialogue(int index, Text text) throws InterruptedException {
        text.setText("");
        for(int i = 0; i < combatDialogue.get(phase).length(); i++){
            /**
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
             */

            // This line will need to be modified when the UI is complete
            text.setText(text.getText() + combatDialogue.get(phase).charAt(i));
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
            endCombat();
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
            combatDialogue.put(6, "The player wins!");
            end = true;
        }else if(enemy.characterStats.getHealth() <= 0){
            combatDialogue.put(6, "The player lost!");
            end = true;
        }else if(runAway){
            end = true;
        }
        phase = 6;
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
        model.nextPhase();
        int expected = 1;
        int result = model.phase;
        if(expected != result){
            System.out.println("nextPhase() test #1 failed! expected = " + expected + ", result = " + result);
        }

        //nextPhase() test #2
        /**
        for (int i = 0; i < 5; i++){
            model.nextPhase();
        }

        expected = 1;
        result = model.phase;
        if(expected != result){
            System.out.println("nextPhase() test #2 failed! expected = " + expected + ", result = " + result);
        }
         */

        //whoGoesFirst() test #1
        model.whoGoesFirst();

        if(model.player.characterStats.getDex() > model.enemy.characterStats.getDex() && model.playerTurn){
            result = 1;
        }else if(model.player.characterStats.getDex() < model.enemy.characterStats.getDex() && model.enemyTurn){
            result = 0;
        }else{
            result = -1;
        }
        if(result == 0 && model.playerTurn){
            System.out.println("whoGoesFirst() test #1 failed! expected = playerTurn, result = enemyTurn");
        }else if(result == 1 && model.enemyTurn){
            System.out.println("whoGoesFirst() test #1 failed! expected = enemyTurn, result = PlayerTurn");
        }
        else if(result == -1){
            System.out.println("whoGoesFirst() test #1 failed! expected = playerTurn, result = nobodies turn");
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
        for(int i = 0; i < 20; i++){
            if(model.playerTurn){
                int enemyTotalHealth = model.enemy.characterStats.getHealth();
                model.attack();
                model.nextPhase();
                int enemyHealth = model.enemy.characterStats.getHealth();
                int playerDamage = enemyTotalHealth - enemyHealth;
                System.out.println("It is the player's turn. The enemies total health was " + enemyTotalHealth + ". The enemies health is now " + enemyHealth);
                System.out.println("total damage was " + playerDamage);
            }else if(model.enemyTurn){
                model.nextPhase();
                int playerHealth = model.player.characterStats.getHealth();
                int playerTotalHealth = model.playerTotalHealth;
                int enemyDamage = playerTotalHealth - playerHealth;
                System.out.println("It is the enemies turn. The player's total health was " + playerTotalHealth + ". The player's health is now " + playerHealth);
                System.out.println("total damage was " + enemyDamage);
            }
            model.nextPhase();
        }

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


        //typeOutDialogue() test #1
        //model.typeOutDialogue(model.phase);

        //the following tests should be uncommented once character generator is implemented

        //create enemy test #1
        model.createEnemy();
        Character characterResult = model.enemy;
        if(characterResult == null){
        System.out.println("createEnemy() test #1 failed! result = " + result);
        }


        //attack() test #1
        model.playerTurn = true;
        model.attack();
    }
}
