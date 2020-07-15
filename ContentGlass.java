import javax.swing.*;
import java.awt.*;

public class ContentGlass extends JPanel {

    public static int WIDTH;
    public static int HEIGHT;

    private GlassPane glassPane;
    private Parametres parametres;
    private PaneGraphic paneGraphic;

    public ContentGlass(){
        setup();
    }

    public void setup(){
        WIDTH = 900;
        HEIGHT = 500;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setLayout(new BorderLayout());
        glassPane = new GlassPane();
        add(glassPane, BorderLayout.CENTER);

        parametres = new Parametres(); //for the parametres
        paneGraphic = new PaneGraphic(); //the rest

        glassPane.addPanel(paneGraphic);
        glassPane.addPanel(parametres);
    }
}

class GlassPane extends JLayeredPane { //I use this JLayeredPane to add panels on top of others

    private int i;

    public GlassPane(){
        i = 0;
        setOpaque(true);
    }

    public void addPanel(JPanel jpanel){
        add(jpanel, i, 0);
        i++;
    }
}
