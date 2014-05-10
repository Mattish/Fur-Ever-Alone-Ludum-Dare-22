package TentacleNoises;

import processing.core.PApplet;

import java.lang.management.ThreadInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Matthew Dale
 * Date: 05/12/11
 * Time: 23:14
 * Email: ma001md@gold.ac.uk
 */
public class Console_Lines {
    Thing[] lines;
    int fontsize;
    int current;
    int w;
    int h;
    int x;
    int y;
    int sizeh;
    PApplet p;
    boolean flick;

    public Console_Lines(PApplet p, int buffer, int x, int y, int w, int h) {
        this.p = p;
        lines = new Thing[buffer];
        current = 0;
        flick = false;
        sizeh = IdealSize(h);
        this.x = x;
        this.y = y;
        lines[0] = new Thing(p, "init", x + 5, y + h - sizeh, w - 5, sizeh, "", sizeh, false, 1);
    }

    public void DrawLines(int h, int w) {
        for (int p = 0; p < lines.length; p++) {
            if (lines[p] != null) {
                if (lines[p].GetTextSize() != fontsize) {
                    lines[p].ChangeFontSize(lines[p].GetTextSize());
                }
                lines[p].Draw();
            }
        }
    }

    public Thing[] GetLines() {
        return lines;
    }

    public Thing GetThingByName(String s) {
        for (Thing t : lines) {
            if (t.GetName() == s) {
                return t;
            }
        }
        return null;
    }

    public void AddLine(String n, String t, int w, int h, boolean select) {
        for (int k = current; k >= 0; k--) {
            if (k + 1 < lines.length - 1) {
                lines[k + 1] = lines[k];
                if (lines[k + 1] != null) {
                    lines[k + 1].SetY((lines[k + 1].GetY() - sizeh));
                }
            }
        }
        lines[0] = new Thing(p, n, x + 5, y + h - sizeh, ((sizeh-8) * t.length()), sizeh, t, sizeh, select, 1);
        if (current < lines.length) {
            current++;
        }
    }

    public void AddEmptyLine() {
        AddLine("", "", 0, 0, false);
    }

    public int IdealSize(int h) {
        return (h / lines.length);
    }

    public void ClearLines() {
        lines = new Thing[lines.length];
    }
}


