package TentacleNoises;

import processing.core.PApplet;

import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Matthew Dale
 * Date: 05/12/11
 * Time: 23:14
 * Email: ma001md@gold.ac.uk
 */
public class Game_Component {
    public final int STOPPED = 0;
    public final int RUNNING = 1;
    public final int STOPPING = 2;
    public final int STARTING = 3;
    public int CHANGINGCOUNTDOWN;
    public String CHANGETO;
    public final String NAME;
    public int STATE;
    public PApplet p;

    public Game_Component(PApplet p, String n) {
        NAME = n;
        this.p = p;
        CHANGINGCOUNTDOWN = -1;
        CHANGETO = "";
        STATE = STOPPED;
    }

    public void Draw() {

    }

    public void ChangeTo(String s, int n) {
        if (STATE != STOPPING) {
            CHANGETO = s;
            Stop(n);
        }
    }

    public void ChangeLevel(int n) {
    }

    public void Start(int n) {
        STATE = STARTING;
        CHANGINGCOUNTDOWN = n;
    }

    public void Stop(int n) {
        STATE = STOPPING;
        CHANGINGCOUNTDOWN = n;
    }

    public void Reset() {

    }

    public void Update() {
        if (STATE == RUNNING) {
            Draw();
        } else if (STATE == STOPPING) {
            if (CHANGINGCOUNTDOWN < 0) {
                STATE = STOPPED;
            }
            CHANGINGCOUNTDOWN = CHANGINGCOUNTDOWN - 1;
            Draw();
        } else if (STATE == STARTING) {
            if (CHANGINGCOUNTDOWN < 0) {
                STATE = RUNNING;
            }
            CHANGINGCOUNTDOWN = CHANGINGCOUNTDOWN - 1;
        } else if (STATE == STOPPED) {

            //DO NOTHING
        }

        if (STATE != STOPPING) { //STOP MOUSE WHEN STOPPING
            Inputs.Enable();
        } else {
            Inputs.Disable();
        }
    }

    public String GetName() {
        return NAME;
    }
}


