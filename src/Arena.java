import colliders.Checkpoint;
import colliders.ColliderWall;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Arena extends JPanel implements KeyListener{

    int KARTSPEED = 5;
    KartAnimatorPanel kartOne;
    KartAnimatorPanel kartTwo;

    //JPanel kartOne;

    ColliderWall leftCollider;
    ColliderWall rightCollider;
    ColliderWall topCollider;
    ColliderWall bottomCollider;

    Checkpoint rightCheckpoint;
    public Arena(){
        this.setSize(850, 650);
        this.setLayout(null);
        this.addKeyListener(this);
        RaceTrack racetrack = new RaceTrack();

        /*
        leftCollider = new ColliderWall(1);
        rightCollider = new ColliderWall(3);
        topCollider = new ColliderWall(0);
        bottomCollider = new ColliderWall(2);



        this.add(leftCollider);
        this.add(rightCollider);
        this.add(topCollider);
        this.add(bottomCollider);



        rightCheckpoint = new Checkpoint(0, 100, 20, 50, 50);
        this.add(rightCheckpoint);


        kartOne = new KartAnimatorPanel("kartCyan", "kartOne", 350, 500);
        //kartOne = new JPanel();
        kartTwo = new KartAnimatorPanel("kartOrange", "kartTwo", 350, 560);

        this.add(kartOne);
        this.add(kartTwo);
        this.add(racetrack);
        kartOne.StartAnimation();
        kartTwo.StartAnimation();

        this.setVisible(true);
 */

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*
         * Kart one controls
         * W --> accelerate
         * S --> reverse
         * A --> turn left
         * D --> turn right
         *
         *
         * Kart two controls
         * UP Arrow --> accelerate
         * DOWN Arrow --> reverse
         * LEFT Arrow --> turn left
         * RIGHT Arrow --> turn right
         *
         *
         * */
        switch (e.getKeyChar()){
            case 'w':
                CheckCollision();
               kartOne.SetKARTSPEED(-5, 0);

            case 's':
                CheckCollision();
                kartOne.SetKARTSPEED(5, 0);

                break;
            case 'a':
                CheckCollision();
                kartOne.TurnKart(1);

                //kartOne.setLocation(kartOne.getX(), kartOne.getY()-2);

                break;
            case 'd':
                CheckCollision();
                kartOne.TurnKart(0);
                //kartOne.setLocation(kartOne.getX(), kartOne.getY()+2);
                break;
        }
        //System.out.println("Key pressed: " + e.getKeyChar());




        switch (e.getKeyChar()){
            case 'u':

                kartTwo.SetKARTSPEED(5, 0);

                //kartOne.updateLocation(1, 0);
                break;
            case 'j':

                kartTwo.SetKARTSPEED(-5, 0);

                break;
            case 'h':
                kartTwo.TurnKart(1);

                //kartOne.setLocation(kartOne.getX(), kartOne.getY()-2);

                break;
            case 'k':
                kartTwo.TurnKart(0);
                //kartOne.setLocation(kartOne.getX(), kartOne.getY()+2);
                break;
        }


    }



    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()){


            case 'w':
                kartOne.SetKARTSPEED(0, 0);
                //kartOne.updateLocation(1, 0);
                break;
            case 's':
                kartOne.SetKARTSPEED(0, 0);

            break;



            case 'u':
                kartTwo.SetKARTSPEED(0, 0);
                //kartOne.updateLocation(1, 0);
                break;
            case 'j':

                kartTwo.SetKARTSPEED(0, 0);


                break;


        }
    }



    public void CheckCollision(){
        if(kartOne.rigidBody.intersects(kartTwo.rigidBody)){
            kartOne.SetKARTSPEED(0, 0);
            System.out.println("Crash with other vehicle");
            JOptionPane.showOptionDialog(null, "Game Over! Drive safely next time!", "Game Over!", JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE, null, null, null);

        }

        if(kartOne.rigidBody.intersects(leftCollider.collider)){
            kartOne.SetKARTSPEED(0, 0);

            System.out.println("Collided with left wall");
        }

        if(kartOne.rigidBody.intersects(bottomCollider.collider)){
            kartOne.SetKARTSPEED(0, 0);

            System.out.println("Collided with bottom wall");
        }



        if(kartOne.rigidBody.intersects(rightCollider.collider)){
            kartOne.SetKARTSPEED(0, 0);

            System.out.println("Collided with right wall");
        }

        if(kartOne.rigidBody.intersects(topCollider.collider)){
            kartOne.SetKARTSPEED(0, 0);

            System.out.println("Collided with top wall");
        }


    }


}





