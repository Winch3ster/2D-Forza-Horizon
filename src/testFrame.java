import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class testFrame extends JFrame implements KeyListener, ActionListener {

    private CustomButton n;
    public testFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.addKeyListener(this);

        n = new CustomButton("Test button", new Color(224, 101,76), Color.white);




        n.addActionListener(this);

        JPanel container = new JPanel();
        container.setBounds(10, 10, 500, 100);
        container.setBackground(Color.GREEN);
        container.add(n);

        this.add(container);

        this.setVisible(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == n){
            System.out.println("Button clicked ");
        }
    }
}
