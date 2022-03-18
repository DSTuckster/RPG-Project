package sample;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    protected String path=".wav";
    protected File file = new File(path);
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
            } catch (IOException e) {

            } catch (UnsupportedAudioFileException e) {

            } catch (LineUnavailableException e) {

            }
        }
    }
    protected void stopMusic(){
        this.c.stop();
    }
}
