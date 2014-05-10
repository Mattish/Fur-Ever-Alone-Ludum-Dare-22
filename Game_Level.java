import TentacleNoises.Game_Component;
import TentacleNoises.Inputs;
import TentacleNoises.Thing;
import com.sun.xml.internal.ws.client.PortInfo;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Mattish_2
 * Date: 17/12/11
 * Time: 03:53
 * To change this template use File | Settings | File Templates.
 */
public class Game_Level extends Game_Component {
    Thing[] level;
    Effect[] leveleffects;
    Thing background;
    Thing remote;
    Thing levelchange;
    int remotedisable;
    int remoteheight;
    boolean remotedraw;
    PVector playerspawn;
    Player player;
    Magic magic1;
    boolean magicsuccessful;
    boolean levelchanging;
    int magiccd;
    int deathcd;
    Effect deatheffect;
    int deatheffectlength;
    boolean dead;
    int starting;
    float startingfade;
    float scrollingtext;
    String[] storytext;
    boolean loaded = false;


    public Game_Level(PApplet p, String s, int levelnum) {
        super(p, s);
        ChangeLevel(levelnum);
    }

    public void ChangeLevel(int num) {
        starting = 180;
        scrollingtext = 0;
        startingfade = 0;
        deatheffect = new Effect(p, "DeathEffect", 100, 0, 300, 300, 1, 1);
        deatheffectlength = -1;
        deathcd = 0;
        dead = false;
        magic1 = new Magic(p, 255, 200, 200);
        magicsuccessful = false;
        magiccd = 30;
        level = new Thing[1000];
        leveleffects = new Effect[100];
        levelchanging = false;
        player = new Player(this.p);
        //STATE = RUNNING;
        remotedisable = -1;
        remoteheight = 0;
        remotedraw = false;
        remote = new Thing(p, "Remote", -50, -50, 20, 20, p.loadImage("remote.png"), false, 0);
        if (num != 5 && num != 10 && num != 14) {
            background = new Thing(p, "Background", 0, 0, p.width, p.height, p.loadImage("BG1.png"), false, 0);
        } else {
            background = new Thing(p, "Background", 0, 0, p.width, p.height, p.loadImage(num + ".png"), false, 0);
        }
        LoadLevel(num);
    }

