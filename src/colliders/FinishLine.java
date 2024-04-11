package colliders;

import javax.swing.*;
import java.awt.*;

public class FinishLine extends JPanel {
    /*
        How the finish line works?

        To activate the finish line, 2 conditions must be met
            1) The vehicle's isInOrder = true.
            2) All the boolean in the checkPoints must be true --? {true, true, true} // left, top, right

     */
    int width;
    int height;
    int x;
    int y;
    public Rectangle collider;

    Image finishLineImage;
    public FinishLine( int _width, int _height, int _x, int _y){
        this.width = _width;
        this.height = _height;
        this.x = _x;
        this.y = _y;
        finishLineImage = new ImageIcon(getClass().getResource("../assets/images/finishLine.jpg")).getImage();

        this.setSize(width, height);
        this.setLocation(x, y);

        this.setVisible(true);
        this.collider = new Rectangle(x, y, width, height);



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color red = Color.BLACK;
        g.setColor(red);

        g.drawRect(0,0,width, height);

        if (finishLineImage != null) {
            g.drawImage(finishLineImage, 0, 0, width, height, this);
        }
    }



}
