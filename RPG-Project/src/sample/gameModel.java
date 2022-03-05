package sample;

import java.util.ArrayList;

public class gameModel implements Runnable{
    protected Thread thread;
    private boolean running;
    protected final int playerSpeed=4;
    protected final int FPS = 60;
    
    private int playerX=100,playerY=100;
    double drawInterval = (double)1000000000/FPS;

    ArrayList<GameSubscriber> subs = new ArrayList<>();

    public void startThread() {
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        running = true;
    }
    @Override
    public void run() {
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (running) {
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
        for (GameSubscriber g : subs) {
            g.modelChanged(getPlayerX(), getPlayerY());
        }

    }
    public void closeThread(){
        running=false;
    }
}