    public void LoadLevel(int n) {
        levelchange = null;
        PImage GrassUD = p.loadImage("Tile_GrassUD.png");
        PImage GrassLR = p.loadImage("Tile_GrassLR.png");
        PImage GrassU = p.loadImage("Tile_GrassU.png");
        PImage GrassL = p.loadImage("Tile_GrassL.png");
        PImage GrassD = p.loadImage("Tile_GrassD.png");
        PImage Black = p.loadImage("Tile_Black.png");
        PImage Plants1 = p.loadImage("Tile_Plants1.png");
        PImage EndSign = p.loadImage("Tile_End.png");

        if (n == 5 || n == 10 || n == 14) {
            storytext = new String[8];
            storytext = p.loadStrings("level" + n + "-story.txt");
        } else {
            String[] lines = p.loadStrings("level" + n + "-1.txt");
            int itemcount = 0;
            int staY = 0;
            for (String string : lines) {
                int staX = 0;
                for (char c : string.toCharArray()) {
                    if (c == 'T') {
                        playerspawn = new PVector(100 * staX, 100 * staY);
                        level[itemcount] = new Thing(p, "LevelPart", 100 * staX, 100 * staY, 100, 100, GrassD, false, 0);
                        itemcount++;
                    } else if (c == 't') {
                        level[itemcount] = new Thing(p, "LevelPart", 100 * staX, 100 * staY, 100, 100, GrassUD, false, 0);
                        itemcount++;
                    } else if (c == 'r') {
                        level[itemcount] = new Thing(p, "LevelPart", 100 * staX, 100 * staY, 100, 100, GrassLR, false, 0);
                        itemcount++;
                    } else if (c == 'u') {
                        level[itemcount] = new Thing(p, "LevelPart", 100 * staX, 100 * staY, 100, 100, GrassU, false, 0);
                        itemcount++;
                    } else if (c == 'd') {
                        level[itemcount] = new Thing(p, "LevelPart", 100 * staX, 100 * staY, 100, 100, GrassD, false, 0);
                        itemcount++;
                    } else if (c == 'l') {
                        level[itemcount] = new Thing(p, "LevelPart", 100 * staX, 100 * staY, 100, 100, GrassL, false, 0);
                        itemcount++;
                    } else if (c == '1') {
                        level[itemcount] = new Thing(p, "LevelPart", 100 * staX, 100 * staY, 100, 100, Black, true, 0);
                        itemcount++;
                    }
                    staX++;
                }
                staY++;
            }

            lines = p.loadStrings("level" + n + "-2.txt");
            staY = 0;
            if (lines != null) {
                for (String string : lines) {
                    int staX = 0;
                    for (char c : string.toCharArray()) {
                        if (c == 'p') {
                            level[itemcount] = new Thing(p, "LevelPart", 100 * staX, 100 * staY, 100, 100, Plants1, false, 0);
                            itemcount++;
                        }
                        if (c == 'c') {
                            levelchange = new Thing(p, "LevelChange", 100 * staX, 100 * staY, 100, 100, false, 0);
                            level[itemcount] = new Thing(p, "LevelEnd", 100 * staX, 100 * staY, 100, 100, EndSign, false, 0);
                            itemcount++;
                        }
                        staX++;
                    }
                    staY++;
                }
            }
            itemcount = 0;
            lines = p.loadStrings("level" + n + "-3.txt");
            staY = 0;
            if (lines != null) {
                for (String string : lines) {
                    int staX = 0;
                    for (char c : string.toCharArray()) {
                        if (c == 'e') {
                            leveleffects[itemcount] = new Effect(p, "Gust", 150, 0, 100 * staX, (100 * staY) + 25, 100, 50);
                            itemcount++;
                        } else if (c == 'r') {
                            leveleffects[itemcount] = new Effect(p, "StrongGustR", 250, 0, 100 * staX, (100 * staY) + 25, 150, 50);
                            itemcount++;
                        } else if (c == 'l') {
                            leveleffects[itemcount] = new Effect(p, "StrongGustL", 250, 0, (100 * staX) - 50, (100 * staY) + 25, 150, 50);
                            itemcount++;
                        } else if (c == 'd') {
                            leveleffects[itemcount] = new Effect(p, "StrongGustD", 250, 0, (100 * staX) + 25, (100 * staY), 50, 150);
                            itemcount++;
                        } else if (c == 'u') {
                            leveleffects[itemcount] = new Effect(p, "StrongGustU", 250, 0, (100 * staX) + 25, (100 * staY) - 50, 50, 150);
                            itemcount++;
                        } else if (c == '7') {
                            leveleffects[itemcount] = new Effect(p, "Lava", 300, 240, (100 * staX), (100 * staY) + 70, 70, 30);
                            itemcount++;
                        } else if (c == 'U') {
                            leveleffects[itemcount] = new Effect(p, "LavaU", 300, 0, (100 * staX) + 10, (100 * staY) + 70, 70, 30);
                            itemcount++;
                        } else if (c == 'D') {
                            leveleffects[itemcount] = new Effect(p, "LavaD", 300, 0, (100 * staX) + 10, (100 * staY), 70, 30);
                            itemcount++;
                        } else if (c == 'L') {
                            leveleffects[itemcount] = new Effect(p, "LavaL", 300, 0, (100 * staX) + 70, (100 * staY) + 20, 30, 70);
                            itemcount++;
                        } else if (c == 'R') {
                            leveleffects[itemcount] = new Effect(p, "LavaR", 300, 0, (100 * staX), (100 * staY) + 20, 30, 70);
                            itemcount++;
                        }
                        staX++;
                    }
                    staY++;
                }
            }

            player.SetX((int) playerspawn.x);
            player.SetY((int) playerspawn.y + 20);
            player.SetDirection(player.RIGHT);
        }
    }

