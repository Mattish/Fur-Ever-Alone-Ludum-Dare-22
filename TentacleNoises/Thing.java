package TentacleNoises;

import processing.core.*;

/**
 * Created by IntelliJ IDEA.
 * User: Matthew Dale
 * Date: 05/12/11
 * Time: 23:14
 * Email: ma001md@gold.ac.uk
 */
public class Thing {
    int x, y, w, h, r, g, b, aniHover, aniClick, aniDo;
    String name;
    float offsetx, offsety;
    int txtsize;
    PImage img;
    String txt;
    boolean selectable, hover, clicked;
    Animation anim;
    public PApplet p;

    final int TOPLEFT = 1;
    final int TOPRIGHT = 2;
    final int BOTTOMRIGHT = 3;
    final int BOTTOMLEFT = 4;

    public Thing(PApplet p, String n, int x, int y, int w, int h, boolean select, int ani) {
        this.p = p;
        name = n;
        r = 255;
        g = 0;
        b = 0;
        selectable = select;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        offsetx = 1;
        offsety = 1;
        hover = false;
        clicked = false;
        aniHover = ani;
        aniClick = 0;
        aniDo = 0;
        anim = new Animation(p, aniHover);
    }

    public Thing(PApplet p, String n, int x, int y, int w, int h, PImage img, boolean select, int ani) {
        this(p, n, x, y, w, h, select, ani);
        this.img = img;
    }

    public Thing(PApplet p, String n, int x, int y, int w, int h, String txt, int txtsize, boolean select, int ani) {
        this(p, n, x, y, w, h, select, ani);
        this.txt = txt;
        this.txtsize = txtsize;
    }

    public void ChangeFontSize(int siz) {
        txtsize = siz;
        p.textFont(Inputs.font, txtsize);
    }

    public boolean IsSelectable() {
        return selectable;
    }

    public void SetSelectable(boolean k) {
        selectable = k;
    }

    public boolean IsHover() {
        return hover;
    }

    public boolean IsClicked() {
        return clicked;
    }

    public void SetHover(boolean k) {
        hover = k;
    }

    public int GetY() {
        return y;
    }

    public int GetX() {
        return x;
    }

    public int GetH() {
        return h;
    }

    public void SetY(int y) {
        this.y = y;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public void CheckWidth() {
        this.w = ((h - 8) * txt.length());
    }

    public void AniDo(int d) {
        aniDo = d;
    }

    public void AniClick(int d) {
        aniClick = d;
    }

    public void AniHover(int d) {
        aniHover = d;
    }

    public void Draw() {
        p.fill(255, 255, 255);
        if (img != null) {
            p.image(img, x + offsetx, y + offsety, w, h);
        }
        if (txt != null) {
            p.text(txt, x + offsetx, y + offsety, w, h);
        }
    }

    public void Update() {
        p.fill(r, g, b); //COLOUR OF TEXT

        if (IsMouseHovering()) { //UPDATING STATES
            hover = true;
        } else {
            hover = false;
        }
        if (hover && Inputs.Clicked() && IsSelectable() && aniClick != 0) {
            Clicked();
        }

        if (clicked && aniClick != 0) {
            anim.ChangeAnimation(aniClick);
            PVector update = anim.Animate(offsetx, offsety, w, h);
            offsetx = (int) update.x;
            offsety = (int) update.y;
        } else if (aniDo != 0) {
            anim.ChangeAnimation(aniDo);
            PVector update = anim.Animate(offsetx, offsety, w, h);
            offsetx = (int) update.x;
            offsety = (int) update.y;
        } else if (hover && aniHover != 0) {
            anim.ChangeAnimation(aniHover);
            PVector update = anim.Animate(offsetx, offsety, w, h);
            offsetx = (int) update.x;
            offsety = (int) update.y;
        } else {
            PVector update = anim.Return(0.9f);
            offsetx = (int) update.x;
            offsety = (int) update.y;
        }
    }

    public String GetName() {
        return name;
    }

    public void Return() {
        hover = false;
        clicked = false;
        aniDo = 0;
    }

    protected void Clicked() {
        hover = !hover;
        clicked = !clicked;
    }

    public boolean IsText() {
        if (txt != "") {
            return true;
        } else {
            return false;
        }
    }

    public int GetTextSize() {
        return txtsize;
    }

    public int GetW() {
        return w;
    }

    public void SetTextSize(int s) {
        txtsize = s;
    }

    public void SetText(String s) {
        txt = s;
        CheckWidth();
    }

    public boolean IsMouseHovering() {
        if (Inputs.GetX() > x + offsetx && Inputs.GetX() < x + offsetx + w) {
            if (Inputs.GetY() > y + offsety && Inputs.GetY() < y + offsety + h) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void ChangeImage(PImage pimg) {
        img = pimg;
    }

    public boolean[] Intersect(Thing t) {
        boolean[] tmp = new boolean[8];
        for (boolean b : tmp)
            b = false;

        if (t.GetX() > GetX() && t.GetX() < GetX() + GetW()) {   //CHECK TOPLEFT
            if (t.GetY() > GetY() && t.GetY() < GetY() + GetH()) {
                tmp[0] = true;
            }
        }

        if (t.GetX() + t.GetW() > GetX() && t.GetX() + t.GetW() < GetX() + GetW()) {   //CHECK TOPRIGHT
            if (t.GetY() > GetY() && t.GetY() < GetY() + GetH()) {
                tmp[1] = true;
            }
        }

        if (t.GetX() + t.GetW() > GetX() && t.GetX() + t.GetW() < GetX() + GetW()) {   //CHECK BOTTOMRIGHT
            if (t.GetY() + t.GetH() > GetY() && t.GetY() + t.GetH() < GetY() + GetH()) {
                tmp[2] = true;
            }
        }

        if (t.GetX() > GetX() && t.GetX() < GetX() + GetW()) {   //CHECK BOTTOMLEFT
            if (t.GetY() + t.GetH() > GetY() && t.GetY() + t.GetH() < GetY() + GetH()) {
                tmp[3] = true;
            }
        }

        if (t.GetX() > GetX() && t.GetX() < GetX() + GetW()) {   //CHECK BOTTOMLEFT ABOVE
            if (t.GetY() + t.GetH() - 3 > GetY() && t.GetY() + t.GetH() - 3 < GetY() + GetH()) {
                tmp[4] = true;
            }
        }

        if (t.GetX() + t.GetW() > GetX() && t.GetX() + t.GetW() < GetX() + GetW()) {   //CHECK BOTTOMRIGHT ABOVE
            if (t.GetY() + t.GetH() - 3 > GetY() && t.GetY() + t.GetH() - 3 < GetY() + GetH()) {
                tmp[5] = true;
            }
        }

        if (t.GetX() + t.GetW() / 2 > GetX() && t.GetX() + t.GetW() / 2 < GetX() + GetW()) {   //CHECK BOTTOMMID
            if (t.GetY() + t.GetH() > GetY() && t.GetY() + t.GetH() < GetY() + GetH()) {
                tmp[6] = true;
            }
        }

        if (t.GetX() + t.GetW() / 2 > GetX() && t.GetX() + t.GetW() / 2 < GetX() + GetW()) {   //CHECK TOPMID
            if (t.GetY() > GetY() && t.GetY() < GetY() + GetH()) {
                tmp[7] = true;
            }
        }

        return tmp;
    }
}


