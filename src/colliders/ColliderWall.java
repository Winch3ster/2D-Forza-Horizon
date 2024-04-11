package colliders;

import javax.swing.*;
import java.awt.*;

public class ColliderWall extends JPanel {


    int width;
    int height;
    int x;
    int y;


    public Rectangle collider;
    public ColliderWall(int width, int height, int x, int y){

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        this.setSize(width, height);
        this.setLocation(x, y);

        this.setVisible(true);
        this.collider = new Rectangle(x, y, width, height);

        /*
        colliderDirection

        0)Top
        1)Left
        2)Bottom
        3)Right

         */

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color red = Color.magenta;
        g.setColor(red);

        g.fillRect(0, 0, width, height);

    }


}
