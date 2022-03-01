package sample;

public class CombatTests {

    protected CombatModel model;

    public CombatTests(){

    }

    public void setCombatModel(CombatModel m){
        model = m;
    }

    public void runTests() throws InterruptedException {
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
