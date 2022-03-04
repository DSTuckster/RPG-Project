package sample;

import java.util.ArrayList;

public class gameModel implements Runnable{
    protected gameView view;
    Thread thread;
    int playerX=100,playerY=100,playerSpeed=4;
    protected final int FPS = 60;
    double drawInterval = 1000000000/FPS;

    ArrayList<GameSubscriber> subs = new ArrayList<>();



    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (thread!=null) {
            update();


            try {
                double remainingTime = (nextDrawTime - System.nanoTime())/1000000;
                if(remainingTime <0) {
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;

            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerY() {
        return playerY;
    }
    public int getPlayerSpeed(){
        return playerSpeed;
    }

    public void update() {
        notifySubscribers();

    }

    public void addSubscriber(GameSubscriber g){
        subs.add(g);

    }

    public void notifySubscribers(){
        for(int i=0;i<subs.size();i++) {
            GameSubscriber g = subs.get(i);
            g.modelChanged(getPlayerX(),getPlayerY());
        }

    }
}
