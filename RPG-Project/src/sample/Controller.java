package sample;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Controller {

    protected CombatModel combatModel;

    //sets the combat model
    public void setModels(CombatModel cm){
        combatModel = cm;
    }

    public void nextPhase(MouseEvent e) {
        try {
            combatModel.nextPhase();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void handleAttack(ActionEvent event){
        combatModel.attack();
    }

    public void handleRun(){
        combatModel.runAway();
    }

    public void handleMagic(){
        combatModel.usedMagic();
    }
}
