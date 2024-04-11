import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class HostGame extends JFrame implements ActionListener {
    private JButton startGameButton;

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

        container.setBounds(50, 50, 400, 200);
        container.setLayout(null);
        container.setBackground(Color.GREEN);

        try {
            localHost = InetAddress.getLocalHost();
            System.out.println("IP Address: " + localHost.getHostAddress());
        } catch (Exception e) {
            System.out.println("Unable to get IP Address: " + e.getMessage());
        }

        JLabel instruction = new JLabel("Show your friend your ip and you are good to go!");
        instruction.setBounds(0,0,400, 50);
        JLabel label = new JLabel(localHost.getHostAddress());
        label.setBounds(0,50, 200,50);
        startGameButton = new JButton("Start Game");
        startGameButton.setBounds(0, 100, 100, 50);
        startGameButton.setCursor(new Cursor(12));
        startGameButton.addActionListener(this);
        container.add(instruction);
        container.add(label);
        container.add(startGameButton);

        add(container);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startGameButton){
            //Check if friend is ready
            Server s = new Server();
            s.runServer();
            MultiplayerWindow w = new MultiplayerWindow(1, "Player1", "127", "0", "0", "1", 3);
            dispose();
        }
    }
}
