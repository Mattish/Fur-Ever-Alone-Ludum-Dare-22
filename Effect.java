import TentacleNoises.Thing;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by IntelliJ IDEA.
 * User: Mattish_2
 * Date: 18/12/11
 * Time: 05:02
 * To change this template use File | Settings | File Templates.
 */
public class Effect extends Thing {
    PVector[] bits;
    float[] randoms;
    float[] randoms2;
    float var1;
    float var2;
    PApplet p;
    int[] fade;
    int count;
    int cycle;
    boolean lethal;
    int time;
    int timelength;

    public Effect(PApplet p, String name, int amount, int tl, int xx, int yy, int ww, int hh) {
        super(p, name, xx, yy, ww, hh, true, 0);
        time = 1;
        timelength = tl;
        lethal = true;
        this.p = p;
        count = 0;
        var1 = 0f;
        var2 = 0f;
        bits = new PVector[amount];
        randoms = new float[amount];
        randoms2 = new float[amount];
        fade = new int[amount];

        cycle = 0;
        PImage img = p.loadImage("bit.png");

        if (GetName() == "StrongGustR" || GetName() == "StrongGustD"
                || GetName() == "StrongGustU" || GetName() == "StrongGustL") {
            for (cycle = 0; cycle < bits.length; cycle++) {
                randoms[cycle] = p.random(-1f, 1f);
            }
        } else if (GetName() == "Gust") {
            for (cycle = 0; cycle < bits.length; cycle++) {
                randoms[cycle] = p.random(-0.5f, 0.5f);
            }
        } else if (GetName() == "LavaR" || GetName() == "LavaL" || GetName() == "LavaU" || GetName() == "LavaD") {
            for (cycle = 0; cycle < bits.length; cycle++) {
                randoms[cycle] = p.random(0.5f, 1f);
                randoms2[cycle] = p.random(-5f, 5f);
            }
        } else if (GetName() == "DeathEffect") {
            for (cycle = 0; cycle < bits.length; cycle++) {
                randoms[cycle] = p.random(-1f, 1f);
                randoms2[cycle] = p.random(-1f, 1f);
            }
        }

        cycle = 0;
    }

    public void Reset() {
        count = 0;
    }

    public void SetLethal(boolean b) {
        lethal = b;
    }

    public boolean GetLethal() {
        return lethal;
    }

