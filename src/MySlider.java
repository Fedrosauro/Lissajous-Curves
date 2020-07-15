import javax.swing.*;
import java.awt.*;

public class MySlider extends JSlider {

    private int x, y, width, height;

    public MySlider(int min, int max, int value, int x, int y, int width, int height, Color color){
        super(min, max, value);
        this.x = x; this.y = y; this.height = height; this.width = width;
        setBounds(x, y, width, height);
        setPaintTrack(true);
        setUI(new MyBasicSliderUI(this, color));
        setBackground(Color.black);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
