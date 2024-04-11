import javax.swing.*;
import java.awt.*;

public class BgPanel extends JPanel {


    private Image image;

    public BgPanel() {
        this.image = new ImageIcon(getClass().getResource("assets/images/mainMenuBg.jpg")).getImage();
        this.setLocation(0,0);
        this.setSize(new Dimension(850, 650));
        //this.setBackground(Color.magenta);

    }


    protected void paintComponent(Graphics g) {


        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, 850, 650, this);
        }
    }



}
