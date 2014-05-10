import TentacleNoises.Game_Component;
import TentacleNoises.Inputs;
import TentacleNoises.Thing;
import processing.core.PApplet;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Mattish_2
 * Date: 17/12/11
 * Time: 03:11
 * To change this template use File | Settings | File Templates.
 */
public class Game_MainMenu extends Game_Component {
    Thing[] menu;
    boolean loaded;
    Effect start;
    boolean DoIt;
    int fade;

    public Game_MainMenu(PApplet p, String n) {
        super(p, n);
        loaded = false;
        DoIt = false;
        fade = 0;
        start = new Effect(p, "DeathEffect", 300, 360, p.width / 2, p.height / 2, 1, 1);
        menu = new Thing[3];
        menu[0] = new Thing(this.p, "Begin", (p.width / 2) - 75, p.height / 2, 150, 75, "Begin", 20, true, 0);
        menu[0].AniClick(40);
    }

    public void Draw() {
        FontChange();
        start.DrawEffect(DoIt);
        for (Thing thing : menu) {  // UPDATE ALL THE THINGS
            if (thing != null) {
                thing.Update();
                if (thing.IsClicked()) {
                    if (Inputs.Clicked()) {         //CLICKING ON THING
                        if (thing.GetName() == "Begin") {
                            ChangeTo("Level", 360);
                            if (!Inputs.ChangePlease) {
                                DoIt = true;
                                Inputs.ChangeLevelTo = 1;
                                Inputs.ChangePlease = true;
                            }
                        }
                    }
                }
            }
        }


        for (Thing thing : menu) {
            if (thing != null)
                thing.Draw();               // DRAW ALL THE THINGS
        }
        if (DoIt) {
            p.fill(0,0,0, fade);
            p.rect(0, 0, p.width, p.height);
            fade++;
        }
    }

    private void FontChange() {
        if (!loaded) {

            p.textFont(p.loadFont("MeiryoUI-Bold-48.vlw"));
            loaded = true;
        }
    }
}
