package sample;

import javafx.application.Application;


public class Controller  {
    gameModel g;
    public void start() {
        g = new gameModel();
        g.startThread();
        Application.launch(gameView.class);



    }
   public void moveUp(){
            g.setPlayerY(g.getPlayerY()-g.getPlayerSpeed());
    }
    public void moveDown() {
        g.setPlayerY(g.getPlayerY() + g.getPlayerSpeed());
    }
    public void moveLeft() {
        g.setPlayerX(g.getPlayerX() - g.getPlayerSpeed());
    }
    public void moveRight() {
        g.setPlayerX(g.getPlayerX() + g.getPlayerSpeed());
    }




    }