    public void DrawEffect(boolean t) {
        time++;

        //HIT BOX TEST
        /*p.fill(0, 255, 0);
        p.rect(GetX(), GetY(), GetW(), GetH());
        */


        var1 += 0.1;
        if (timelength != 0) {
            if (time > timelength) {
                lethal = !lethal;
                time = 0;
            }
        }

        if (t && lethal) {
            if (GetName() == "StrongGustR" || GetName() == "Gust") {
                bits[count] = new PVector(this.GetX(), this.GetY() + 25);
                fade[count] = 255;
                count++;
            } else if (GetName() == "DeathEffect") {
                for (int ta = 0; ta < 2; ta++) {
                    bits[count] = new PVector(this.GetX(), this.GetY());
                    fade[count] = 255;
                    count++;
                }
            } else if (GetName() == "StrongGustD") {
                bits[count] = new PVector(this.GetX() + 25, this.GetY());
                fade[count] = 255;
                count++;
            } else if (GetName() == "StrongGustL") {
                bits[count] = new PVector(this.GetX() + 150, this.GetY() + 25);
                fade[count] = 255;
                count++;
            } else if (GetName() == "StrongGustU") {
                bits[count] = new PVector(this.GetX() + 25, this.GetY() + 150);
                fade[count] = 255;
                count++;
            } else if (GetName() == "LavaU") {
                for (int ta = 0; ta < 5; ta++) {
                    bits[count] = new PVector(this.GetX() + 40 + randoms2[count] * 10, 25 + this.GetY() + p.random(10));    //SPAWNING
                    fade[count] = 255;
                    count++;
                }
            } else if (GetName() == "LavaD") {
                for (int ta = 0; ta < 5; ta++) {
                    bits[count] = new PVector(this.GetX() + 40 + randoms2[count] * 10, this.GetY() - 5 + p.random(10));    //SPAWNING
                    fade[count] = 255;
                    count++;
                }
            } else if (GetName() == "LavaL") {
                for (int ta = 0; ta < 5; ta++) {
                    bits[count] = new PVector(this.GetX() + 25 + p.random(10), 30 + this.GetY() + randoms2[count] * 10);    //SPAWNING
                    fade[count] = 255;
                    count++;
                }
            } else if (GetName() == "LavaR") {
                for (int ta = 0; ta < 5; ta++) {
                    bits[count] = new PVector(this.GetX() - 5 + p.random(10), 30 + this.GetY() + randoms2[count] * 10);    //SPAWNING
                    fade[count] = 255;
                    count++;
                }
            }

        }
        if (count > bits.length - 1) {
            Reset();
        }

        for (cycle = 1; cycle < bits.length - 1; cycle++) {         //WHERE ARE THEY GOING
            if (bits[cycle] != null) {
                //bits[cycle].x += randoms[cycle - 1];
                if (GetName() == "StrongGustR") {
                    bits[cycle].x += 5;
                    bits[cycle].y += randoms[cycle + 1];
                } else if (GetName() == "StrongGustD") {
                    bits[cycle].x += randoms[cycle + 1];
                    bits[cycle].y += 5;
                } else if (GetName() == "StrongGustU") {
                    bits[cycle].x += randoms[cycle + 1];
                    bits[cycle].y -= 5;
                } else if (GetName() == "StrongGustL") {
                    bits[cycle].x -= 5;
                    bits[cycle].y += randoms[cycle + 1];
                } else if (GetName() == "Gust") {
                    bits[cycle].x += 2;
                    bits[cycle].y += randoms[cycle + 1];
                } else if (GetName() == "LavaU") {
                    bits[cycle].x -= randoms2[cycle - 1] / 10;
                    bits[cycle].y -= randoms[cycle + 1];
                } else if (GetName() == "LavaD") {
                    bits[cycle].x -= randoms2[cycle - 1] / 10;
                    bits[cycle].y += randoms[cycle + 1];
                } else if (GetName() == "LavaL") {
                    bits[cycle].x -= randoms[cycle + 1];
                    bits[cycle].y -= randoms2[cycle - 1] / 10;
                } else if (GetName() == "LavaR") {
                    bits[cycle].x += randoms[cycle + 1];
                    bits[cycle].y -= randoms2[cycle - 1] / 10;
                } else if (GetName() == "DeathEffect") {
                    //bits[cycle].x += p.cos(var1);
                    //bits[cycle].y += p.sin(var1);
                    bits[cycle].x += randoms2[cycle] * 2;
                    bits[cycle].y += randoms[cycle] * 2;
                }
            }

        }
        var2++;
        for (cycle = 0; cycle < bits.length - 1; cycle++) {

            if (bits[cycle] != null) {
                if (GetName() == "StrongGustR" || GetName() == "Gust" || GetName() == "StrongGustD"
                        || GetName() == "StrongGustU" || GetName() == "StrongGustL") {    //DRAWING
                    p.noStroke();
                    p.fill(255, fade[cycle]);
                    fade[cycle] -= 6;
                    p.rect(bits[cycle].x, bits[cycle].y, 1f, 1f);
                } else if (GetName() == "LavaU" || GetName() == "LavaD"
                        || GetName() == "LavaL" || GetName() == "LavaR") {
                    p.noStroke();
                    p.fill(255, fade[cycle] % 100, 0, fade[cycle]);
                    fade[cycle] -= 6;
                    //p.rect(bits[cycle].x, bits[cycle].y, 8.5f, 8.5f);
                    p.ellipse(bits[cycle].x, bits[cycle].y, 8.5f, 8.5f);
                } else if (GetName() == "DeathEffect") {
                    p.noStroke();
                    p.fill((var2 * 3.5f) % 255, (var2 * 4.5f) % 255, (var2 * 6.5f) % 255, fade[cycle]);

                    fade[cycle] -= 5;
                    p.rect(bits[cycle].x, bits[cycle].y, 3f, 3f);
                }
                if (fade[cycle] < 0) {
                    bits[cycle] = null;
                    fade[count] = 255;
                }
            }
        }
    }
}

