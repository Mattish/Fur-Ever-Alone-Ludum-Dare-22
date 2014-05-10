import TentacleNoises.Thing;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by IntelliJ IDEA.
 * User: Mattish_2
 * Date: 17/12/11
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class Magic {
    PVector[] bits;
    float[] randoms;
    PApplet p;
    int[] fade;
    int count;
    int cycle;
    int x;
    int y;

    public Magic(PApplet p, int amount, int xx, int yy) {
        this.p = p;
        count = 0;
        x = xx;
        y = yy;
        bits = new PVector[amount];
        randoms = new float[amount];
        fade = new int[amount];

        cycle = 0;
        PImage img = p.loadImage("bit.png");
        for (cycle = 0; cycle < bits.length; cycle++) {
            randoms[cycle] = p.random(-1.5f, 1.5f);
        }
        cycle = 0;
    }

    public void SetX(int xx) {
        x = xx;
    }

    public void SetY(int yy) {
        y = yy;
    }

    public void Reset() {
        count = 0;
    }

    public void DrawMagic(int xx, int yy, boolean t) {

        if (t) {
            bits[count] = new PVector(xx, yy);
            fade[count] = 255;
            count++;
        }
        if (count > 254) {
            Reset();
        }

        for (cycle = 1; cycle < bits.length - 1; cycle++) {
            if (bits[cycle] != null) {
                bits[cycle].x += randoms[cycle - 1];
                bits[cycle].y += randoms[cycle + 1];
            }

        }

        for (cycle = 0; cycle < bits.length - 1; cycle++) {

            if (bits[cycle] != null) {
                p.noStroke();
                p.fill(255, fade[cycle]);
                fade[cycle] -= 3;
                p.rect(bits[cycle].x, bits[cycle].y, 2f, 2f);
                if (fade[cycle] < 0) {
                    bits[cycle] = null;
                    fade[count] = 255;
                }
            }
        }
    }
}

