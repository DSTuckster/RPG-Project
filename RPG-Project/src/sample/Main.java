package sample;

import javafx.application.Application;

import javafx.scene.Scene;
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
    protected CreditsModel creditsModel;

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
        creditsModel = new CreditsModel();


        gModel.addSubscriber(gView);
        gView.setController(controller);

        combatView.setModel(combatModel);
        combatView.setController(controller);
        combatModel.addSubscriber(combatView);
        charModel.addSubscriber(charView);

        controller.setModels(combatModel, gModel, charModel, creditsModel);

        charView.setController(controller);
        charView.setModel(charModel);

        welcomeView.setController(controller);

        creditsModel.addSubscriber(creditsView);

        // Scenes to switch through
        Scene sceneWelcome = new Scene(welcomeView, resWidth, resHeight);
        Scene sceneCharGen = new Scene(charView, resWidth, resHeight);
        Scene sceneCombat = new Scene(combatView, resWidth, resHeight);
        Scene sceneTraversal = new Scene(gView, resWidth, resHeight);
        Scene sceneCredits = new Scene(creditsView, resWidth, resHeight);

        sceneTraversal.setFill(Color.BLACK);
        sceneTraversal.setOnKeyPressed(controller::handleKeys);


        primaryStage.setTitle("RPG");
        primaryStage.setScene(sceneWelcome);
        primaryStage.show();

    }


    public static void main(String[] args) { launch(args); }
}
