package sample;

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    protected Controller controller;
    protected Dimension2D dim;

    protected CombatModel combatModel;
    protected CombatView combatView;

    protected gameModel g;
    protected gameView gView;

    protected CharacterGeneratorView charView;
    protected CharacterGenerator charModel;

    protected WelcomeView welcomeView;

    protected int canHeight;
    protected int canWidth;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        canHeight = (int)(screenBounds.getHeight()/1.5);
        canWidth = (int)(screenBounds.getWidth()/1.5);

        controller = new Controller();
        combatModel = new CombatModel();
        combatView = new CombatView();
        g = new gameModel();
        gView = new gameView(800,800);
        charView = new CharacterGeneratorView();
        charModel = new CharacterGenerator();
        welcomeView = new WelcomeView();

        //enable this line if you are running the traversal mechanic
        g.startThread();
        g.addSubscriber(gView);

        gView.setController(controller);

        combatView.setModel(combatModel);
        controller.setModels(combatModel, g, charModel);

        combatView.setController(controller);

        charView.setController(controller);
        charView.setModel(charModel);

        combatModel.addSubscriber(combatView);
        charModel.addSubscriber(charView);

        welcomeView.setController(controller);


        //NOTE: This is for testing the combatView, delete it later
        combatModel.setCombatScenario(new CombatScenario(new Character(), new Character()));


        HBox root = new HBox();

        // Transitioning works from the welcome page through to the battle. For testing input
        // any of these scenes into primaryStage.setScene( "HERE" )
        Scene sceneWelcome = new Scene(welcomeView, 800, 800);
        Scene sceneCharGen = new Scene(charView, 800, 800);
        Scene sceneCombat = new Scene(combatView, 800, 800);
        Scene sceneTraversal = new Scene(gView, 800, 800);
        sceneTraversal.setFill(Color.BLACK);
        sceneTraversal.setOnKeyPressed(controller::handleKeys);


        primaryStage.setTitle("RPG");
        primaryStage.setScene(sceneWelcome);
        primaryStage.show();


    }


    public static void main(String[] args) { launch(args); }
}
