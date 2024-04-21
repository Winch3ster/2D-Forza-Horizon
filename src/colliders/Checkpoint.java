package colliders;

import javax.swing.*;
import java.awt.*;

public class Checkpoint extends JPanel {

    //For a simple square racetrack,
    //The checkpoint on the:
    //          - right track --> 0
    //          - top track --> 1
    //          - left track --> 2


    int checkPointNumber;

    int width;
    int height;
    int x;
    int y;

    public Rectangle collider;
    public Checkpoint(int _checkPointNumber, int _width, int _height, int _x, int _y){
        this.checkPointNumber = _checkPointNumber;
        this.width = _width;
        this.height = _height;
        this.x = _x;
        this.y = _y;
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setOpaque(false);
        this.setVisible(true);
        this.collider = new Rectangle(x, y, width, height);

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color red = new Color(1f,0f,0f,0f );;
        g.setColor(red);

       g.fillRect(0,0,width, height);
    }
}
