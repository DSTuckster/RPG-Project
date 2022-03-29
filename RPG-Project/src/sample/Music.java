package sample;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    protected File file;
    protected Clip c;
    public Music(String scene) {
        scene=scene+".wav";
        file = new File(scene);
        }

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
    protected void stopMusic(){
        this.c.stop();
    }
}
