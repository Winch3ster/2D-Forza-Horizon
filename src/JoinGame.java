import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class JoinGame extends JFrame implements Runnable, ActionListener {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket client;
    private boolean connectionStillAlive = true;


    private JTextField ipField1;
    private JTextField ipField2;
    private JTextField ipField3;
    private JTextField ipField4;

    private CustomButton joinGameButton;

    Image frameIcon;
    //Try to talk to server first
    //See if server respond
    public JoinGame(){
        setSize(500, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("2D Forza Horizon");
        frameIcon =  new ImageIcon(getClass().getResource("assets/images/frameIcon.png")).getImage();
        setIconImage(frameIcon);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(3, 1));
        container.setBounds(0,0,500, 280);

        JPanel instructionContainer = new JPanel();
        instructionContainer.setLayout(null);
        JLabel instruction = new JLabel("Enter the IP address shown on\nyour friend's PC to start!");
        instruction.setBounds(20, 30, 500, 40);
        instructionContainer.add(instruction);

        JPanel ipContainer = new JPanel();
        ipContainer.setLayout(null);

        JLabel ipLabel = new JLabel("IP Address: ");
        ipLabel.setBounds(20,0, 100, 30);
        //Text area to input ip address
        ipField1 = new JTextField("127");
        ipField1.setBounds(130, 0, 50, 30);

        JLabel dot1 = new JLabel(".");

        ipField2 = new JTextField("45");
        ipField2.setBounds(190, 0, 50, 30);
        JLabel dot2 = new JLabel(".");

        ipField3 = new JTextField("01");
        ipField3.setBounds(250, 0, 50, 30);
        JLabel dot3 = new JLabel(".");

        ipField4 = new JTextField("10");
        ipField4.setBounds(340, 0, 50, 30);

        ipContainer.add(ipLabel);
        ipContainer.add(ipField1);
        ipContainer.add(dot1);
        ipContainer.add(ipField2);
        ipContainer.add(dot2);
        ipContainer.add(ipField3);
        ipContainer.add(dot3);
        ipContainer.add(ipField4);


        JPanel buttonPanel= new JPanel();
        buttonPanel.setLayout(null);
        joinGameButton = new CustomButton("Join game", new Color(224, 101,76), Color.white);
        joinGameButton.setBounds(140, 0, 200, 50);
        joinGameButton.addActionListener(this);
        buttonPanel.add(joinGameButton);
        container.add(instructionContainer);
        container.add(ipContainer);
        container.add(buttonPanel);

        add(container);


        setVisible(true);
    }


    @Override
    public void run() {
        try{
            String host =  ipField1.getText() + "." + ipField2.getText() + "." + ipField3.getText() + "." + ipField4.getText();
            System.out.println("From run host: " + host);
            client = new Socket(host, 9999);
            in = new ObjectInputStream (client.getInputStream());
            out = new ObjectOutputStream (client.getOutputStream());


            VehicleDataObject obj = new VehicleDataObject(3,0,0, 0, false, false, false);
            out.writeObject(obj);
            out.flush();


            VehicleDataObject inMessage;
            inMessage = (VehicleDataObject) in.readObject();

            if(inMessage.isConnectionStatus()){
                OpenMultiplayerWindow();
            }
        }catch (IOException e){
            //TODO: Handle
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void OpenMultiplayerWindow() {
        //shutdown();

        System.out.println("OpenMultiplayerWindow");
        MultiplayerWindow w = new MultiplayerWindow(2, ipField1.getText(), ipField2.getText(), ipField3.getText(), ipField4.getText(), 3);
        dispose();
    }

    //Will run on 2 threads one to receive response from server and 1 to send request or message to server


    public void shutdown(){
        try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }
        }catch (Exception e){
            shutdown();
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == joinGameButton){
            System.out.println("Button clicked ");
            System.out.println("IP Field 1: " + ipField1.getText());
            System.out.println("IP Field 2: " + ipField2.getText());
            System.out.println("IP Field 3: " + ipField3.getText());
            System.out.println("IP Field 4: " + ipField4.getText());
            this.run();
        }
    }

}
