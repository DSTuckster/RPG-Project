package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

public class CreditsView extends StackPane implements CreditsSubs {

    private final Canvas myCanvas;
    private final GraphicsContext gc;

    protected TranslateTransition userTrans;
    protected TranslateTransition aiwTrans;
    protected TranslateTransition shxTrans;
    protected TranslateTransition dstTrans;
    protected TranslateTransition pmwTrans;
    protected TranslateTransition dmsTrans;
    protected TranslateTransition creditsTrans;
    private Controller controller;
    private CreditsModel model;


    public CreditsView(int width, int height) {
        myCanvas = new Canvas(width, height);
        gc = myCanvas.getGraphicsContext2D();
        this.setStyle("-fx-background-color: black");

        // Used as a 'finishing position' for the text which travels down-screen
        int endpoint = height/2 + 100;

        // Collection of names and text!!
        // Each chunk of code defines a piece of text which floats down the screen
        Text user = new Text(0, -4800, "And you!\nThanks for playing!!");
        user.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        user.setTextAlignment(TextAlignment.CENTER);
        user.setFill(Color.WHITE);
        userTrans = new TranslateTransition(Duration.seconds(105), user);
        userTrans.setFromX(0);
        userTrans.setFromY(user.getY());
        userTrans.setToY(0);
        userTrans.setCycleCount(1);

        // Aidan's text
        Text aiw = new Text(0, -4000, "Aidan Waring \n\nOverworld Traversal \nOverworld Design\nInteraction Logic");
        aiw.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        aiw.setTextAlignment(TextAlignment.CENTER);
        aiw.setFill(Color.WHITE);
        aiwTrans = new TranslateTransition(Duration.seconds(100), aiw);
        aiwTrans.setFromX(0);
        aiwTrans.setFromY(aiw.getY());
        aiwTrans.setToY(endpoint);
        aiwTrans.setCycleCount(1);

        // Bob's text
        Text shx = new Text(0, -3200, "Shaobo Xu \n\nData Management \nMusic Architecture");
        shx.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        shx.setTextAlignment(TextAlignment.CENTER);
        shx.setFill(Color.WHITE);
        shxTrans = new TranslateTransition(Duration.seconds(83), shx);
        shxTrans.setFromX(0);
        shxTrans.setFromY(shx.getY());
        shxTrans.setToY(endpoint);
        shxTrans.setCycleCount(1);

        // Dylan's text
        Text dst = new Text(0, -2400, "Dylan Tucker \n\nCombat Loop & Logic\nGameplay Direction");
        dst.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        dst.setTextAlignment(TextAlignment.CENTER);
        dst.setFill(Color.WHITE);
        dstTrans = new TranslateTransition(Duration.seconds(65), dst);
        dstTrans.setFromX(0);
        dstTrans.setFromY(dst.getY());
        dstTrans.setToY(endpoint);
        dstTrans.setCycleCount(1);

        // Paige's text
        Text pmw = new Text(0, -1600, "Paige Wiebe \n\nGUI Design \nCharacter Generation Architecture");
        pmw.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        pmw.setTextAlignment(TextAlignment.CENTER);
        pmw.setFill(Color.WHITE);
        pmwTrans = new TranslateTransition(Duration.seconds(50), pmw);
        pmwTrans.setFromX(0);
        pmwTrans.setFromY(pmw.getY());
        pmwTrans.setToY(endpoint);
        pmwTrans.setCycleCount(1);

        // Derek's text
        Text dms = new Text(0, -800, "Derek Steeg \n\nCreative Direction\nCharacter Generation Logic\n& Art");
        dms.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        dms.setTextAlignment(TextAlignment.CENTER);
        dms.setFill(Color.WHITE);
        dmsTrans = new TranslateTransition(Duration.seconds(35), dms);
        dmsTrans.setFromX(0);
        dmsTrans.setFromY(dms.getY());
        dmsTrans.setToY(endpoint);
        dmsTrans.setCycleCount(1);

        // Credits text
        Text credits = new Text(0, 0, "Credits");
        credits.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        credits.setTextAlignment(TextAlignment.LEFT);
        credits.setFill(Color.WHITE);
        creditsTrans = new TranslateTransition(Duration.seconds(20), credits);
        creditsTrans.setFromX(0);
        creditsTrans.setFromY(credits.getY());
        creditsTrans.setToY(endpoint);
        creditsTrans.setCycleCount(1);

        this.getChildren().addAll(myCanvas, credits, dms, pmw, dst, shx, aiw, user);
    }

    protected void play() {
        this.userTrans.play();
        this.aiwTrans.play();
        this.shxTrans.play();
        this.dstTrans.play();
        this.pmwTrans.play();
        this.dmsTrans.play();
        this.creditsTrans.play();
    }

    public void setController(Controller newController) {
        this.controller = newController;
    }

    public void setModel(CreditsModel newCreditsModel) {
        this.model = newCreditsModel;
    }

    @Override
    public void modelChanged() {
        this.play();
    }
}
