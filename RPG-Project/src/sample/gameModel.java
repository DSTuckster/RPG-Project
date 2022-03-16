package sample;

import java.util.ArrayList;

public class gameModel implements Runnable{
    protected Thread thread;
    private boolean running;
    ArrayList<Entity> entities = new ArrayList<>();
    protected final int FPS = 60;


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
        entities.add(new Player());

        double delta =0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();

            delta+= (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >=1) {
                update();
                delta--;

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
            for(Entity e : entities) {
                g.modelChanged();
            }

        }

    }
    public void closeThread(){
        /**closes the thread when window is closed*/
        running=false;
    }

    public static void main(String args[]) {

        //test 1 checks to see if thread closes and starts properly
        gameModel g = new gameModel();
        g.startThread();



        if (!g.thread.isAlive()) {
            System.out.println("error in closeThread function expected thread to be dead but is not");
        }

        //test 2 checks to see if all player images load correctly




    }
}
