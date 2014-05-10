import processing.core.*;
import processing.opengl.*;

import TentacleNoises.*;

import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Mattish_2
 * Date: 04/12/11
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */


public class Sketch extends PApplet {
    int w, h;
    Game game;
    PFont font;
    Thing t;
    Sound sound;

    public Sketch(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void setup() {


        size(w, h, OPENGL);
        frameRate(60);
        game = new Game(this, 5);
        game.AddGC(new Game_Level(this, "Level", 10));
        font = loadFont("PurpleTentaclePD-72.vlw");
        Inputs.SetFont(font);
        textFont(font, 48);
        game.AddGC(new Game_Splash(this, "Splash"));
        game.AddGC(new Game_MainMenu(this, "MainMenu"));
        Inputs.ChangeLevelTo = 10;
        smooth();
        strokeWeight(10);
        Inputs.SetFont(font);
        Inputs.Disable();
        sound = new Sound(this);
        sound.StartBG();
    }

    public void draw() {
        background(0);
        fill(255);
        Inputs.Update(mouseEvent);
        game.Update();
        if (Inputs.ChangePlease == true) {
            game.GetGCByName("Level").ChangeLevel(Inputs.ChangeLevelTo);
            Inputs.ChangePlease = false;
        }
    }

    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == 37)
            Inputs.SetLeft(true);
        if (ke.getKeyCode() == 38)
            Inputs.SetUp(true);
        if (ke.getKeyCode() == 39)
            Inputs.SetRight(true);
        if (ke.getKeyCode() == 40)
            Inputs.SetDown(true);
    }

    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyCode() == 37)
            Inputs.SetLeft(false);
        else if (ke.getKeyCode() == 38)
            Inputs.SetUp(false);
        else if (ke.getKeyCode() == 39)
            Inputs.SetRight(false);
        else if (ke.getKeyCode() == 40)
            Inputs.SetDown(false);

    }
}
