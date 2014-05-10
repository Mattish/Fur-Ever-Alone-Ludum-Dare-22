import TentacleNoises.Game_Component;
import TentacleNoises.Inputs;
import TentacleNoises.Thing;
import processing.core.PApplet;

/**
 * Created by IntelliJ IDEA.
 * User: Mattish_2
 * Date: 17/12/11
 * Time: 02:49
 * To change this template use File | Settings | File Templates.
 */
public class Game_Splash extends Game_Component {

    Thing title;
    int fade;
    int timecount;
    boolean derp;
    boolean loaded;

    public Game_Splash(PApplet p, String n) {
        super(p, n);
        fade = 255;
        derp = false;
        timecount = -1;
        STATE = RUNNING;
        title = new Thing(this.p, "SplashName", (p.width / 2) - 300, p.height / 2, 600, 400, "TentacleNoises presents", 32, false, 0);
    }

    public void Draw() {
        FontChange();
        title.Update();
        title.Draw();
        p.fill(0, 0, 0, fade);
        p.rect(0, 0, p.width, p.height);
        if (timecount < 100) {
            if (fade > 0) {
                fade -= 3;
            } else {
                timecount++;
            }
        } else {
            fade += 3;
            if (!derp) {
                ChangeTo("MainMenu", 90);
                derp = true;
            }
        }
    }

    private void FontChange() {
        if (!loaded) {
            Inputs.SetFont(p.loadFont("PurpleTentaclePD-72.vlw"));
            loaded = true;
        }
    }
}
