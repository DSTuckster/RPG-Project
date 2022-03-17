package sample;



import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;


public class CombatModel {

    ArrayList<CombatSubscriber> subs = new ArrayList<>();

    protected Character player;
    protected Character enemy;

    protected CombatScenario scenario;

    public int phase;
    public int playerTurnPhase;
    public int enemyTurnPhase;
    public boolean playerTurn;

    protected int costPerSpell;

    protected boolean runAway;

    protected boolean reset;

    public Hashtable<Integer, String> combatDialogue;
    public String currentDialogue;

    public Character boss;

    private Random r = new Random();

    public CombatModel(){
        costPerSpell = 25;
        combatDialogue = new Hashtable<>();
        boss = new Character();
        setBoss(20);
    }

    /**
     * When the player triggers a combat scenario, then the traversal system will create a new combat scenario and call this method
     * This method prepares the model for combat
     * @param s: a new combat scenario
     */
    public void setCombatScenario(CombatScenario s) {
        scenario = s;

        player = scenario.player;
        enemy = scenario.enemy;

        if(enemy == null){
            enemy = boss;
        }

        combatDialogue.put(0 ,"A wild " + enemy.name + " has appeared!");
        setCurrentDialogue(combatDialogue.get(phase));

        whoGoesFirst();
        notifySubscribers();
    }

    /**
     * subtract damage from character health
     * damage = character strength + random value between 1 and 5
     */
    public void attack() {
        int extraDamage = (int) (Math.random() * 5 + 1);
        //IF statement checks whose turn it is
        if(playerTurn){
            //player attacks
            int damage = player.characterStats.getStr() + extraDamage;
            int newHealth = enemy.characterStats.getHealth() - damage;
            enemy.characterStats.setHealth(newHealth);

            combatDialogue.replace(phase+1, "The player did " + damage + " damage");
            setCurrentDialogue(combatDialogue.get(phase));
            playerTurn = false;
        }else{
            //enemy attacks
            int damage = enemy.characterStats.getStr() + extraDamage;
            int newHealth = player.characterStats.getHealth() - damage;
            player.characterStats.setHealth(newHealth);
            combatDialogue.replace(enemyTurnPhase+1, "The enemy did " + damage + " damage");
            setCurrentDialogue(combatDialogue.get(enemyTurnPhase+1));
        }
        notifySubscribers();
    }

    /**
     * when player or enemy uses magic,
     * then subtract cost of spell from mana stat
     * damage = character intelligence + random value between -3 and  8
     * Character must have enough mana to cast a spell (costPerSpell = 25)
     */
    public void usedMagic() {
        int extraDamage = (int) (Math.random() * (8-(-3)) + -3);
        if(playerTurn && player.characterStats.getMana() >= costPerSpell){
            //player does damage
            int newHealth = enemy.characterStats.getHealth() - player.characterStats.getInt() - extraDamage;
            enemy.characterStats.setHealth(newHealth);

            // subtract magic points from player
            player.characterStats.setMana(player.characterStats.getMana()-costPerSpell);

            combatDialogue.replace(phase+1, "The player used a spell and did " + (player.characterStats.getInt() + extraDamage) + " damage");
            setCurrentDialogue(combatDialogue.get(phase));
            playerTurn=false;

        }
        if(!playerTurn && enemy.characterStats.getMana() >= costPerSpell){
            //enemy does damage
            int newHealth = player.characterStats.getHealth() - enemy.characterStats.getInt() - extraDamage;
            player.characterStats.setHealth(newHealth);

            // subtract magic points from enemy
            enemy.characterStats.setMana(enemy.characterStats.getMana()-costPerSpell);

            combatDialogue.replace(enemyTurnPhase+1, "The enemy used a spell and did " + (enemy.characterStats.getInt() + extraDamage) + " damage");
            setCurrentDialogue(combatDialogue.get(enemyTurnPhase+1));
        }
        notifySubscribers();
    }

