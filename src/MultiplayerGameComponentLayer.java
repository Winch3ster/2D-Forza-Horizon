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


    boolean crashedWithWall = false;
    //Consist of colliders and vehicle
    public MultiplayerGameComponentLayer(GameUILayer _gameUILayer, int maxlap){
        setOpaque(false);
        setLayout(null);
        setBounds(0,0, 850, 650);

        this.gameUILayer = _gameUILayer;

        kart = new KartAnimatorPanel("kartCyan", "kartOne", 350, 500, maxlap);
        add(kart);

        kartTwo = new KartAnimatorPanel("kartOrange", "kartTwo", 350, 440, maxlap);
        add(kartTwo);


        // wall colliders so vehicle will not exit the map
        leftCollider = new ColliderWall(20, 510, 35, 65);
        rightCollider = new ColliderWall(20, 510, 780, 65);
        topCollider = new ColliderWall(740, 20, 40, 65);
        bottomCollider = new ColliderWall(740, 20, 40, 550);


        add(leftCollider);
        add(rightCollider);
        add(topCollider);
        add(bottomCollider);


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

        //get the crash audio file
        try{

        }catch(Exception e){

        }

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
            CheckCollision();
        }
    }

    public void CheckCollision(){

        if(kart.rigidBody.intersects(leftCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            if(!crashedWithWall){
                System.out.println("Collided with left wall");
                playCrashSound();
                crashedWithWall = true;
            }
        }else{
            crashedWithWall = false;
        }


        if(kart.rigidBody.intersects(bottomCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            if(!crashedWithWall){
                System.out.println("Collided with bottom wall");
                playCrashSound();
                crashedWithWall = true;
            }
        }else{
            crashedWithWall = false;
        }

        if(kart.rigidBody.intersects(rightCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            if(!crashedWithWall){
                System.out.println("Collided with right wall");
                playCrashSound();
                crashedWithWall = true;
            }
        }else{
            crashedWithWall = false;
        }

        if(kart.rigidBody.intersects(topCollider.collider)){
            kart.SetKARTSPEED(0, 0);
            if(!crashedWithWall){
                System.out.println("Collided with top wall");
                playCrashSound();
                crashedWithWall = true;
            }
        }else{
            crashedWithWall = false;
        }



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
                System.out.println("Kart one score: " + kart.score);
                gameUILayer.setCurrentLap(kart.score);

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
