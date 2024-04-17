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
import java.io.File;


import javax.sound.sampled.Clip;



public class PracticeWindow extends JFrame implements KeyListener{
    //
    private Timer timer; //check or update frame
    int TIMER_DELAY = 50;
    private final int ANIMATION_TICK_RATE = 50; //50ms per tick

    int KARTSPEED = 5;



    ColliderWall leftCollider;
    ColliderWall rightCollider;
    ColliderWall topCollider;
    ColliderWall bottomCollider;


    Checkpoint rightCheckpoint;
    Checkpoint topCheckpoint;
    Checkpoint leftCheckpoint;
    GameComponentLayer gameComponentLayer;

    FinishLine finishLine;

    Image frameIcon;
    GameUILayer gameUILayer;

    int MAXLAP = 3;


    public boolean carIsMoving;
    AudioInputStream audioStream;
    Clip clip;

    AudioInputStream bgAudioStream;
    Clip bgClip;

    boolean displayedEndGame;
    boolean playBgMusic;

    AudioInputStream crashAudioStream;
    Clip crashClip;

    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private boolean uPressed = false;
    private boolean jPressed = false;
    private boolean hPressed = false;
    private boolean kPressed = false;



    public PracticeWindow(){
        setSize(850, 650);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("2D Forza Horizon");
        frameIcon =  new ImageIcon(getClass().getResource("assets/images/frameIcon.png")).getImage();
        setIconImage(frameIcon);

        //Map layer
        MapLayer mapLayer = new MapLayer();
        carIsMoving = false;


        //Game UI layer
        gameUILayer = new GameUILayer(3);



        //Game component layer

        gameComponentLayer = new GameComponentLayer(gameUILayer, MAXLAP);
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

        displayedEndGame = false;

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

        }
        playBgMusic = true;
        playBackgroundMusic();
        StartTimer();
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
        } else if (keyChar == 'u') {
            uPressed = true;
        } else if (keyChar == 'j') {
            jPressed = true;
        } else if (keyChar == 'h') {
            hPressed = true;
        } else if (keyChar == 'k') {
            kPressed = true;
        }





    }

    @Override
    public void keyReleased(KeyEvent e) {

        char keyChar = e.getKeyChar();

        // Set the corresponding boolean variable to false for each key release
        if (keyChar == 'w') {
            wPressed = false;
            System.out.println("W key pressed: " + wPressed);

        } else if (keyChar == 's') {
            sPressed = false;
        } else if (keyChar == 'a') {
            aPressed = false;
        } else if (keyChar == 'd') {
            dPressed = false;
        } else if (keyChar == 'u') {
            uPressed = false;
        } else if (keyChar == 'j') {
            jPressed = false;
        } else if (keyChar == 'h') {
            hPressed = false;
        } else if (keyChar == 'k') {
            kPressed = false;
        }




    }

    private void updateKartMovements() {
        // Update kart movements based on the keys currently pressed
        int kartSpeedX = 0;
        int kartSpeedY = 0;
        int kartTurn = 0;


        int kartTwoSpeedX = 0;
        int kartTwoSpeedY = 0;
        int kartTwoTurn = 0;



        if (wPressed) {
            kartTwoSpeedX = 5;
        } else if (sPressed) {
            kartTwoSpeedX = -5;
        }

        if (aPressed) {
            kartTwoTurn = 1;
        } else if (dPressed) {
            kartTwoTurn = 0;
        }



        if (uPressed) {
            kartSpeedX = 5;
        } else if (jPressed) {
            kartSpeedX = -5;
        }

        if (hPressed) {
            kartTurn = 1;
        } else if (kPressed) {
            kartTurn = 0;
        }


        if(!gameComponentLayer.kartOneCrashed || kartSpeedX ==-5){
            gameComponentLayer.kart.SetKARTSPEED(kartSpeedX, 0);
        }

        if((hPressed || kPressed) && !gameComponentLayer.kartOneCrashed){
            gameComponentLayer.kart.TurnKart(kartTurn);

        }

        // Repeat the same logic for kartTwo if needed


        //KartTwo --> orange implementing WASD


        if(!gameComponentLayer.kartTwoCrashed || kartTwoSpeedX ==-5){
            gameComponentLayer.kartTwo.SetKARTSPEED(kartTwoSpeedX, 0);
        }

        if((aPressed || dPressed) && !gameComponentLayer.kartTwoCrashed){
            gameComponentLayer.kartTwo.TurnKart(kartTwoTurn);

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
        gameComponentLayer.kart.SetKARTSPEED(0, 0); //stop the vehicle
        clip.stop();
        clip.close();

        JOptionPane.showMessageDialog(null, ("Player " + winner +  " wins! Round ended"), "Round Ended", JOptionPane.PLAIN_MESSAGE);

        MainMenu m = new MainMenu();

        playBgMusic = false;
        displayedEndGame = true;
        dispose();
    }

    private void DisplayGameOverMessage() {
        gameComponentLayer.kart.SetKARTSPEED(0, 0); //stop the vehicle
        gameComponentLayer.kartTwo.SetKARTSPEED(0,0);
        clip.stop();
        clip.close();

        JOptionPane.showMessageDialog(null, "Game over! Drive safely next time!", "Round Ended", JOptionPane.PLAIN_MESSAGE);

        MainMenu m = new MainMenu();

        playBgMusic = false;
        displayedEndGame = true;
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


    class TimerHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(carIsMoving){
                playSound();
            }else{
                stopSound();
            }

            if(gameUILayer.getCurrentMaxLap() == MAXLAP && !displayedEndGame){

                int winner = gameUILayer.getWinner();
                DisplayGameEndedMessage(winner);
            }

            // Update the kart movements based on the keys pressed
            updateKartMovements();

            if(gameComponentLayer.gameOver){
                timer.stop();
                PlayCarCrashTrack();
                DisplayGameOverMessage();

            }
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
