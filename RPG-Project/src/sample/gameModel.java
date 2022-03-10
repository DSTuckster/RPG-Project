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

    double drawInterval = (double)1000000000/FPS; //How often the thread needs to update in nanoseconds
    ArrayList<GameSubscriber> subs = new ArrayList<>();


    public void startThread() {
        /** Creates and starts the game thread in such a way that it will close when the javaFX window is closed*/
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        running = true;
    }
    @Override
    public void run() {
        /**Core game loop function, Creates a player object and runs the loop and the update function every 1/FPS seconds
         * tries to let the thread sleep if there is remaining time as to not overload the thread optimizing performance*/
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
        /**Important details that need to be updated every frame go here*/
        notifySubscribers();

    }

    public void addSubscriber(GameSubscriber g){
        /**adds a subscriber to the arraylist Subs that will be updated every frame from the update function*/
        subs.add(g);

    }

    public void notifySubscribers(){
        /**updates all gameSubscribers in this case, the view so that they can draw with the new locations and required images*/
        for (GameSubscriber g : subs) {
            g.modelChanged(player.getX(),player.getY(),player.getPlayerImage());
        }

    }
    public void closeThread(){
        /**closes the thread when window is closed*/
        running=false;
    }
}