    public void heal(){
        if(playerTurn && player.characterStats.getMana() >= costPerSpell){
            int extraHealAmount = r.nextInt((player.characterStats.getCon()/2));
            int healAmount = player.characterStats.getWis() + extraHealAmount;
            int newHealth = player.characterStats.getHealth() + healAmount;
            player.characterStats.setMana(player.characterStats.getMana()-costPerSpell);


            if(newHealth > player.characterStats.getMaxHealth()){
                player.characterStats.setHealth(player.characterStats.getMaxHealth());
            }else{
                player.characterStats.setHealth(player.characterStats.getHealth() + healAmount);
            }
            combatDialogue.replace(phase+1, "The player used a spell and healed " + (player.characterStats.getWis() + extraHealAmount) + " health");
            setCurrentDialogue(combatDialogue.get(phase));
        }
        if(!playerTurn && enemy.characterStats.getMana() >= costPerSpell){
            int extraHealAmount = r.nextInt((enemy.characterStats.getCon()/2));
            int healAmount = enemy.characterStats.getWis() + extraHealAmount;
            int newHealth = enemy.characterStats.getHealth() + healAmount;
            enemy.characterStats.setMana(enemy.characterStats.getMana()-costPerSpell);

            if(newHealth > enemy.characterStats.getMaxHealth()){
                enemy.characterStats.setHealth(enemy.characterStats.getHealth() + healAmount);
            }else{
                enemy.characterStats.setHealth(enemy.characterStats.getMaxHealth());
            }
            combatDialogue.replace(phase+1, "The enemy used a spell and healed " + (enemy.characterStats.getWis() + extraHealAmount) + " health");
            setCurrentDialogue(combatDialogue.get(phase));
        }
        notifySubscribers();
    }

    /**
     * called when player wins
     * adds to player exp and levels up player (increments player stats)
     *
     * TODO: test different formulas to find better balancing
     * player exp gain formula = 2^(enemy level)
     */
    public void expGain(){
        //add exp to player, and if player has enough to level up, then increment player level
        player.characterStats.addExp(enemy.characterStats.getCharacterLevel());

        //if player has enough exp to level up
        if (player.characterStats.getExp() >= player.characterStats.getMaxExp()){
            player.characterStats.levelUp();
        }
        notifySubscribers();
    }

    /**
     * go to next phase of battle
     */
    public void nextPhase() {
        //TODO this method can be optimized better. Also, the view handles end of combat, so this class does not need to
        //If player or enemy are not dead and both characters have attacked, then go back to phase 1
        if(phase >= 4){
            phase = 0;
        }

        //who's turn is it?
        if(phase == playerTurnPhase-1){
            playerTurn = true;
        }else if(phase == enemyTurnPhase){
            playerTurn = false;
            enemyPhase();
        }else{  // no ones turn
            playerTurn = false;
        }

        phase += 1;
        setCurrentDialogue(combatDialogue.get(phase));
        notifySubscribers();
    }

    /**
     * use dexterity stat to find who goes first
     * if player dex is higher, then player will go first
     */
    public void whoGoesFirst(){
        if(player.characterStats.getDex() > enemy.characterStats.getDex()){   //player's turn
            playerTurn = true;
        }else if (player.characterStats.getDex() < enemy.characterStats.getDex()){    //enemies turn
            playerTurn = false;
        }else{  // playerDex == enemyDex, so roll dice
            int decision = (int) (Math.random() * 101 + 1);

            playerTurn = decision >= 50;
        }
        setCombatDialogue();
        notifySubscribers();
    }

    /**
     * This method sets combat dialogue in its proper order
     */
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
     * use character generator to create an enemy for battle
     * Create an enemy within +2 or -2 of player level
     * return: enemy Character
     */
    public Character createEnemy(){
        Character c = new Character();
        int maxEnemyLevel = player.characterStats.getCharacterLevel()+2;
        int minEnemyLevel = player.characterStats.getCharacterLevel()-2;
        if(minEnemyLevel <= 0){
            minEnemyLevel = 1;
        }

        int enemyLevel = minEnemyLevel + r.nextInt(maxEnemyLevel - minEnemyLevel + 1);
        for(int i = 1; i < enemyLevel; i++){
            c.characterStats.levelUp();
        }
        if(enemyLevel <= 2){
            c.characterStats.setStr(3);
        }else{
            c.characterStats.setStr(c.characterStats.getStr()-6);
        }
        if(enemyLevel <= 2){
            c.characterStats.setInt(4);
        }else{
            c.characterStats.setInt(c.characterStats.getInt()-7);
        }
        return c;
    }

    /**
     * sets the character level for the boss and sets appropriate attributes so the fight is consistent
     * @param bossLevel: set to 20 for now
     */
    private void setBoss(int bossLevel){
        for(int i = 1; i < bossLevel; i++){
            boss.characterStats.levelUp();
        }
        boss.characterStats.setStr(115);
        boss.characterStats.setInt(110);
        boss.characterStats.setCon(105);
        boss.setName("Megasaurus Rex");
    }

    /**
     * enemies turn
     * if intelligence is higher than strength, then enemy will use magic until they run out of mana
     */
    public void enemyPhase() {
        if(enemy.characterStats.getInt() >= enemy.characterStats.getStr() && enemy.characterStats.getMana() >= costPerSpell){
            usedMagic();
        }else{
            attack();
        }
    }

