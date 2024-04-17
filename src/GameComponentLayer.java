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
import java.sql.SQLOutput;

public class GameComponentLayer extends JPanel {

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





    boolean isCheckingCollisionEnter;
    boolean isCheckingCollisionEnterKartTwo;
    boolean kartOneCrashed;
    boolean kartTwoCrashed;

    boolean gameOver = false;

    int kartOneCrashPosition;
    int kartTwoCrashPosition;
    // 0 --> left; 1 --> right; 2 --> top; 3 --> bottom; 4 --> middle




    //Consist of colliders and vehicle
    public GameComponentLayer(GameUILayer _gameUILayer, int maxLap){
        setOpaque(false);
        setLayout(null);
        setBounds(0,0, 850, 650);

        this.gameUILayer = _gameUILayer;

        kart = new KartAnimatorPanel("kartCyan", "kartOne", 350, 500, maxLap);
        add(kart);

        kartTwo = new KartAnimatorPanel("kartOrange", "kartTwo", 350, 440, maxLap);
        add(kartTwo);





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




        isCheckingCollisionEnter = true;
        isCheckingCollisionEnterKartTwo = true;
        kartOneCrashed = false;
        kartTwoCrashed = false;


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

            //check for collision enter
            //check for collision exit
            if(isCheckingCollisionEnter){
                CheckCollisionEnter();
            }else{
                CheckCollisionExit();
            }

            if(isCheckingCollisionEnterKartTwo){
                CheckCollisionEnterKartTwo();
            }else{
                CheckCollisionExitKartTwo();
            }

            CheckPointsCollisionKartOne();
            CheckPointsCollisionKartTwo();
            CheckCarCrash();
        }
    }

    private void CheckCarCrash() {

        if(kart.rigidBody.intersects(kartTwo.rigidBody)) {
            gameOver = true;

        }
    }

    private void CheckCollisionExitKartTwo() {

        switch(kartTwoCrashPosition){
            case 0 :
                if (!kartTwo.rigidBody.intersects(leftCollider.collider)) {
                    kartTwoCrashed = false;
                    isCheckingCollisionEnterKartTwo = true;
                }
                break;
            case 1:
                if (!kartTwo.rigidBody.intersects(rightCollider.collider)) {
                    kartTwoCrashed = false;
                    isCheckingCollisionEnterKartTwo = true;
                }
                break;
            case 2:
                if(!kartTwo.rigidBody.intersects(topCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartTwoCrashed =false;
                    isCheckingCollisionEnterKartTwo = true;
                }
                break;
            case 3:
                if(!kartTwo.rigidBody.intersects(bottomCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartTwoCrashed =false;
                    isCheckingCollisionEnterKartTwo = true;

                }
                break;
            case 4:
                if(!kartTwo.rigidBody.intersects(middleCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartTwoCrashed =false;
                    isCheckingCollisionEnterKartTwo = true;

                }
                break;

        }



    }

    private void CheckCollisionEnterKartTwo() {

        if(kartTwo.rigidBody.intersects(bottomCollider.collider)){
            kartTwo.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartTwoCrashed = true;
            isCheckingCollisionEnterKartTwo = false;
            kartTwoCrashPosition = 3;

        }


        if(kartTwo.rigidBody.intersects(leftCollider.collider)) {
            kartTwo.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartTwoCrashed = true;
            isCheckingCollisionEnterKartTwo = false;
            kartTwoCrashPosition = 0;

        }

        if(kartTwo.rigidBody.intersects(rightCollider.collider)) {
            kartTwo.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartTwoCrashed = true;
            isCheckingCollisionEnterKartTwo = false;
            kartTwoCrashPosition = 1;


        }

        if(kartTwo.rigidBody.intersects(topCollider.collider)){
            kartTwo.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartTwoCrashed = true;
            isCheckingCollisionEnterKartTwo = false;
            kartTwoCrashPosition = 2;
        }

        if(kartTwo.rigidBody.intersects(middleCollider.collider)){
            kartTwo.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartTwoCrashed = true;
            isCheckingCollisionEnterKartTwo = false;
            kartTwoCrashPosition = 4;
        }




    }

    public void CheckCollisionEnter(){
        // 0 --> left; 1 --> right; 2 --> top; 3 --> bottom;
        if(kart.rigidBody.intersects(bottomCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartOneCrashed = true;
            isCheckingCollisionEnter = false;
            kartOneCrashPosition = 3;

        }


        if(kart.rigidBody.intersects(leftCollider.collider)) {
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartOneCrashed = true;
            isCheckingCollisionEnter = false;
            kartOneCrashPosition = 0;

        }

        if(kart.rigidBody.intersects(rightCollider.collider)) {
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartOneCrashed = true;
            isCheckingCollisionEnter = false;
            kartOneCrashPosition = 1;


        }

        if(kart.rigidBody.intersects(topCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartOneCrashed = true;
            isCheckingCollisionEnter = false;
            kartOneCrashPosition = 2;
        }

        if(kart.rigidBody.intersects(middleCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            playCrashSound();
            System.out.println("Collision occurred!");
            kartOneCrashed = true;
            isCheckingCollisionEnter = false;
            kartOneCrashPosition = 4;
        }













    }

    public void CheckCollisionExit(){


        switch(kartOneCrashPosition){
            case 0 :
                if (!kart.rigidBody.intersects(leftCollider.collider)) {
                    kartOneCrashed = false;
                    isCheckingCollisionEnter = true;
                }
                break;
            case 1:
                if (!kart.rigidBody.intersects(rightCollider.collider)) {
                    kartOneCrashed = false;
                    isCheckingCollisionEnter = true;
                }
                break;
            case 2:
                if(!kart.rigidBody.intersects(topCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartOneCrashed =false;
                    isCheckingCollisionEnter = true;
                }
                break;
            case 3:
                if(!kart.rigidBody.intersects(bottomCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartOneCrashed =false;
                    isCheckingCollisionEnter = true;

                }
                break;
            case 4:
                if(!kart.rigidBody.intersects(middleCollider.collider)) {
                    //kart.SetKARTSPEED(0, 0);
                    kartOneCrashed =false;
                    isCheckingCollisionEnter = true;

                }
                break;

        }















    }






    public void CheckPointsCollisionKartOne(){


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
                System.out.println("Kart one score: " + kart.score);
                gameUILayer.setCurrentLapPlayer1(kart.score);

            }
            //Reset
            kart.checkPointReached = new boolean[]{false, false, false};

            kart.isInOrder = false;
        }
    }





    public void CheckPointsCollisionKartTwo(){


        //Checkpoints collision
        if(kartTwo.rigidBody.intersects(rightCheckpoint.collider)){
            //playerKart.SetKARTSPEED(0, 0);

            kartTwo.checkPointReached[0] = true;
            kartTwo.isInOrder = false;

        }
        if(kartTwo.rigidBody.intersects(topCheckpoint.collider)){
            //playerKart.SetKARTSPEED(0, 0);

            kartTwo.checkPointReached[1] = true;

        }

        if(kartTwo.rigidBody.intersects(leftCheckpoint.collider)){
            //playerKart.SetKARTSPEED(0, 0);

            kartTwo.checkPointReached[2] = true;
            kartTwo.isInOrder = true;
        }


        //Finish line
        if(kartTwo.rigidBody.intersects(finishLine.collider)){
            //playerKart.SetKARTSPEED(0, 0);
            if(kartTwo.checkPointReached[0] == true && kartTwo.checkPointReached[1] == true && kartTwo.checkPointReached[2] == true && kartTwo.isInOrder){
                kartTwo.score++;
                System.out.println("Kart one score: " + kartTwo.score);
                gameUILayer.setCurrentLapPlayer2(kartTwo.score);

            }
            //Reset
            kartTwo.checkPointReached = new boolean[]{false, false, false};

            kartTwo.isInOrder = false;
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


}
