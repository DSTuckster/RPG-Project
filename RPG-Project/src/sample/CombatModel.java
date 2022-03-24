package sample;



import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;


public class CombatModel {

    ArrayList<CombatSubscriber> subs = new ArrayList<>();

    protected Character player;
    protected Character enemy;

    protected CombatScenario scenario;
    protected ArrayList<CombatScenario> allScenarios;

    public int phase;
    public int playerTurnPhase;
    public int enemyTurnPhase;
    public boolean playerTurn;

    protected int costPerSpell;

    protected boolean runAway;
    protected boolean bossFight;

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

        allScenarios = new ArrayList<>();
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
            bossFight = true;
        }else{
            bossFight = false;
        }

        combatDialogue.put(0 ,"A wild " + enemy.getName() + " has appeared!");
        setCurrentDialogue(combatDialogue.get(phase));

        whoGoesFirst();
        notifySubscribers();
    }
    public ArrayList<CombatScenario> getCombatScenarios(){
        return allScenarios;
    }
    public void setAllCombatScenarios(Character p){
        Character bubbles = createEnemy();
        Character blossom = createEnemy();
        Character buttercup = createEnemy();
        bubbles.setName("Bubbles");
        blossom.setName("Blossom");
        buttercup.setName("Buttercup");

        CombatScenario scene1 = new CombatScenario(p, bubbles);
        CombatScenario scene2 = new CombatScenario(p, blossom);
        CombatScenario scene3 = new CombatScenario(p, buttercup);
        CombatScenario scene4 = new CombatScenario(p);

        allScenarios.add(scene1);
        allScenarios.add(scene2);
        allScenarios.add(scene3);
        allScenarios.add(scene4);
    }

    /**
     * subtract damage from character health
     * damage = character strength + random value between 1 and 5
     */
    public void attack(Character p, Character e) {
        int extraDamage = (int) (Math.random() * 5 + 1);

        int damage = p.characterStats.getStr() + extraDamage;
        int newHealth = e.characterStats.getHealth() - damage;
        e.characterStats.setHealth(newHealth);

        combatDialogue.replace(phase+1, p.getName() + " did " + damage + " damage");
        setCurrentDialogue(combatDialogue.get(phase));
        notifySubscribers();
    }

    /**
     * when player or enemy uses magic,
     * then subtract cost of spell from mana stat
     * damage = character intelligence + random value between -3 and  8
     * Character must have enough mana to cast a spell (costPerSpell = 25)
     */
    public void usedMagic(Character p, Character e) {
        int extraDamage = (int) (Math.random() * (8-(-3)) + -3);
        if(p.characterStats.getMana() >= costPerSpell){
            //player does damage
            int newHealth = e.characterStats.getHealth() - p.characterStats.getInt() - extraDamage;
            e.characterStats.setHealth(newHealth);

            // subtract magic points from player
            p.characterStats.setMana(p.characterStats.getMana()-costPerSpell);

            combatDialogue.replace(phase+1, p.getName() + " used a spell and did " + (p.characterStats.getInt() + extraDamage) + " damage");
            setCurrentDialogue(combatDialogue.get(phase));
        }
        notifySubscribers();
    }

    public void heal(Character p){
        if(p.characterStats.getMana() >= costPerSpell){
            int extraHealAmount = r.nextInt((p.characterStats.getCon()/2));
            int healAmount = p.characterStats.getWis() + extraHealAmount;
            int newHealth = p.characterStats.getHealth() + healAmount;
            p.characterStats.setMana(p.characterStats.getMana()-costPerSpell);


            if(newHealth > p.characterStats.getMaxHealth()){
                p.characterStats.setHealth(p.characterStats.getMaxHealth());
            }else{
                p.characterStats.setHealth(p.characterStats.getHealth() + healAmount);
            }
            combatDialogue.replace(phase+1, p.getName() + " used a spell and healed " + (p.characterStats.getWis() + extraHealAmount) + " health");
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

        endCombatChecks();
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
            combatDialogue.put(1, "It is " + player.getName() + "'s turn!");
            combatDialogue.put(2, player.getName() + " did " + player.characterStats.getStr() + " damage");
            combatDialogue.put(3, "It is " + enemy.getName() + "'s turn!");
            combatDialogue.put(4, "The " + enemy.getName() + " did " + enemy.characterStats.getStr() + " damage");
        }else{
            playerTurnPhase = 3;
            enemyTurnPhase = 1;
            combatDialogue.put(1, "It is " + enemy.getName() + "'s turn!");
            combatDialogue.put(2, enemy.getName() + " did " + enemy.characterStats.getStr() + " damage");
            combatDialogue.put(3, "It is " + player.getName() + "'s turn!");
            combatDialogue.put(4, player.getName() + " did " + player.characterStats.getStr() + " damage");
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

        int enemyLevel = minEnemyLevel + r.nextInt(maxEnemyLevel - minEnemyLevel + 1);
        if(enemyLevel <= 0){
            enemyLevel = 1;
        }
        for(int i = 1; i < enemyLevel; i++){
            c.characterStats.levelUp();
        }
        if(enemyLevel <= 2){
            //c.characterStats.setStr(3);
        }else{
            c.characterStats.setStr(c.characterStats.getStr()-6);
        }
        if(enemyLevel <= 2){
            //c.characterStats.setInt(4);
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
            usedMagic(enemy, player);
        }else{
            attack(enemy, player);
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
            combatDialogue.replace(playerTurnPhase+1, player.getName() + " failed to run away!");
            nextPhase();
        }
        notifySubscribers();
    }

    /**
     * check if enemy health <= 0, if true give player XP
     */
    public void endCombatChecks(){
        if(enemy.characterStats.getHealth() <= 0){
            expGain();
        }
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
        if(enemy == boss){
            newCombatScenario = new CombatScenario(player);
        }
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
        while(model.player.characterStats.getHealth() <= 0 && model.enemy.characterStats.getHealth() <= 0){
            if(count == 20){
                System.out.println("break!!!!!!!!!!!!!!!!!!!!");
                break;
            }
            if(model.playerTurn){
                int enemyTotalHealth = model.enemy.characterStats.getHealth();
                model.attack(model.player, model.enemy);
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
                model.attack(model.enemy, model.player);
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
        model.attack(model.player, model.enemy);
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