    public void Draw() {
        FontChange();
        p.background(40);
        background.Draw();
        if (Inputs.ChangeLevelTo != 5 && Inputs.ChangeLevelTo != 10 && Inputs.ChangeLevelTo != 14) {
            if (dead) { //CHECKING FOR IF DEAD
                if (deathcd > -1) {
                    deathcd--;
                } else if (deathcd < 0) {
                    player = new Player(this.p);
                    player.SetX((int) playerspawn.x);
                    player.SetY((int) playerspawn.y);
                    dead = false;
                }
            }
            if (player.GetY() < 0 || player.GetY() + player.GetH() > p.height) {                      // CHECKING IF FELL OFF SCREEN
                BeDead();
            }
            if (levelchange != null) {
                boolean[] levelchangecheck = player.Intersect(levelchange);           //LEVEL CHANGE CHECK
                for (boolean b : levelchangecheck) {

                    if (b && !levelchanging) {
                        Inputs.ChangeLevelTo++;
                        Inputs.ChangePlease = true;
                        levelchanging = true;
                    }
                }
            }

            remotedraw = false;

            if (magiccd > 0)     //TELEPORT CD REDUCE
                magiccd--;

            if (remotedisable > -1) {
                remotedisable--;
            }
            if (remotedisable == 0) {   //TELEPORT RESETTING
                remoteheight = 0;
                if (remote.GetY() > 0 && remote.GetY() < p.height) {
                    boolean[] remotecheck = CheckCollision(new Thing(p, "RemoteCheck", remote.GetX(), remote.GetY(), 30, 50, "test", 0, false, 0));
                    if (!remotecheck[7] && !remotecheck[2] && !remotecheck[3]) {
                        if (magiccd < 1) {
                            player.SetY(remote.GetY());   //SUCCESSFUL TELEPORTING
                            magicsuccessful = true;
                            player.SetState(player.STATIC);
                            player.SetDirection(player.NONE);
                            magiccd = 60;
                        }
                    } else {
                        magicsuccessful = false;
                    }
                }
            }
            if (player.GetX() > p.width - player.GetW() / 2) {
                ScreenShiftRight();               //MOVE SCREEN
            }
            if (player.GetX() < 0 - player.GetW() / 2) {
                ScreenShiftLeft();   //MOVE SCREEN
            }
            if (!dead) {
                player.Update();
            }
            for (Thing thing : level) {  // UPDATE ALL THE THINGS
                if (thing != null)
                    thing.Update();
            }
            if (!dead) {
                ControlChar();   //CONTROLS
                CheckEffects(player);      //LEVEL EFFECTS GUST, FIRE ETC.
            }
            boolean[] colCheck = CheckCollision(player);

            boolean fall = true;

            if (colCheck[3] && colCheck[4]) //WALL COLLISION STUFF
                if (player.GetDirection() != player.RIGHT) {
                    player.SetDirection(player.NONE);
                    player.SetX(player.GetX() + 4);
                }
            if (colCheck[2] && colCheck[5])
                if (player.GetDirection() != player.LEFT) {
                    player.SetDirection(player.NONE);
                    player.SetX(player.GetX() - 4);
                }

            if (colCheck[2] && colCheck[6]) // GROUND COLLISION
                fall = false;
            if (player.GetState() == player.FALLING) {
                player.SetState(player.STATIC);
            }
            if (colCheck[3] && colCheck[6]) // GROUND COLLISION
                fall = false;
            if (player.GetState() == player.FALLING) {
                player.SetState(player.STATIC);
            }
            if (colCheck[3] && colCheck[2]) // GROUND COLLISION
                fall = false;
            if (player.GetY() % 50 > 2)        //DONT BE THE GROUND YOU DUMB CAT OMG
                player.SetY(player.GetY() - 1);
            if (player.GetState() == player.FALLING) {
                player.SetState(player.STATIC);
            }

            if (fall)
                player.SetState(player.FALLING);
            if (!dead) {
                player.UpdatePlace(); // UDPATE PLAYER BASED ON STATE
                player.Draw(); // DRAW DA PLAYER
            }
            for (Thing thing : level) {
                if (thing != null)
                    thing.Draw();               // DRAW ALL THE THINGS
            }
            for (Effect effect : leveleffects) {
                if (effect != null)
                    effect.DrawEffect(true);
            }
            magic1.DrawMagic(player.GetX() + player.GetW() / 2, player.GetY() + remoteheight + player.GetH() / 2, remotedraw);
            remote.SetY(player.GetY() + remoteheight);


            if (deatheffectlength > 0) {
                deatheffect.DrawEffect(true);
                deatheffectlength--;
            } else {
                deatheffect.DrawEffect(false);
            }
            p.textSize(10);
            p.fill(255);
            p.stroke(255, 255, 255);
            p.text("fps:" + p.frameRate, 5, 10);      //FPS COUNTER
        } else {
            p.textSize(15);
            p.fill(255, 255, 255);
            for (int tx = 0; tx < storytext.length; tx++) {
                if (storytext[tx] != null) {
                    p.text(storytext[tx], 100, (p.height + scrollingtext) + (tx * 25));
                }
            }
            scrollingtext -= 1.5;
        }
        if (p.abs(scrollingtext) > p.height + 150 && Inputs.ChangeLevelTo != 14) {
            if (!levelchanging) {
                Inputs.ChangeLevelTo++;
                Inputs.ChangePlease = true;
                levelchanging = true;
            }
        } else if (p.abs(scrollingtext) > p.height - 150 && Inputs.ChangeLevelTo == 14) {
            scrollingtext += 1.5;
        }
        if (starting > 0) {
            if (starting > 90)
                startingfade += 255 / 90;
            else if (starting <= 90)
                startingfade -= 255 / 90;

            p.textSize(48);                  // DRAWING LEVEL NUMBER START
            p.fill(255, 255, 255, startingfade);
            p.text("Level " + Inputs.ChangeLevelTo, p.width / 5, p.height / 2);
            starting--;
        }
    }

