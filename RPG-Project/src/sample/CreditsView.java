package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

public class CreditsView extends StackPane {

    Canvas myCanvas;
    GraphicsContext gc;

    public CreditsView(int width, int height) {
        myCanvas = new Canvas(width, height);
        gc = myCanvas.getGraphicsContext2D();
        this.setStyle("-fx-background-color: black");

        // Used as a 'finishing position' for the text which travels down-screen
        int endpoint = height/2 + 25;

        // Collection of names and text!!
        // Each chunk of code defines a piece of text which floats down the screen
        Text user = new Text(0, -4800, "And you!\nThanks for playing!!");
        user.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        user.setFill(Color.WHITE);
        TranslateTransition userTrans = new TranslateTransition(Duration.seconds(105), user);
        userTrans.setFromX(0);
        userTrans.setFromY(user.getY());
        userTrans.setToY(0);
        userTrans.setCycleCount(1);
        userTrans.play();

        // Aidan's text
        Text aiw = new Text(0, -4000, "Aidan Waring");
        aiw.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        aiw.setFill(Color.WHITE);
        TranslateTransition aiwTrans = new TranslateTransition(Duration.seconds(100), aiw);
        aiwTrans.setFromX(0);
        aiwTrans.setFromY(aiw.getY());
        aiwTrans.setToY(endpoint);
        aiwTrans.setCycleCount(1);
        aiwTrans.play();

        // Bob's text
        Text shx = new Text(0, -3200, "Shaobo Xu");
        shx.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        shx.setFill(Color.WHITE);
        TranslateTransition shxTrans = new TranslateTransition(Duration.seconds(83), shx);
        shxTrans.setFromX(0);
        shxTrans.setFromY(shx.getY());
        shxTrans.setToY(endpoint);
        shxTrans.setCycleCount(1);
        shxTrans.play();

        // Dylan's text
        Text dst = new Text(0, -2400, "Dylan Tucker");
        dst.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        dst.setFill(Color.WHITE);
        TranslateTransition dstTrans = new TranslateTransition(Duration.seconds(65), dst);
        dstTrans.setFromX(0);
        dstTrans.setFromY(dst.getY());
        dstTrans.setToY(endpoint);
        dstTrans.setCycleCount(1);
        dstTrans.play();

        // Paige's text
        Text pmw = new Text(0, -1600, "Paige Wiebe");
        pmw.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        pmw.setFill(Color.WHITE);
        TranslateTransition pmwTrans = new TranslateTransition(Duration.seconds(50), pmw);
        pmwTrans.setFromX(0);
        pmwTrans.setFromY(pmw.getY());
        pmwTrans.setToY(endpoint);
        pmwTrans.setCycleCount(1);
        pmwTrans.play();

        // Derek's text
        Text dms = new Text(0, -800, "Derek Steeg");
        dms.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        dms.setFill(Color.WHITE);
        TranslateTransition dmsTrans = new TranslateTransition(Duration.seconds(35), dms);
        dmsTrans.setFromX(0);
        dmsTrans.setFromY(dms.getY());
        dmsTrans.setToY(endpoint);
        dmsTrans.setCycleCount(1);
        dmsTrans.play();

        // Credits text
        Text credits = new Text(0, 0, "Credits");
        credits.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        credits.setFill(Color.WHITE);
        TranslateTransition creditsTrans = new TranslateTransition(Duration.seconds(20), credits);
        creditsTrans.setFromX(0);
        creditsTrans.setFromY(credits.getY());
        creditsTrans.setToY(endpoint);
        creditsTrans.setCycleCount(1);
        creditsTrans.play();

        this.getChildren().addAll(myCanvas, credits, dms, pmw, dst, shx, aiw, user);
    }

}
