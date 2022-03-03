package sample;

import java.util.ArrayList;

public class CombatModel {

    ArrayList<CombatSubscriber> subs = new ArrayList<>();

    //uncomment once character generator is implemented
    /**
     protected Character player;
     protected Character enemy;
     */

    //temp variables which should be deleted once character generator is implemented
    public int health;
    public int damage;
    public int dex;
    public int strength;


    public int phase;
    public boolean playerTurn;
    public boolean enemyTurn;

    public ArrayList<String> combatDialogue;

    public CombatModel(){
        combatDialogue = new ArrayList<String>();
        combatDialogue.add("A wild Charizard has appeared!");
        combatDialogue.add("It is the players turn!");
        combatDialogue.add("It is the enemies turn!");
        combatDialogue.add("The player did x damage");
        combatDialogue.add("The enemy did x damage");
    }

    /**
     * subtract damage from character health
     */
    public void attack(){
        // remove this line
        health -= (damage * strength);

        // uncomment code when character generator is implemented
        /**
        if(playerTurn){
            enemy.health -= (player.damage * player.strength);
        }else{
            player.health -= (enemy.damage * enemy.strength);
        }
         */
        phase += 1;
        playerTurn = false;
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
     * use dexterity stat to find who goes first
     * calls playerPhase() if player dex is higher, enemyPhase() if not
     */
    public void whoGoesFirst(){
        /**
        if(player.dexterity > enemy.dexterity){
            playerTurn = true;
        }else if (player.dexterity < enemy.dexterity){
            enemyTurn = true;
        }else{
            int decision = (int) (Math.random() * 101 + 1);

            if(decision >= 50){
                playerTurn = true;
                enemyTurn = false;
            }else{
                enemyTurn = true;
                playerTurn = false;

            }
        }
        */
    }

    /**
     * type out dialogue automatically one letter at a time
     * (i.e. pokemon/final fantasy)
     */
    public void typeOutDialogue(int index) throws InterruptedException {
        for(int i = 0; i < combatDialogue.get(index).length(); i++){
            try{
                Thread.sleep((int) (Math.random() * 175 + 100));
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }

            // This line will need to be modified when the UI is complete
            System.out.print(combatDialogue.get(index).charAt(i));
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

    }

    /**
     * players turn
     */
    public void playerPhase(){
        return;
    }

    /**
     * go to next phase of battle
     * NOTE: this method is likely to change
     */
    public void nextPhase() throws InterruptedException {
        if(phase >= 10){
            phase = 0;
        }
        if(phase == 3){
            playerTurn = true;
            playerPhase();
        }else{
            playerTurn = false;
        }
        phase += 1;
        notifySubscribers();
    }

    //end combat if random number coin toss is in players favor
    public void runAway(){

    }

    //end combat
    public void endCombat(){

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
}
