import colliders.Checkpoint;
import colliders.ColliderWall;
import colliders.FinishLine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;


import javax.sound.sampled.Clip;



public class MultiplayerWindow extends JFrame implements KeyListener{
    //

    private int playerNumber;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket client;
    private boolean connectionStillAlive = true;




    private Timer timer; //check or update frame
    int TIMER_DELAY;
    private final int ANIMATION_TICK_RATE = 50; //50ms per tick

    int KARTSPEED = 5;



    ColliderWall leftCollider;
    ColliderWall rightCollider;
    ColliderWall topCollider;
    ColliderWall bottomCollider;


    Checkpoint rightCheckpoint;
    Checkpoint topCheckpoint;
    Checkpoint leftCheckpoint;
    MultiplayerGameComponentLayer gameComponentLayer;

    FinishLine finishLine;

    Image frameIcon;
    GameUILayer gameUILayer;

    int MAXLAP = 3;


    public boolean carIsMoving;
    AudioInputStream audioStream;
    Clip clip;

    AudioInputStream crashAudioStream;
    Clip crashClip;


    AudioInputStream bgAudioStream;
    Clip bgClip;

    boolean playBgMusic;

    boolean crashWithWall;

    String playerName;
    String ipAddress1;
    String ipAddress2;
    String ipAddress3;
    String ipAddress4;





    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;




