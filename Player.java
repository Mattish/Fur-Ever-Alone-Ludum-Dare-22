import TentacleNoises.Animation;
import TentacleNoises.Thing;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by IntelliJ IDEA.
 * User: Mattish_2
 * Date: 17/12/11
 * Time: 04:15
 * To change this template use File | Settings | File Templates.
 */
public class Player extends Thing {
    final int STATIC = 0;
    final int RUNNING = 1;
    final int JUMPING = 2;
    final int FALLING = 3;
    final int NONE = 0;
    final int LEFT = 1;
    final int RIGHT = 2;

    int STATE;
    int DIRECTION;

    Animation doing;
    float animationcount;
    PImage STATIC_LEFT_0;
    PImage STATIC_LEFT_1;
    PImage STATIC_RIGHT_0;
    PImage STATIC_RIGHT_1;

    float fallrate;

    public Player(PApplet p) {
        super(p, "Player", 50, 1, 50, 50, p.loadImage("char.png"), false, 0);
        LoadImages(p);
        fallrate = 0.3f;
        doing = new Animation(p, 0);
        animationcount = 0;
        STATE = STATIC;
        DIRECTION = LEFT;
    }

    public void UpdatePlace() {
        PVector loc = new PVector(GetX(), GetY());
        if (STATE == RUNNING) {
            fallrate = 0.3f;
            if (DIRECTION == LEFT) {
                if (animationcount < 1)
                    ChangeImage(STATIC_LEFT_0);
                else
                    ChangeImage(STATIC_LEFT_1);

                doing.ChangeAnimation(LEFT);
                loc = doing.Animate(GetX(), GetY(), GetW(), GetH());
                this.SetX((int) loc.x);
            } else if (DIRECTION == RIGHT) {
                if (animationcount < 1)
                    ChangeImage(STATIC_RIGHT_0);
                else
                    ChangeImage(STATIC_RIGHT_1);

                doing.ChangeAnimation(RIGHT);
                loc = doing.Animate(GetX(), GetY(), GetW(), GetH());
                this.SetX((int) loc.x);
            } else if (DIRECTION == NONE) {
                doing.ReturnVars(0.3f);
            }

        }

        if (STATE == FALLING) {
            if (DIRECTION == LEFT) {
                if (animationcount < 1)
                    ChangeImage(STATIC_LEFT_0);
                else
                    ChangeImage(STATIC_LEFT_1);

                doing.ChangeAnimation(LEFT+2);
                loc = doing.Animate(GetX(), GetY(), GetW(), GetH());
                this.SetX((int) loc.x);
            } else if (DIRECTION == RIGHT) {
                if (animationcount < 1)
                    ChangeImage(STATIC_RIGHT_0);
                else
                    ChangeImage(STATIC_RIGHT_1);

                doing.ChangeAnimation(RIGHT+2);
                loc = doing.Animate(GetX(), GetY(), GetW(), GetH());
                this.SetX((int) loc.x);
            } else if (DIRECTION == NONE) {
                doing.ReturnVars(0.3f);
            }

            SetY(GetY() + (int)fallrate);
            fallrate *= 1.3;
            if (fallrate > 3){
                fallrate = 3;
            }
        }
        if (STATE == STATIC) {
            fallrate = 0.3f;
            if (DIRECTION == RIGHT) {
                if (animationcount < 1)
                    ChangeImage(STATIC_RIGHT_0);
                else
                    ChangeImage(STATIC_RIGHT_1);
            }
            if (DIRECTION == LEFT) {
                if (animationcount < 1)
                    ChangeImage(STATIC_LEFT_0);
                else
                    ChangeImage(STATIC_LEFT_1);
            }

            doing.ChangeAnimation(0);
            loc = doing.Animate(GetX(), GetY(), GetW(), GetH());
            this.SetX((int) loc.x);
        }
        if (animationcount > 2.0f) {
            animationcount = 0f;
        }
        animationcount += 1 / 30f;
    }

    public void LoadImages(PApplet p) {
        STATIC_LEFT_0 = p.loadImage("Player_Static_Left_0.png");
        STATIC_RIGHT_0 = p.loadImage("Player_Static_Right_0.png");
        STATIC_LEFT_1 = p.loadImage("Player_Static_Left_1.png");
        STATIC_RIGHT_1 = p.loadImage("Player_Static_Right_1.png");
    }


    public int GetState() {
        return STATE;
    }

    public void SetState(int state) {
        STATE = state;
    }

    public void SetDirection(int direction) {
        DIRECTION = direction;
    }

    public int GetDirection() {
        return DIRECTION;
    }

}
