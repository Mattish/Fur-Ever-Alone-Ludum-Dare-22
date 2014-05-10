import processing.core.PApplet;

import javax.sound.sampled.*;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Mattish_2
 * Date: 18/12/11
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class Sound {
    Clip teleport;
    Clip bg;
    AudioInputStream ais;
    AudioInputStream ais2;
    PApplet p;
    URL url;
    URL url2;

    public Sound(PApplet pApplet) {
        p = pApplet;
        LoadSounds();
    }

    public void LoadSounds() {

        url2 = getClass().getClassLoader().getResource("BG.wav");

        try {
            ais2 = AudioSystem.getAudioInputStream(url2);
            AudioFormat af = ais2.getFormat();
            DataLine.Info linfo2 = new DataLine.Info(Clip.class, af);
            Line line2 = AudioSystem.getLine(linfo2);
            bg = (Clip) line2;
            bg.open(ais2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void StartBG() {
        try {
            if (!bg.isRunning()) {
                bg.stop();
            }
            bg.loop(5000);
            bg.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
