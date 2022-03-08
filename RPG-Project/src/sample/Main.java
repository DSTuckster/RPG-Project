package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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

    protected int canHeight;
    protected int canWidth;

    @Override
    public void start(Stage primaryStage) throws Exception{
        canHeight=800;
        canWidth=800;
        controller = new Controller();
        combatModel = new CombatModel();
        combatView = new CombatView();
        g = new gameModel();
        gView = new gameView(canHeight,canWidth);
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


        //NOTE: This is for testing the combatView, delete it later
        combatModel.setCombatScenario(new CombatScenario(new Character(), new Character()));


        HBox root = new HBox();


        Scene sceneWelcome = new Scene(welcomeView, 800, 800);
        Scene sceneCharGen = new Scene(charView, 800, 800);
        Scene sceneCombat = new Scene(combatView, 800, 800);
        Scene sceneTraversal = new Scene(gView, 800, 800);
        sceneTraversal.setFill(Color.BLACK);
        sceneTraversal.setOnKeyPressed(controller::handleKeys);

        // Will add ways to transition through different scenes later
        // For now add what scene you want to see in primaryStage.setScene( ENTER HERE ) from
        // different scenes created above ^^^^^

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(sceneTraversal);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
