import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class PaneGraphic extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    /*
    In this class we draw everything we need to draw
     */

    private final int DELAY = 5;
    private Timer timer;

    private boolean draw; //if we draw we disable the button we clicked (button 1)

    private double figureTime;
    private ArrayList<Shape> graph; //in this ArrayList we will store each point of the graph

    private double x, y;

    private Rectangle2D rect1; //DRAW FIGURE button
    private int x1, y1;
    private int width1, height1;
    private boolean clicked1, change1;

    private Rectangle2D rect2; //NEW ONE button
    private int x2;
    private boolean clicked2, change2;

    private int GoffsetX, GoffsetY, LSquare;

    public PaneGraphic(){
        setup();
        initTimer();
        addMouseListener(this);
        addMouseMotionListener(this);
        addWindowListener(new WindowAdapter() { //stops the timer if we close the window
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = getTimer();
                timer.stop();
            }
        });
    }

    public void setup(){
        setBounds(0, 0, ContentGlass.WIDTH,  ContentGlass.HEIGHT);
        setBackground(Color.black);

        figureTime = 0;
        graph = new ArrayList<>(); //new figure = new graph

        width1 = 160;
        height1 = 40;
        x1 = Parametres.bigOffsetX + 13;
        y1 = Parametres.mySliderPhase.getY() + 80;
        rect1= new Rectangle2D.Float(x1, y1, width1, height1);

        x2 = x1 + width1 + 10;
        rect2= new Rectangle2D.Float(x2, y1, width1, height1);

        GoffsetX = 245; GoffsetY = 230; LSquare = 400;

        clicked1 = false; change1 = false;

        clicked2 = false; change2 = false;

        draw = false;
    }

    public void initTimer(){
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer(){
        return timer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        if(draw && figureTime <= 15) figureTime += 0.005;
        //this is my way to try to draw a good looking graph
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Stroke defaultStroke = g2d.getStroke();

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);


        g2d.setRenderingHints(rh);

        drawVariables(g2d);

        g2d.setColor(Color.white);

        if(figureTime <= 10 && draw) {
            x = Parametres.mySliderA.getValue() * Math.sin(Parametres.mySlidera.getValue() * figureTime + 2 * Parametres.mySliderPhase.getValue());
            y = Parametres.mySliderB.getValue() * Math.sin(Parametres.mySliderb.getValue() * figureTime);

            Shape line = new Line2D.Double(x + GoffsetX, y + GoffsetY, x + GoffsetX, y + GoffsetY);
            graph.add(line);
        }

        g2d.setStroke(new BasicStroke(0.5f));
        g2d.setColor(new Color(104, 104, 104));
        g2d.drawRect(45, 30, LSquare, LSquare);

        Shape lineX = new Line2D.Double(x + GoffsetX, 30, x + GoffsetX, 30 + LSquare);
        g2d.draw(lineX);

        Shape lineY = new Line2D.Double(45, y + GoffsetY, 45 + LSquare, y + GoffsetY);
        g2d.draw(lineY);

        g2d.setFont(new Font("Tahoma", Font.BOLD, 12));
        g2d.setStroke(new BasicStroke(8));
        g2d.setColor(new Color(255, 0, 0));
        Shape PointX = new Line2D.Double(x + GoffsetX, 30 + LSquare, x + GoffsetX, 30 + LSquare);
        g2d.draw(PointX);
        g2d.drawString(String.format("%.1f",x), (float)(x + GoffsetX + 8), (float)(30 + LSquare - 8));

        g2d.setColor(new Color(16, 0, 255));
        Shape PointY = new Line2D.Double(45, y + GoffsetY, 45, y + GoffsetY);
        g2d.draw(PointY);
        g2d.drawString(String.format("%.1f",y), (float)(45 + 8), (float)(y + GoffsetY - 8));

        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(2));

        for (int i = 0; i < graph.size(); i++) {
            g2d.draw(graph.get(i));
        }

        g2d.setStroke(new BasicStroke(8));
        g2d.setColor(new Color(38, 220, 0));
        Shape pointDraw = new Line2D.Double(x + GoffsetX, y + GoffsetY, x + GoffsetX, y + GoffsetY);
        g2d.draw(pointDraw);

        drawEquations(g2d);

        g2d.setStroke(defaultStroke);

        drawButtons(g2d);
    }

    public void drawEquations(Graphics2D g2d){
        g2d.setFont(new Font("Times New Roman", Font.ITALIC, 23));
        g2d.setColor(Color.white);
        g2d.drawString("Equations :", x1, y1 + height1 + 30);
        g2d.drawString("= A sin ( at + ϕ )", x1 + 17, y1 + height1 + 60);
        g2d.drawString("= B sin ( bt )", x1 + 17, y1 + height1 + 90);
        g2d.setColor(new Color(255, 0, 0));
        g2d.drawString("x", x1, y1 + height1 + 60);
        g2d.setColor(new Color(16, 0, 255));
        g2d.drawString("y", x1, y1 + height1 + 90);
    }

    public void drawButtons(Graphics2D g2d){
        g2d.setFont(new Font("Tahoma", Font.BOLD, 18));
        int textWidth1 = g2d.getFontMetrics().stringWidth("DRAW FIGURE");
        int textWidth2 = g2d.getFontMetrics().stringWidth("NEW ONE");
        if(change1 && !draw) {
            g2d.setColor(Color.white);
            g2d.fillRect(x1, y1, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("DRAW FIGURE",x1 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(Color.white);
            g2d.drawRect(x1, y1, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("DRAW FIGURE",x1 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        }

        if(draw) {
            g2d.setColor(new Color(68, 68, 68));
            g2d.drawRect(x1, y1, width1, height1);
            g2d.setColor(new Color(68, 68, 68));
            g2d.drawString("DRAW FIGURE",x1 + (width1 - textWidth1)/2, y1 + height1/2 + 8);
        }

        if(change2) {
            g2d.setColor(Color.white);
            g2d.fillRect(x2, y1, width1, height1);
            g2d.setColor(Color.black);
            g2d.drawString("NEW ONE",x2 + (width1 - textWidth2)/2, y1 + height1/2 + 8);
        } else{
            g2d.setColor(Color.white);
            g2d.drawRect(x2, y1, width1, height1);
            g2d.setColor(Color.white);
            g2d.drawString("NEW ONE",x2 + (width1 - textWidth2)/2, y1 + height1/2 + 8);
        }
    }

    public void drawVariables(Graphics2D g2d){
        g2d.setFont(new Font("Tahoma", Font.BOLD, 18));
        int offsetX = 30;
        int offsetY = 4;
        int startX = Parametres.mySlidera.getX() + Parametres.mySlidera.getWidth() + offsetX + Parametres.bigOffsetX;

        g2d.setColor(Color.white);
        g2d.fillRoundRect(startX - 10, Parametres.mySlidera.getY() + Parametres.bigOffsetY + offsetY, 80, MyBasicSliderUI.WIheight,6,6);
        g2d.setColor(Color.white);
        g2d.fillRoundRect(startX - 10, Parametres.mySliderb.getY() + Parametres.bigOffsetY + offsetY, 80, MyBasicSliderUI.WIheight,6,6);
        g2d.setColor(Color.white);
        g2d.fillRoundRect(startX - 10, Parametres.mySliderA.getY() + Parametres.bigOffsetY + offsetY, 80, MyBasicSliderUI.WIheight,6,6);
        g2d.setColor(Color.white);
        g2d.fillRoundRect(startX - 10, Parametres.mySliderB.getY() + Parametres.bigOffsetY + offsetY, 80, MyBasicSliderUI.WIheight,6,6);
        g2d.setColor(Color.white);
        g2d.fillRoundRect(startX - 10, Parametres.mySliderPhase.getY() + Parametres.bigOffsetY + offsetY, 80, MyBasicSliderUI.WIheight,6,6);

        g2d.setColor(new Color(254, 95, 85));
        g2d.drawString("a : " + Parametres.mySlidera.getValue(), startX,
                Parametres.mySlidera.getY() + Parametres.mySlidera.getHeight()/2 + offsetY + Parametres.bigOffsetY);

        g2d.setColor(new Color(123, 158, 135));
        g2d.drawString("b : " + Parametres.mySliderb.getValue(), startX,
                Parametres.mySliderb.getY() + Parametres.mySliderb.getHeight()/2 + offsetY + Parametres.bigOffsetY);

        g2d.setColor(new Color(94, 116, 127));
        g2d.drawString("A : " + Parametres.mySliderA.getValue(), startX,
                Parametres.mySliderA.getY() + Parametres.mySliderA.getHeight()/2 + offsetY + Parametres.bigOffsetY);

        g2d.setColor(new Color(250, 188, 42));
        g2d.drawString("B : " + Parametres.mySliderB.getValue(), startX,
                Parametres.mySliderB.getY() + Parametres.mySliderB.getHeight()/2 + offsetY + Parametres.bigOffsetY);

        g2d.setColor(new Color(96, 49, 64));
        g2d.drawString("\u0278 : " + (double) Parametres.mySliderPhase.getValue()/100, startX - 5,
                Parametres.mySliderPhase.getY() + Parametres.mySliderPhase.getHeight()/2 + offsetY + Parametres.bigOffsetY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y) && !draw) {
            change1 = true;
        } else change1 = false;

        if (rect2.contains(x, y)) {
            change2 = true;
        } else change2 = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rect1.contains(x, y) && !draw) {
            draw = true;
            Parametres.setAllOff();
        }

        if (rect2.contains(x, y)) {
            setup();
            Parametres.resetValues();
            Parametres.setAllOn();
        }
    }

    ////////////////////////////////////////////////////
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    ////////////////////////////////////////////////////
}
