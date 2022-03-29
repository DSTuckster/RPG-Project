package sample;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    protected File file;
    protected Clip c;

    /**
     * initialize the music file
     * @param scene the music file name
     */
    public Music(String scene) {
        scene=scene+".wav";
        file = new File(scene);
        }

    /**
     * play the music file you want
     * @param file the music file you want to play
     */
    protected void playMusic(File file) {
        if (file.exists()) {
            try {
                AudioInputStream audio = AudioSystem.getAudioInputStream(file);
                this.c = AudioSystem.getClip();
                this.c.open(audio);
                this.c.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) {}
        }
    }
    
    /**
     * stop the music file you are playing
     */
    protected void stopMusic(){
        this.c.stop();
    }
}