    /**
     * end combat if random number coin toss is in players favor
     */
    public void runAway(){
        int check = r.nextInt(101);
        if(check > 51){
            runAway = true;
        }else{
            combatDialogue.replace(playerTurnPhase+1, "The player failed to run away!");
            nextPhase();
        }
        notifySubscribers();
    }

    /**
     * check for end combat conditions, if conditions are true, then return true
     * conditions that result in true:
     * Player ran away
     * player health below zero
     * enemy Health below zero
     */
    public boolean endCombatChecks(){
        boolean end = false;
        if(player.characterStats.getHealth() <= 0){
            end = true;
        }else if(enemy.characterStats.getHealth() <= 0){
            expGain();
            end = true;
        }else if(runAway){
            end = true;
        }

        return end;
    }

    /**
     * if the user selects yes, to retry combat
     * this method will completely restart the same combat scenario combat
     */
    public void restCombat() {
        player.characterStats.setMana(player.characterStats.getMaxMana());
        player.characterStats.setHealth(player.characterStats.getMaxHealth());
        enemy.characterStats.setMana(enemy.characterStats.getMaxMana());
        enemy.characterStats.setHealth(enemy.characterStats.getMaxHealth());
        combatDialogue.clear();
        playerTurn = false;
        runAway = false;
        playerTurnPhase = 0;
        enemyTurnPhase = 0;
        phase = 0;
        reset = true;

        CombatScenario newCombatScenario = new CombatScenario(player, enemy);
        setCombatScenario(newCombatScenario);
        notifySubscribers();
    }

    public String getCurrentDialogue(){
        return currentDialogue;
    }

    public void setCurrentDialogue(String dialogue){
        currentDialogue = dialogue;
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
        }else{
            result = -1;
        }
        if(result != 1 && model.player.characterStats.getDex() > model.enemy.characterStats.getDex()){
            System.out.println("whoGoesFirst() test #1 failed! expected = playerTurn, result = enemyTurn");
        }
        if(result != 0 && model.player.characterStats.getDex() < model.enemy.characterStats.getDex()){
            System.out.println("whoGoesFirst() test #1 failed! expected = enemyTurn, result = PlayerTurn");
        }
        if(result != -1 && model.player.characterStats.getDex() == model.enemy.characterStats.getDex()){
            System.out.println("whoGoesFirst() test #1 failed! expected a result of -1, but result = " + result);
        }

        //enemy and player damage test
        //test each combat phase
        //This variable is a safety net, I would occasionally get an infinite loop. I think I fixed it, but just in case...
        int count = 0;
        model.phase = 0;
        while(!model.endCombatChecks()){
            if(count == 20){
                System.out.println("break!!!!!!!!!!!!!!!!!!!!");
                break;
            }
            if(model.playerTurn){
                int enemyTotalHealth = model.enemy.characterStats.getHealth();
                model.attack();
                model.nextPhase();
                int enemyHealth = model.enemy.characterStats.getHealth();
                int playerDamage = enemyTotalHealth - enemyHealth;
                int totalPlayerDamage = model.player.characterStats.getStr()+5;
                if(model.player.characterStats.getStr() > playerDamage || model.player.characterStats.getStr()+5 < playerDamage){
                    System.out.println("damage test failed!");
                    System.out.println("It is the player's turn. The enemies total health was " + enemyTotalHealth + ". The enemies health is now " + enemyHealth);
                    System.out.println("total damage was " + playerDamage + ". Should have been between " + model.player.characterStats.getStr() + "-" + totalPlayerDamage);
                }
            }else if(model.enemyTurnPhase == model.phase){
                int playerTotalHealth = model.player.characterStats.getHealth();
                model.attack();
                model.nextPhase();
                int playerHealth = model.player.characterStats.getHealth();
                int enemyDamage = playerTotalHealth - playerHealth;
                int totalEnemyDamage = model.enemy.characterStats.getStr()+5;
                if(model.enemy.characterStats.getStr() > enemyDamage || model.enemy.characterStats.getStr()+5 < enemyDamage){
                    System.out.println("damage test failed!");
                    System.out.println("It is the enemies turn. The player's total health was " + playerTotalHealth + ". The player's health is now " + playerHealth);
                    System.out.println("total damage was " + enemyDamage + ". Should have been between " + model.enemy.characterStats.getStr() + "-" + totalEnemyDamage);
                }

            }else{
                model.nextPhase();
            }
            count ++;
        }

        model.setCombatDialogue();

        //enemyPhase() test #1
        int totalEnemyMana = model.enemy.characterStats.getMana();
        model.enemyPhase();
        if(totalEnemyMana == model.enemy.characterStats.getMana() && model.enemy.characterStats.getInt() >= model.enemy.characterStats.getStr() && model.enemy.characterStats.getMana() >= model.costPerSpell){
            System.out.println("enemy chose attack when they should have chosen magic");
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
