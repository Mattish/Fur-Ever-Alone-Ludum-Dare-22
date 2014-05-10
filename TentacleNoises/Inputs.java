package TentacleNoises;

import processing.core.*;

import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Matthew Dale
 * Date: 05/12/11
 * Time: 23:14
 * Email: ma001md@gold.ac.uk
 */
public class Inputs {
    static int x;
    static int y;
    static boolean clicked;
    static boolean pressed;
    static boolean click;
    static int last;
    static PFont font;
    static PApplet p;
    static boolean enabled;
    public static boolean left;
    public static boolean up;
    public static boolean right;
    public static boolean down;
    public static boolean ChangePlease;
    public static int ChangeLevelTo;

    public Inputs(PApplet p) {
        this.enabled = true;
        this.click = false;
        this.p = p;
        this.ChangePlease = false;
        this.ChangeLevelTo = 1;
    }

    public static void SetUp(boolean b) {
        Inputs.up = b;
    }

    public static void SetLeft(boolean b) {
        Inputs.left = b;
    }

    public static void SetRight(boolean b) {
        Inputs.right = b;
    }

    public static void SetDown(boolean b) {
        Inputs.down = b;
    }

    public static void SetFont(PFont f) {
        Inputs.font = f;
    }

    public static void Disable() {
        enabled = false;
    }

    public static void Enable() {
        enabled = true;
    }

    public static void Update(MouseEvent e) {
        if (e != null) {
            int num = e.getID();

            // CLICK STUFF
            Inputs.SetClicked(false);
            if (num == 502 || num == 500) {
                if (Inputs.last != 500 && Inputs.last != 502) {
                    Inputs.SetClicked(true);
                }
            }
            //EXPLICIT CLICK CHANCE
            if (num == 503) {
                if (Inputs.last == 506 || Inputs.last == 501) {
                    Inputs.SetClicked(true);
                }
            }


            if (num == 501 || num == 506) {
                Inputs.SetPressed(true);
            } else {
                Inputs.SetPressed(false);
            }


            Inputs.SetX(e.getPoint().x);
            Inputs.SetY(e.getPoint().y);
            //Inputs STUFF END

            Inputs.last = num;
        }
    }

    public static void SetX(int x) {
        Inputs.x = x;
    }

    public static void SetY(int y) {
        Inputs.y = y;
    }

    public static void SetClicked(boolean k) {
        Inputs.clicked = k;
    }

    public static boolean Clicked() {
        if (enabled)
            return clicked;
        else
            return false;

    }

    public static int GetX() {
        return x;
    }

    public static int GetY() {
        return y;
    }

    public static void SetPressed(boolean k) {
        Inputs.pressed = k;
    }

    public static boolean Pressed() {
        if (enabled)
            return pressed;
        else
            return false;
    }
}


