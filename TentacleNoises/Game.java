package TentacleNoises;

import processing.core.PApplet;

/**
 * Created by IntelliJ IDEA.
 * User: Matthew Dale
 * Date: 05/12/11
 * Time: 23:14
 * Email: ma001md@gold.ac.uk
 */
public class Game {
    Game_Component[] GClist;
    PApplet p;
    int current;

    public Game(PApplet p,int amount) {
        GClist = new Game_Component[amount];
        this.p = p;
        current = 0;
        //GClist[1] = new Game_Test(p);
        //GClist[2] = new Game_Main(p);
    }

    public void Update() {
        for (Game_Component gc : GClist) {
            if (gc != null) {
                gc.Update();
                if (gc.STATE == gc.STOPPING){
                    Game_Component GC = GetGCByName(gc.CHANGETO);
                    if (GC.STATE != gc.STARTING){
                        GC.Start(gc.CHANGINGCOUNTDOWN);
                        GC.Reset();
                    }
                }
            }

        }

    }

    public void AddGC(Game_Component gc){
        GClist[current] = gc;
        current++;
    }

    public Game_Component GetGCByName(String n){
        for(Game_Component gc : GClist){
            if (gc.GetName() == n){
                return gc;
            }
        }
        return null;
    }
}



