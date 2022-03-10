package sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class gameModel implements Runnable{
    protected Thread thread;
    private boolean running;
    protected Player player;
    protected final int FPS = 60;
    protected String up ="boy_up_1.png";
    protected String down = "boy_down_1.png";
    protected String left = "boy_left_1.png";
    protected String right = "boy_right_1.png";

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
        player = new Player();

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


    public void update() {
        notifySubscribers();

    }

    public void addSubscriber(GameSubscriber g){
        subs.add(g);

    }

    public void notifySubscribers(){
        for (GameSubscriber g : subs) {
            g.modelChanged(player.getX(),player.getY(),player.getPlayerImage());
        }

    }
    public void closeThread(){
        running=false;
    }
}
