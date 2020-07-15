import javax.swing.*;
import java.awt.*;

public class Parametres extends JPanel {

    /*
    In this class we will set parametres for graphics
     */

    public static MySlider mySlidera;
    public static MySlider mySliderb;
    public static MySlider mySliderA;
    public static MySlider mySliderB;
    public static MySlider mySliderPhase;

    private int startX;
    private int stepY;
    public static int sHeight;
    public static int bigOffsetY;
    public static int bigOffsetX;

    public Parametres(){
        setup();
    }

    public void setup(){
        setLayout(null);
        setBackground(Color.black);

        startX = 5;
        stepY = 0;

        sHeight = 45;
        bigOffsetY = 20;
        bigOffsetX = 500;

        mySlidera = new MySlider(1, 20, 10,
                startX, stepY,
                250, sHeight,
                new Color(254, 95, 85));

        stepY += mySlidera.getHeight() + 5;

        mySliderb = new MySlider(1, 20, 10,
                startX, stepY,
                250, sHeight,
                new Color(123, 158, 135));

        stepY += mySliderb.getHeight() + 5;

        mySliderA = new MySlider(1, 180, 90,
                startX, stepY,
                250, sHeight,
                new Color(94, 116, 127));

        stepY += mySliderA.getHeight() + 5;

        mySliderB = new MySlider(1, 180, 90,
                startX, stepY,
                250, sHeight,
                new Color(250, 188, 42));

        stepY += mySliderB.getHeight() + 5;

        mySliderPhase = new MySlider(0, 200, 100,
                startX, stepY,
                250, sHeight,
                new Color(96, 49, 64));

        setBounds(bigOffsetX, bigOffsetY, mySlidera.getWidth() + 10,
                stepY + mySliderPhase.getHeight() + 5);

        this.add(mySlidera);
        this.add(mySliderb);
        this.add(mySliderA);
        this.add(mySliderB);
        this.add(mySliderPhase);
    }

    public static void setAllOff(){
        mySlidera.setEnabled(false);
        mySliderb.setEnabled(false);
        mySliderA.setEnabled(false);
        mySliderB.setEnabled(false);
        mySliderPhase.setEnabled(false);
    }

    public static void setAllOn(){
        mySlidera.setEnabled(true);
        mySliderb.setEnabled(true);
        mySliderA.setEnabled(true);
        mySliderB.setEnabled(true);
        mySliderPhase.setEnabled(true);
    }

    public static void resetValues(){
        mySlidera.setValue(10);
        mySliderb.setValue(10);
        mySliderA.setValue(90);
        mySliderB.setValue(90);
        mySliderPhase.setValue(100);
    }
}
