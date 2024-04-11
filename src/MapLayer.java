import javax.swing.*;
import java.awt.*;

public class MapLayer extends JPanel {

    Image mapImage;
    public MapLayer(){
        setBounds(-10,-10, 850, 650);
        //setBackground(Color.GREEN);

        mapImage =  new ImageIcon(getClass().getResource("assets/images/racetrack.png")).getImage();


    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, 850, 650, this);
        }
    }
}
