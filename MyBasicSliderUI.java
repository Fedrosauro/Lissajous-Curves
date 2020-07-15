import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

class MyBasicSliderUI extends BasicSliderUI { //custom UI for JSliders

    private Color color;

    public static int WIheight;

    public MyBasicSliderUI(MySlider mySlider, Color color){
        super(mySlider);
        this.color = color;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


        WIheight = trackRect.height + 20;
        g2d.setColor(Color.white);
        g2d.fillRoundRect(trackRect.x - 6,
                trackRect.y - 10,
                trackRect.width + 11,
                WIheight,
                6, 6);

        g2d.setColor(new Color(226, 226, 226));
        g2d.fillRoundRect(trackRect.x,
                trackRect.y + trackRect.height/2 - 2,
                trackRect.width,
                4,
                0, 0);

        super.paint(g, c);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillRoundRect(trackRect.x,
                trackRect.y + trackRect.height/2 - 2,
                thumbRect.getLocation().x - 5,
                4,
                0, 0);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle t = thumbRect;

        g2d.setColor(color);
        g2d.fillOval(t.x, t.y,t.width,t.height);

    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(15, 15);
    }

    @Override
    protected Color getFocusColor(){
        return (Color.black);
    }
}