    public MultiplayerWindow(int playerNumber, String name, String ip1, String ip2,String ip3,String ip4, int maxlap){
        this.playerNumber = playerNumber;

        setSize(850, 650);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("2D Forza Horizon");
        frameIcon =  new ImageIcon(getClass().getResource("assets/images/frameIcon.png")).getImage();
        setIconImage(frameIcon);
        this.playerName = name;
        this.ipAddress1 = ip1;
        this.ipAddress2 = ip2;
        this.ipAddress3 = ip3;
        this.ipAddress4 = ip4;



        if(this.playerNumber == 2){
            this.TIMER_DELAY = 80;
        }else {
            this.TIMER_DELAY = 100;
        }
        //Map layer
        MapLayer mapLayer = new MapLayer();
        carIsMoving = false;


        //Game UI layer
        gameUILayer = new GameUILayer(3);



        //Game component layer

        gameComponentLayer = new MultiplayerGameComponentLayer(playerNumber, gameUILayer, maxlap);
        gameComponentLayer.setFocusable(true);
        gameComponentLayer.requestFocus();
        gameComponentLayer.addKeyListener(this);


        //Jlayeredpane to add all layers together
        JLayeredPane container = new JLayeredPane();
        container.setLayout(null);
        container.setBounds(0,0, 850, 650);

        container.add(gameUILayer);
        container.add(gameComponentLayer);
        container.add(mapLayer);

        add(container);


        setVisible(true);
        //Audio

        /*
         */
        try {
            File audioFile = new File("src/assets/audio/raceCarSoundEffectLoop.wav");
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            File bgAudioFile = new File("src/assets/audio/mainMenuTheme.wav");
            bgAudioStream = AudioSystem.getAudioInputStream(bgAudioFile);
            bgClip = AudioSystem.getClip();
            bgClip.open(bgAudioStream);






        }catch (Exception e){
            System.out.println("error1: " + e.getMessage());

        }
        playBgMusic = true;
        playBackgroundMusic();
        StartTimer();





        try {
            String host = ipAddress1 + "." + ipAddress2 + "." + ipAddress3 + "." + ipAddress4;

            client = new Socket(host, 9999);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());


            out.writeObject(new VehicleDataObject(playerNumber,"player1", 350,440,0,true,true,true));
            // Start a separate thread to listen for server responses
            new Thread(() -> {
                try{
                VehicleDataObject data;

                while((data = (VehicleDataObject) in.readObject()) != null){
                    try{
                        updateKartTwoPosition(data);
                    }catch (Exception e){
                        System.out.println("Error 2: " + e.getMessage());
                    }

                }}catch (Exception e){
                    System.out.println("Error 3: " + e.getMessage());

                }

            }).start();
        } catch (Exception e) {
            System.out.println("error3: " + e.getMessage());
        }



    }

    private void updateKartTwoPosition(VehicleDataObject data) {

        if(data.getPlayerNumber() != playerNumber){
            System.out.println("Updating opponent position");
            gameComponentLayer.kartTwo.setLocationx(data.GetLocationX());
            gameComponentLayer.kartTwo.setLocationy(data.GetLocationY());
            gameComponentLayer.kartTwo.setDirection(data.GetDirection());
        }

        if(data.isWin()){
            DisplayGameEndedMessage(data.getPlayerNumber());
        }


    }

    private void playBackgroundMusic() {

        try {

            new Thread(() -> {
                bgClip.loop(Clip.LOOP_CONTINUOUSLY);
                while (playBgMusic) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                bgClip.stop();
                bgClip.close();
            }).start();




        }catch (Exception e){

        }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        char keyChar = e.getKeyChar();

        // Set the corresponding boolean variable to true for each key press
        if (keyChar == 'w') {
            wPressed = true;
            System.out.println("W key pressed: " + wPressed);

        } else if (keyChar == 's') {
            sPressed = true;
        } else if (keyChar == 'a') {
            aPressed = true;
        } else if (keyChar == 'd') {
            dPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // Set the corresponding boolean variable to false for each key release
        if (keyChar == 'w') {
            wPressed = false;
            carIsMoving = false;

        } else if (keyChar == 's') {
            sPressed = false;
        } else if (keyChar == 'a') {
            aPressed = false;
        } else if (keyChar == 'd') {
            dPressed = false;
        }

    }


    private void updateKartMovements() {
        // Update kart movements based on the keys currently pressed
        int kartSpeedX = 0;
        int kartSpeedY = 0;
        int kartTurn = 0;


        if (wPressed) {
            kartSpeedX = 5;
            carIsMoving = true;
        } else if (sPressed) {
            kartSpeedX = -5;
        }

        if (aPressed) {
            kartTurn = 1;
        } else if (dPressed) {
            kartTurn = 0;
        }



        if(!gameComponentLayer.kartCrashed || kartSpeedX ==-5){

            gameComponentLayer.kart.SetKARTSPEED(kartSpeedX, 0);
        }

        if((aPressed || dPressed) && !gameComponentLayer.kartCrashed){
            gameComponentLayer.kart.TurnKart(kartTurn);

        }


    }




    private void playSound() {
        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }


    private void stopSound() {

        clip.stop();

    }

    private void DisplayGameEndedMessage(int winner) {
        timer.stop();
        gameComponentLayer.kart.SetKARTSPEED(0, 0); //stop the vehicle
        gameComponentLayer.kartTwo.SetKARTSPEED(0,0);
        clip.stop();
        clip.close();

        JOptionPane.showMessageDialog(null, ("Player " + winner + " won the round!"), "Round Ended", JOptionPane.PLAIN_MESSAGE);
        dispose();
        playBgMusic = false;
        shutdown();

        MainMenu m = new MainMenu();



    }

    private void DisplayGameOverMessage() {
        timer.stop();
        clip.stop();
        clip.close();

        JOptionPane.showMessageDialog(null, "Game over! Drive safely next time!", "Round Ended", JOptionPane.PLAIN_MESSAGE);

        MainMenu m = new MainMenu();

        playBgMusic = false;
        dispose();
    }



    private void StartTimer(){
        if (timer == null){

            this.timer = new Timer(TIMER_DELAY, new TimerHandler());
            this.timer.start();
        }else{
            //Have timer but not running
            if(!timer.isRunning()){
                this.timer.restart();

            }
        }

    }

    private void sendPositionUpdateToServer() {
        try {
            VehicleData d = gameComponentLayer.getPlayerKartData();
            int xv = d.getX();
            int yv = d.getY();
            int dv = d.getDirection();
            boolean w = d.Won();
            //update server

            out.writeObject(new VehicleDataObject(playerNumber, "kartOne", xv, yv, dv, true, w, false));
            out.flush();

        } catch (Exception e) {
        }
    }


    public void shutdown(){
        connectionStillAlive = false;
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


    class TimerHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            updateKartMovements();

            sendPositionUpdateToServer();


            if(carIsMoving){
                playSound();
            }else{
                stopSound();
            }
            CheckIfPlayerWins();

            if(gameComponentLayer.gameOver){
                PlayCarCrashTrack();
                try {
                    out.writeObject(new VehicleDataObject(playerNumber, "kartOne", 0, 0, 0, true, false, true));
                    out.flush();
                } catch (IOException a) {
                    throw new RuntimeException(a);
                }
                DisplayGameOverMessage();
            }
        }
    }

    private void CheckIfPlayerWins() {

        if(gameComponentLayer.kart.currentLap == MAXLAP){
            try {
                out.writeObject(new VehicleDataObject(playerNumber, "kartOne", 0, 0, 0, true, true, false));
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            DisplayGameEndedMessage(1);
        }
    }

    private void PlayCarCrashTrack() {
        try{
            File crashAudioFile = new File("src/assets/audio/carCrash1.wav");
            crashAudioStream = AudioSystem.getAudioInputStream(crashAudioFile);

            crashClip = AudioSystem.getClip();
            crashClip.open(crashAudioStream);
            crashClip.start();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}




//Send my location
//Receive another kart's location