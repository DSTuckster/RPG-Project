package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {


    private static final int resWidth =1296;
    private static final int resHeight=720;

    protected Controller controller;

    protected CombatModel combatModel;
    protected CombatView combatView;

    protected gameModel gModel;
    protected gameView gView;

    protected CharacterGeneratorView charView;
    protected CharacterGenerator charModel;

    protected CreditsView creditsView;

    protected WelcomeView welcomeView;


    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controller();
        combatModel = new CombatModel();
        combatView = new CombatView();
        gModel = new gameModel();
        gView = new gameView(resWidth,resHeight);
        charView = new CharacterGeneratorView();
        charModel = new CharacterGenerator();
        welcomeView = new WelcomeView();

        creditsView = new CreditsView(resWidth, resHeight);

        //enable this line if you are running the traversal mechanic
        gModel.addSubscriber(gView);

        gView.setController(controller);

        //NOTE: This is for testing the combatView, delete it later
        combatModel.setCombatScenario(new CombatScenario(new Character(), new Character()));


        combatView.setModel(combatModel);
        controller.setModels(combatModel, gModel, charModel);

        combatView.setController(controller);

        charView.setController(controller);
        charView.setModel(charModel);

        combatModel.addSubscriber(combatView);
        charModel.addSubscriber(charView);

        welcomeView.setController(controller);

        // Transitioning works from the welcome page through to the battle. For testing input
        // any of these scenes into primaryStage.setScene( "HERE" )
        Scene sceneWelcome = new Scene(welcomeView, resWidth, resHeight);
        Scene sceneCharGen = new Scene(charView, resWidth, resHeight);
        Scene sceneCombat = new Scene(combatView, resWidth, resHeight);
        Scene sceneTraversal = new Scene(gView, resWidth, resHeight);
        Scene sceneCredits = new Scene(creditsView, resWidth, resHeight);
        sceneTraversal.setFill(Color.BLACK);
        sceneTraversal.setOnKeyPressed(controller::handleKeys);


        primaryStage.setTitle("RPG");
        primaryStage.setScene(sceneCredits);
        primaryStage.show();


    }


    public static void main(String[] args) { launch(args); }
}