    public void ScreenShiftRight() { //PROGRESS LEVEL RIGHT
        player.SetX(player.GetX() - p.width);
        for (Thing levelpart : level) {
            if (levelpart != null)
                levelpart.SetX(levelpart.GetX() - p.width);
        }
        for (Effect effect : leveleffects) {
            if (effect != null)
                effect.SetX(effect.GetX() - p.width);

        }
        if (levelchange != null)
            levelchange.SetX(levelchange.GetX() - p.width);

        if (playerspawn != null)
            playerspawn.x = playerspawn.x - p.width;
    }

    public void ScreenShiftLeft() {  //PROGRESS LEVEL LEFT
        player.SetX(player.GetX() + p.width);
        for (Thing levelpart : level) {
            if (levelpart != null)
                levelpart.SetX(levelpart.GetX() + p.width);
        }
        for (Effect effect : leveleffects) {
            if (effect != null)
                effect.SetX(effect.GetX() + p.width);
        }
        if (levelchange != null)
            levelchange.SetX(levelchange.GetX() + p.width);

        if (playerspawn != null)
            playerspawn.x = playerspawn.x + p.width;
    }

    public void CheckEffects(Thing thing) {           //CHECKING EFFECTS LIKE GUST ETC.
        for (Effect effect : leveleffects) {
            if (effect != null) {
                /*if (p.abs(effect.GetX() - thing.GetX()) < 100
                        && (thing.GetY() + thing.GetH() / 2) > effect.GetY()
                        && (thing.GetY() + thing.GetH() / 2) < effect.GetY() + effect.GetH()) { */
                boolean[] checkingeffecting = effect.Intersect(player);
                boolean touching = false;
                for (boolean b : checkingeffecting) {
                    if (b)
                        touching = true;
                }

                if (touching) {

                    if (effect.GetName() == "Gust")
                        thing.SetX(thing.GetX() + 2);
                    if (effect.GetName() == "StrongGustR")
                        thing.SetX(thing.GetX() + 4);
                    if (effect.GetName() == "StrongGustL")
                        thing.SetX(thing.GetX() - 4);
                    if (effect.GetName() == "StrongGustD")
                        thing.SetY(thing.GetY() + 4);
                    if (effect.GetName() == "StrongGustU")
                        thing.SetY(thing.GetY() - 4);
                    if (effect.GetName() == "LavaU" || effect.GetName() == "LavaD" ||
                            effect.GetName() == "LavaL" || effect.GetName() == "LavaR") {
                        if (effect.GetLethal()) {
                            BeDead();
                        }
                    }
                }
            }
        }
    }

