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

    boolean crashWithWall;
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



        switch (e.getKeyChar()){
            case 'w':
                carIsMoving = true;

                 gameComponentLayer.kart.SetKARTSPEED(-5, 0);

            case 's':
                gameComponentLayer.kart.SetKARTSPEED(5, 0);

                break;
            case 'a':
                gameComponentLayer.kart.TurnKart(1);

                //kartOne.setLocation(kartOne.getX(), kartOne.getY()-2);

                break;
            case 'd':
                gameComponentLayer.kart.TurnKart(0);
                //kartOne.setLocation(kartOne.getX(), kartOne.getY()+2);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {



        switch (e.getKeyChar()){
            case 'w':
                carIsMoving = false;
                gameComponentLayer.kart.SetKARTSPEED(0, 0);
                //kartOne.updateLocation(1, 0);
                break;
            case 's':


                gameComponentLayer.kart.SetKARTSPEED(0, 0);
                break;
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

    private void DisplayGameEndedMessage() {
        gameComponentLayer.kart.SetKARTSPEED(0, 0); //stop the vehicle
        clip.stop();
        clip.close();

        JOptionPane.showMessageDialog(null, "Congratulation! Round ended", "Round Ended", JOptionPane.PLAIN_MESSAGE);

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

            if(gameUILayer.getCurrentLap() == MAXLAP && !displayedEndGame){
                DisplayGameEndedMessage();
            }
        }
    }
}
