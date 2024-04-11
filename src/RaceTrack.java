import javax.swing.*;
import java.awt.*;

public class RaceTrack extends JPanel {

    public RaceTrack(){
        this.setSize(850, 650);
        this.setVisible(true);

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

        Color c1 = Color.green;
        g.setColor(c1);
        g.fillRect(150, 200, 550, 300); // grass

        Color c2 = Color.black;
        g.setColor(c2);
        g.drawRect(50, 100, 750, 500);  // outer edge
        g.drawRect(150, 200, 550, 300); // inner edge

        Color c3 = Color.yellow;
        g.setColor(c3);
        g.drawRect(100, 150, 650, 400); // mid-lane marker

        Color c4 = Color.white;
        g.setColor(c4);
        g.drawLine(425, 500, 425, 600); // start line





    }
}
