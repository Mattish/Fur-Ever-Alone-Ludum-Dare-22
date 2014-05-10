package TentacleNoises;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by IntelliJ IDEA.
 * User: Matthew Dale
 * Date: 05/12/11
 * Time: 23:14
 * Email: ma001md@gold.ac.uk
 */
public class Animation {
    float x, y, w, h, accelx, accely, var1, var2, var3, var4;
    int num;
    PApplet p;

    public Animation(PApplet p, int number) {
        this.p = p;
        num = number;
        Reset();
    }

    public PVector Animate(float xx, float yy, float ww, float hh) {
        x = xx;
        y = yy;
        w = ww;
        h = hh;
        if (num == 0) {      //STOPPING
            x += var1;
            y += var2;
            ReturnVars(0.5f);
        }

        if (num == 1) {       //MOVING LEFT
            if (var1 > 0)
                var1 = 0;
            x += var1;
            if (var1 >= -4.5) {
                var1 -= 0.5;
            }
        }

        if (num == 2) {       //MOVING RIGHT
            if (var1 < 0)
                var1 = 0;
            x += var1;
            if (var1 <= 4.5) {
                var1 += 0.5;
            }
        }

        if (num == 3) {       //FALLING  LEFT
            x += var1;
            if (var1 >= -2.5) {
                var1 -= 0.4;
            }
        }

        if (num == 4) {       //FALLING RIGHT
            x += var1;
            if (var1 <= 2.5) {
                var1 += 0.4;
            }
        }


        if (num == 10)

        { //BOUNCE FORWARD AND BACK
            if (x > 30) {
                Return(0.9f);
                var1 = 360;
            } else {
                x = p.abs(p.sin(var1) * 25);
                var1 += accelx;
            }
        }

        if (num == 20)

        { //FLY TO MIDDLE AT REASONABLY FAST SPEED
            x += (((p.width - w) / 2) - x) / 5;
        }

        if (num == 30)

        { //FLY OFF TO THE RIGHT HANDSIDE
            x += (p.width - x) / 5;
        }

        if (num == 40) { //FLY OFF IN SPIRL
            x += p.sin(var3)*var4;
            y += p.cos(var3)*var4;
            var3 += 0.1; //SPEED
            var4 += 0.05; //DISTANCE
        }

        return new

                PVector(x, y);

    }

    public void ChangeAnimation(int a) {
        num = a;
    }

    public PVector Return(float factor) {
        x *= factor;
        y *= factor;
        var1 *= factor;
        return new PVector(x, y);
    }

    public void ReturnVars(float factor) {
        if (p.abs(var1) < 0.01)
            var1 = 0;
        else
            var1 *= factor;

        if (p.abs(var2) < 0.01)
            var2 = 0;
        else
            var2 *= factor;

        if (p.abs(var3) < 0.01)
            var3 = 0;
        else
            var3 *= factor;
    }

    public void Reset() {
        x = 0;
        y = 0;
        accelx = 0.05f;
        accely = 2f;
        var1 = 0;
        var2 = 0;
        var3 = 0f;
        var4 = 2;
    }
}


