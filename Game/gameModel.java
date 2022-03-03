package sample;

public class gameModel implements Runnable{

    Thread thread;
    int playerX=100,playerY=100,playerSpeed=4;
    protected final int FPS = 60;
    double drawInterval = 1000000000/FPS;



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
        System.out.println(getPlayerX()+ " " +getPlayerY());

    }
}
