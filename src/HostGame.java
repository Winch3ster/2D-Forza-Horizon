import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class HostGame extends JFrame implements ActionListener {
    private CustomButton startGameButton;

    Image frameIcon;

    InetAddress localHost;
    public HostGame(){
        setSize(500, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("2D Forza Horizon");
        frameIcon =  new ImageIcon(getClass().getResource("assets/images/frameIcon.png")).getImage();
        setIconImage(frameIcon);

        JPanel container = new JPanel();

        container.setBounds(50, 20, 400, 200);
        container.setLayout(null);

        try {
            localHost = InetAddress.getLocalHost();
            System.out.println("IP Address: " + localHost.getHostAddress());
        } catch (Exception e) {
            System.out.println("Unable to get IP Address: " + e.getMessage());
        }

        JLabel instruction = new JLabel("Show your friend your IP.");
        JLabel instructionLine2 = new JLabel("Once he entered it into the prompted fields in his view,");
        JLabel instructionLine3 = new JLabel("you are good to go!");

        instruction.setBounds(0,0,400, 50);
        instructionLine2.setBounds(0,30,400, 50);
        instructionLine3.setBounds(0,50,400, 50);

        JLabel IPlabel = new JLabel("Your IP: " + localHost.getHostAddress());
        IPlabel.setFont(new Font("Calibri", Font.BOLD, 20));

        IPlabel.setBounds(0,90, 200,50);
        startGameButton = new CustomButton("Start Game", new Color(224, 101,76), Color.white);
        startGameButton.setBounds(0, 140, 250, 50);
        startGameButton.setCursor(new Cursor(12));
        startGameButton.addActionListener(this);
        container.add(instruction);
        container.add(instructionLine2);
        container.add(instructionLine3);

        container.add(IPlabel);
        container.add(startGameButton);

        add(container);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startGameButton){
            //Check if friend is ready
            MultiplayerWindow w = new MultiplayerWindow(1,  "127", "0", "0", "1", 3);
            dispose();
        }
    }
}