    public void BeDead() {
        deathcd = 60;
        dead = true;
        deatheffectlength = 60;
        deatheffect.SetX(player.GetX() + player.GetW() / 2);
        deatheffect.SetY(player.GetY() + player.GetH() / 2);
    }

    public boolean[] CheckCollision(Thing thing) {    //CHECK FOR COLLISION
        boolean[] col = new boolean[8];
        for (boolean b : col)
            b = false;

        for (Thing levelpart : level) {
            if (levelpart != null) {
                if (levelpart.IsSelectable()) {
                    boolean[] tmp = levelpart.Intersect(thing);
                    if (tmp[0])
                        col[0] = true;
                    if (tmp[1])
                        col[1] = true;
                    if (tmp[2])
                        col[2] = true;
                    if (tmp[3])
                        col[3] = true;
                    if (tmp[4])
                        col[4] = true;
                    if (tmp[5])
                        col[5] = true;
                    if (tmp[6])
                        col[6] = true;
                    if (tmp[7])
                        col[7] = true;
                }

            }

        }
        return col;
    }

    private void ControlChar() {          //CONTROLS CHECK
        int state = player.GetState();
        //LEFT
        if (Inputs.left) {
            player.SetDirection(player.LEFT);
            if (state != player.JUMPING && state != player.FALLING)
                player.SetState(player.RUNNING);
        }
        //UP
        if (Inputs.up) {
            if (magiccd < 1) {
                remote.SetX(player.GetX() + 20);

                remotedisable = 5;
                remote.SetY(remote.GetY() - remoteheight);
                if (remoteheight < -375) {
                    remoteheight = -375;
                }
                remoteheight -= 5;
                remotedraw = true;
            }
        }
        //RIGHT
        if (Inputs.right) {
            player.SetDirection(player.RIGHT);
            if (state != player.JUMPING && state != player.FALLING)
                player.SetState(player.RUNNING);
        }
        //DOWN
        if (Inputs.down) {
            if (magiccd < 1) {
                remote.SetX(player.GetX() + 20);
                remote.SetY(player.GetY());
                remotedisable = 5;
                remote.SetY(remote.GetY() + remoteheight);
                if (remoteheight > 375) {
                    remoteheight = 375;
                }
                remoteheight += 5;
                remotedraw = true;
            }
        }


        if (player.GetState() == player.RUNNING) {
            if (!Inputs.left && !Inputs.right)
                player.SetState(player.STATIC);
        }
    }

    private void FontChange() {
        if (!loaded) {
            p.textFont(p.loadFont("MeiryoUI-Bold-48.vlw"), 8);
            loaded = true;
        }
    }

}

