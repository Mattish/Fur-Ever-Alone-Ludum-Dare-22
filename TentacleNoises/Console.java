package TentacleNoises;

import processing.core.PApplet;

/**
 * Created by IntelliJ IDEA.
 * User: Matthew Dale
 * Date: 05/12/11
 * Time: 23:14
 * Email: ma001md@gold.ac.uk
 */
public class Console {
    Console_Lines console_Lines;
    int bgR;
    int bgG;
    int bgB;
    int w;
    int h;
    int x;
    int y;
    PApplet p;

    public Console(PApplet pa, int buffer, int x, int y, int w, int h) {
        p = pa;
        console_Lines = new Console_Lines(p, buffer, x, y, w, h);
        this.x = x;
        this.y = y;
        bgR = 0;
        bgG = 0;
        bgB = 0;
        this.w = w;
        this.h = h;
    }

    public void ChangeBGColor(int R, int G, int B) {
        bgR = R;
        bgG = G;
        bgB = B;
    }

    private void drawBG() {
        p.fill(bgR, bgG, bgB);
        p.noStroke();
        p.rect(x, y, w, h);
    }

    public Thing[] GetLines() {
        return console_Lines.GetLines();
    }

    public void Update() {
        drawBG();
        console_Lines.DrawLines(h, w);
    }

    public void AddLine(String name, String txt, boolean select) {
        console_Lines.AddLine(name, txt, w, h, select);
    }

    public void AddEmptyLine() {
        console_Lines.AddEmptyLine();
    }

    public Thing GetThingByName(String s) {
        return console_Lines.GetThingByName(s);

    }

    public void ClearLines(){
        console_Lines.ClearLines();
    }
}


