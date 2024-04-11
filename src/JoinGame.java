import DataObjects.Status;

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

    private JTextField playerName;

    private JButton joinGameButton;

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
        container.setBounds(0,0,500, 300);

        //Text area to input ip address
        ipField1 = new JTextField("127");
        ipField2 = new JTextField("45");
        ipField3 = new JTextField("01");
        ipField4 = new JTextField("10");



        playerName = new JTextField("Player2");


        joinGameButton = new JButton("Join game");
        joinGameButton.addActionListener(this);
        container.add(ipField1);
        container.add(ipField2);
        container.add(ipField3);
        container.add(ipField4);
        container.add(playerName);
        container.add(joinGameButton);

        add(container);



        //Text area to input name
        //Button to submit details to server.
        //Server need to reply



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

            //InputHander inputHander = new InputHander();

            //Thread inputHandlerThread = new Thread(inputHander);

            //inputHandlerThread.start();
            VehicleDataObject obj = new VehicleDataObject(2, playerName.getText(), 0,0, 0, false, false, false);
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
        MultiplayerWindow w = new MultiplayerWindow(2, playerName.getText(), ipField1.getText(), ipField2.getText(), ipField3.getText(), ipField4.getText(), 3);
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
            System.out.println("Player Name: " + playerName.getText());

            this.run();
        }
    }

}