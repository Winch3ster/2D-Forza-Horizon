import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {

    private Color buttonColor;
    private Color hoverColor;
    private boolean hover;
    public CustomButton(String label, Color _buttonColor, Color _hoverColor){

        super(label);
        this.buttonColor =_buttonColor;
        this.hoverColor = _hoverColor;
        this.hover = false;

        setCursor(new Cursor(12));
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.white);
        setFont(new Font("Calibri", Font.PLAIN, 35));
        setLocation(50, 50);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                setForeground(buttonColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                setForeground(Color.white);

                repaint();
            }
        });

    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(buttonColor);

        g2d.fillRoundRect(0,0, getWidth(), getHeight(), 15, 15);

        if(hover){

            g2d.setColor(hoverColor);

            
            g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()-5,11, 11);

        }else{
            g2d.setColor(buttonColor);
            g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()-5,15, 15);
        }

        //Border


        //inner fill
        super.paintComponent(g2d);

        g2d.dispose();
    }
}
