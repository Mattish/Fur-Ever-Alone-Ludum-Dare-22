import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class Controller extends Frame {
    private int w = 800, h = 622;
    Applet pApplet;

    public Controller() {
        super("Fur Ever Alone");
        setSize(w, h);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pApplet = new Sketch(w, h);
        add(pApplet);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        pApplet.init();
    }

    public static void main(String[] s) {
        new Controller().setVisible(true);
    }
}