package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import javafx.stage.Stage;

public class Main extends Application {

    protected Controller controller;

    protected CombatModel combatModel;
    protected CombatView combatView;

    protected gameModel g;
    protected gameView gView;

    protected CharacterGeneratorView charView;
    protected CharacterGenerator charModel;

    protected WelcomeView welcomeView;


    @Override
    public void start(Stage primaryStage) throws Exception{


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        controller = new Controller();
        combatModel = new CombatModel();
        combatView = new CombatView();
        g = new gameModel();
        gView = new gameView(1280,720);
        charView = new CharacterGeneratorView();
        charModel = new CharacterGenerator();
        welcomeView = new WelcomeView();

        //enable this line if you are running the traversal mechanic
        g.startThread();
        g.addSubscriber(gView);

        gView.setController(controller);

        //NOTE: This is for testing the combatView, delete it later
        combatModel.setCombatScenario(new CombatScenario(new Character(), new Character()));


        combatView.setModel(combatModel);
        controller.setModels(combatModel, g, charModel);

        combatView.setController(controller);

        charView.setController(controller);
        charView.setModel(charModel);

        combatModel.addSubscriber(combatView);
        charModel.addSubscriber(charView);

        welcomeView.setController(controller);



        HBox root = new HBox();

        // Transitioning works from the welcome page through to the battle. For testing input
        // any of these scenes into primaryStage.setScene( "HERE" )
        Scene sceneWelcome = new Scene(welcomeView, 1280, 720);
        Scene sceneCharGen = new Scene(charView, 1280, 720);
        Scene sceneCombat = new Scene(combatView, 1280, 720);
        Scene sceneTraversal = new Scene(gView, 1280, 720);
        sceneTraversal.setFill(Color.BLACK);
        sceneTraversal.setOnKeyPressed(controller::handleKeys);


        primaryStage.setTitle("RPG");
        primaryStage.setScene(sceneWelcome);
        primaryStage.show();


    }


    public static void main(String[] args) { launch(args); }
}
