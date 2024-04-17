import colliders.Checkpoint;
import colliders.ColliderWall;
import colliders.FinishLine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MultiplayerGameComponentLayer extends JPanel {

    int ANIMATION_TICK = 50;
    ColliderWall leftCollider;
    ColliderWall rightCollider;
    ColliderWall topCollider;
    ColliderWall bottomCollider;
    ColliderWall middleCollider;



    Checkpoint rightCheckpoint;
    Checkpoint topCheckpoint;
    Checkpoint leftCheckpoint;

    public KartAnimatorPanel kart;
    public KartAnimatorPanel kartTwo;

    FinishLine finishLine;

    Timer gameTimer;

    GameUILayer gameUILayer;

    AudioInputStream crashAudioStream;
    Clip crashClip;

    boolean kartCrashed = false;

    boolean crashedWithWall = false;

    boolean isCheckingCollisionEnter = true;

    int kartCrashPosition;
    int playerNumber;
    //Consist of colliders and vehicle

    boolean gameOver = false;
    public MultiplayerGameComponentLayer(int playerNumber, GameUILayer _gameUILayer, int maxlap){
        setOpaque(false);
        setLayout(null);
        setBounds(0,0, 850, 650);

        this.gameUILayer = _gameUILayer;
        this.playerNumber = playerNumber;
        if(playerNumber == 1){
            kart = new KartAnimatorPanel("kartCyan", "kartOne", 350, 500, maxlap);
            add(kart);

            kartTwo = new KartAnimatorPanel("kartOrange", "kartTwo", 350, 440, maxlap);
            add(kartTwo);
        }else{
            kart = new KartAnimatorPanel("kartOrange", "kartTwo", 350, 440, maxlap);
            add(kart);


            kartTwo = new KartAnimatorPanel("kartCyan", "kartOne", 350, 500, maxlap);
            add(kartTwo);
        }



        // wall colliders so vehicle will not exit the map
        leftCollider = new ColliderWall(20, 510, 35, 65);
        rightCollider = new ColliderWall(20, 510, 780, 65);
        topCollider = new ColliderWall(740, 20, 40, 65);
        bottomCollider = new ColliderWall(740, 20, 40, 550);
        middleCollider = new ColliderWall(320, 128, 270, 267);


        add(leftCollider);
        add(rightCollider);
        add(topCollider);
        add(bottomCollider);
        add(middleCollider);


        //Finish line configurations
        rightCheckpoint = new Checkpoint(0, 180, 20, 610, 300);
        topCheckpoint = new Checkpoint(1, 20, 200, 450, 65);
        leftCheckpoint = new Checkpoint(2, 190, 20, 65, 300);

        add(rightCheckpoint);
        add(topCheckpoint);
        add(leftCheckpoint);


        finishLine = new FinishLine(20, 145, 450, 405);
        add(finishLine);



        kart.StartAnimation();
        kartTwo.StartAnimation();

        StartTimer();


    }

    private void StartTimer() {


        if (gameTimer == null){
            gameTimer = new Timer(ANIMATION_TICK, new TimerHandler());
            this.gameTimer.start();
        }else{
            //Have timer but not running
            if(!gameTimer.isRunning()){
                this.gameTimer.restart();

            }
        }

    }


    class TimerHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {


            if(isCheckingCollisionEnter){
                CheckCollisionEnter();
            }else{
                CheckCollisionExit();
            }

            CheckPointsCollision();
            CheckCarCrash();
        }
    }

    public void CheckCollisionEnter(){
        // 0 --> left; 1 --> right; 2 --> top; 3 --> bottom;
        if(kart.rigidBody.intersects(bottomCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartCrashed = true;
            isCheckingCollisionEnter = false;
            kartCrashPosition = 3;

        }


        if(kart.rigidBody.intersects(leftCollider.collider)) {
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartCrashed = true;
            isCheckingCollisionEnter = false;
            kartCrashPosition = 0;

        }

        if(kart.rigidBody.intersects(rightCollider.collider)) {
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartCrashed = true;
            isCheckingCollisionEnter = false;
            kartCrashPosition = 1;


        }

        if(kart.rigidBody.intersects(topCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartCrashed = true;
            isCheckingCollisionEnter = false;
            kartCrashPosition = 2;
        }

        if(kart.rigidBody.intersects(middleCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartCrashed = true;
            isCheckingCollisionEnter = false;
            kartCrashPosition = 4;
        }
    }

    public void CheckCollisionExit(){


        switch(kartCrashPosition){
            case 0 :
                if (!kart.rigidBody.intersects(leftCollider.collider)) {
                    kartCrashed = false;
                    isCheckingCollisionEnter = true;
                }
                break;
            case 1:
                if (!kart.rigidBody.intersects(rightCollider.collider)) {
                    kartCrashed = false;
                    isCheckingCollisionEnter = true;
                }
                break;
            case 2:
                if(!kart.rigidBody.intersects(topCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartCrashed =false;
                    isCheckingCollisionEnter = true;
                }
                break;
            case 3:
                if(!kart.rigidBody.intersects(bottomCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartCrashed =false;
                    isCheckingCollisionEnter = true;

                }
                break;
            case 4:
                if(!kart.rigidBody.intersects(middleCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartCrashed =false;
                    isCheckingCollisionEnter = true;

                }
                break;

        }

    }






    private void CheckCarCrash() {

        if(kart.rigidBody.intersects(kartTwo.rigidBody)) {
            gameOver = true;

        }
    }

    public void CheckPointsCollision(){




        //Checkpoints collision
        if(kart.rigidBody.intersects(rightCheckpoint.collider)){
            //playerKart.SetKARTSPEED(0, 0);

            kart.checkPointReached[0] = true;
            kart.isInOrder = false;

        }
        if(kart.rigidBody.intersects(topCheckpoint.collider)){
            //playerKart.SetKARTSPEED(0, 0);

            kart.checkPointReached[1] = true;

        }

        if(kart.rigidBody.intersects(leftCheckpoint.collider)){
            //playerKart.SetKARTSPEED(0, 0);

            kart.checkPointReached[2] = true;
            kart.isInOrder = true;
        }


        //Finish line
        if(kart.rigidBody.intersects(finishLine.collider)){
            //playerKart.SetKARTSPEED(0, 0);
            if(kart.checkPointReached[0] == true && kart.checkPointReached[1] == true && kart.checkPointReached[2] == true && kart.isInOrder){
                kart.score++;
                kart.IncrementLap();
                System.out.println("player score: " + kart.score);

                if(playerNumber == 1){
                    gameUILayer.setCurrentLapPlayer1(kart.score);

                }else{
                    gameUILayer.setCurrentLapPlayer2(kart.score);

                }

            }
            //Reset
            kart.checkPointReached = new boolean[]{false, false, false};

            kart.isInOrder = false;
        }
    }


    private void playCrashSound(){
        try{

            File crashAudioFile = new File("src/assets/audio/carCrash2.wav");
            crashAudioStream = AudioSystem.getAudioInputStream(crashAudioFile);


            crashClip = AudioSystem.getClip();
            crashClip.open(crashAudioStream);
            crashClip.start();



        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

    public VehicleData getPlayerKartData(){

        return (new VehicleData(kart.getLocationx(), kart.getLocationy(), kart.getDirection(), kart.FinishedLap()));
    }


}
