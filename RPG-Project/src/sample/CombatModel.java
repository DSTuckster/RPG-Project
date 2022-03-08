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
    public boolean playerTurn;
    public boolean enemyTurn;

    public Hashtable<Integer, String> combatDialogue;

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

        combatDialogue.put(0 ,"A wild Charizard has appeared!");
        whoGoesFirst();
    }

    /**
     * subtract damage from character health
     */
    public void attack(){
        if(playerTurn){
            //player attacks
            int newHealth = enemy.characterStats.getHealth() - player.characterStats.getStr();
            enemy.characterStats.setHealth(newHealth);
            playerTurn = false;
        }else{
            //enemy attacks
            int newHealth = player.characterStats.getHealth() - enemy.characterStats.getStr();
            player.characterStats.setHealth(newHealth);
            enemyTurn = false;
        }
        phase += 1;
        notifySubscribers();
    }

    /**
     * called when player wins
     * adds to player exp and levels up player (adds to player stats)
     * formula = ?
     */
    public void expGain(){

    }

    /**
     * go to next phase of battle
     * NOTE: this method is likely to change
     */
    public void nextPhase() throws InterruptedException {
        System.out.println("yes");
        if(phase >= 4){
            phase = 0;
        }
        if(phase == playerTurnPhase-1){
            playerTurn = true;
            playerPhase();
        }else{
            playerTurn = false;
        }
        phase += 1;
        notifySubscribers();
    }

    /**
     * NOTE: something is wrong with this method, not sure what - Dylan
     * use dexterity stat to find who goes first
     * calls playerPhase() if player dex is higher, enemyPhase() if not
     */
    public void whoGoesFirst(){
        System.out.println("Player: " + player.characterStats.getDex());
        System.out.println("Enemy: " + enemy.characterStats.getDex());

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
        System.out.println(enemyTurn);
        System.out.println(playerTurn);
        setCombatDialogue();
    }

    public void setCombatDialogue(){
        if(playerTurn){
            playerTurnPhase = 1;
            combatDialogue.put(1, "It is the players turn!");
            combatDialogue.put(2, "The player did x damage");
            combatDialogue.put(3, "It is the enemies turn!");
            combatDialogue.put(4, "The enemy did x damage");
        }else{
            playerTurnPhase = 3;
            combatDialogue.put(1, "It is the enemies turn!");
            combatDialogue.put(2, "The enemy did x damage");
            combatDialogue.put(3, "It is the players turn!");
            combatDialogue.put(4, "The player did x damage");
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
     * when player or enemy uses magic,
     * then subtract cost of spell from magic points stat
     */
    public void usedMagic(){
        if(playerTurn){
            // subtract magic points from player

        }else{
            // subtract magic points from enemy

        }
    }

    /**
     * use character generator to create an enemy for battle
     */
    public void createEnemy(){

    }

    /**
     * enemies turn
     */
    public void enemyPhase(){
        return;
    }

    /**
     * players turn
     */
    public void playerPhase(){
        return;
    }

    //end combat if random number coin toss is in players favor
    public void runAway(){

    }

    //end combat
    public void endCombat(){
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
        for (int i = 0; i < 10; i++){
            model.nextPhase();
        }

        expected = 1;
        result = model.phase;
        if(expected != result){
            System.out.println("nextPhase() test #1 failed! expected = " + expected + ", result = " + result);
        }

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

        //typeOutDialogue() test #1
        //model.typeOutDialogue(model.phase);

        //the following tests should be uncommented once character generator is implemented
        /**
         //create enemy test #1
         model.createEnemy();
         Character characterResult = model.enemy;
         if(characterResult == null){
         System.out.println("createEnemy() test #1 failed! result = " + result);
         }


         //attack() test #1
         model.attack(true);

         expected = 50;
         result = model.enemy.health;
         if(expected != result){
         System.out.println("test #1 failed! expected = " + expected + ", result = " + result);
         }
         */
    }
}
