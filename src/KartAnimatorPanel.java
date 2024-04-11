import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KartAnimatorPanel extends JPanel{


    public Rectangle rigidBody;

    private static String IMAGE_NAME;
    protected ImageIcon images[];

    private final int TOTAL_IMAGES = 16;
    private int currentImage = 0;
    private final int ANIMATION_DELAY = 50;
    private int width;
    private int height;

    private Timer animationTimer;

    private int locationx;

    private int locationy;

    // Indicate the direction the car is facing
    // 0 --> East,
    // 1 --> north,
    // 2 --> west,
    // 3 --> south
    private int currentOrientation = 0;
    private int direction;

    private boolean updateOrientation = false;
    //KeyListener controls;


    private boolean turnLeft = false;
    private boolean turnRight = false;

    int KARTSPEEDX;
    int KARTSPEEDY;

    int KARTSPEED;
    int timesOfRotation = 0;


    public boolean isInOrder;
    public boolean[] checkPointReached; // left, top , right

    int currentLap;
    int maxLap;

    int score;
    public KartAnimatorPanel(String imageName, String folderName, int _locationx, int _locationy, int maxLap){
        this.currentLap = 0;
        this.maxLap = maxLap;

        this.IMAGE_NAME = imageName;
        this.locationx = _locationx;
        this.locationy = _locationy;
        this.images = new ImageIcon[TOTAL_IMAGES];
        this.setOpaque(false);
        this.setBounds(locationx, locationy, 50, 50);
        //this.setBackground(Color.BLUE);
        //this.setOpaque(false);

        //Instantiate its rigidbody
        this.rigidBody = new Rectangle(0, 0, 50, 50);



        for (int i = 0; i < images.length; i++){

            //images[i] = new ImageIcon(getClass().getResource("asset/kartCyan0.png"));

            this.images[i] = new ImageIcon(getClass().getResource("assets/"+ folderName +"/"+ IMAGE_NAME + i + ".png"));
        }


        this.width = images[0].getIconWidth();
        this.height = images[0].getIconHeight();

        this.direction = 0;
        //this.addKeyListener(this);


        this.checkPointReached = new boolean[] {false, false, false};
        this.isInOrder = false;
        this.score = 0;
    }


    //This is similar to update() in Unity game engine
    public void paintComponent(Graphics g){


        super.paintComponent(g);





        this.rigidBody.setLocation(locationx, locationy);

        this.setLocation(locationx, locationy);
        this.images[direction].paintIcon(this, g, 0, 0);

        if(animationTimer.isRunning()){
            /*
            if(turnLeft) {
                if(this.timesOfRotation < 4){
                    if (this.currentImage == 0) {
                        this.currentImage = 16;
                    }

                    this.currentImage = (currentImage - 1) % TOTAL_IMAGES;
                    this.timesOfRotation++;
                }else{
                    this.timesOfRotation = 0;
                    this.turnLeft = false;
                }

            } else if (turnRight) {
                if(timesOfRotation < 4){

                    this.currentImage = (currentImage + 1) % TOTAL_IMAGES;
                    this.timesOfRotation++;
                }else{
                    this.timesOfRotation = 0;
                    this.turnRight = false;
                }*/







            //currentImage = (currentImage + 1) % TOTAL_IMAGES;

            //Initially current image is 0



        }



    }
    public void StartAnimation(){
        if (animationTimer == null){
            this.currentImage = 0;

            this.animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler());
            this.animationTimer.start();
        }else{
            //Have timer but not running
            if(!animationTimer.isRunning()){
                this.animationTimer.restart();

            }
        }

    }

    public void StopAnimation(){
        if(animationTimer != null){
            animationTimer.stop();
        }

    }

    public Dimension getMinimumSize(){
        return getPreferredSize();
    }

    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }



    private class TimerHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("Current direction: " + direction);

            //locationx = locationx + KARTSPEEDX;
            //locationy = locationy + KARTSPEEDY;

            if(direction == 0) {
                locationx = locationx + 2 * KARTSPEED;
            }else if(direction == 1){
                locationx = locationx + 2 * KARTSPEED;
                locationy = locationy + 1 * KARTSPEED;
            }else if(direction == 2){
                locationx = locationx + 2 * KARTSPEED;
                locationy = locationy + 2 * KARTSPEED;
            }else if(direction == 3){
                locationx = locationx + 1 * KARTSPEED;
                locationy = locationy + 2 * KARTSPEED;
            }else if(direction == 4){
                locationy = locationy + 2 * KARTSPEED;
            }else if (direction == 5){
                locationx = locationx - 1 * KARTSPEED;
                locationy = locationy + 2 * KARTSPEED;
            }else if(direction == 6){
                locationx = locationx - 2 * KARTSPEED;
                locationy = locationy + 2 * KARTSPEED;
            }else if(direction == 7){
                locationx = locationx - 2 * KARTSPEED;
                locationy = locationy + 1 * KARTSPEED;
            }else if (direction == 8){
                locationx = locationx - 2 * KARTSPEED;
            }else if(direction == 9){
                locationx = locationx - 2 * KARTSPEED;
                locationy = locationy - 1 * KARTSPEED;

            }else if (direction == 10){
                locationx = locationx - 2 * KARTSPEED;
                locationy = locationy - 2 * KARTSPEED;
            }else if (direction == 11){
                locationx = locationx - 1 * KARTSPEED;
                locationy = locationy - 2 * KARTSPEED;
            }else if (direction == 12){
                locationy = locationy - 2 * KARTSPEED;
            }else if (direction == 13){
                locationx = locationx + 1 * KARTSPEED;
                locationy = locationy - 2 * KARTSPEED;
            }else if (direction == 14){
                locationx = locationx + 2 * KARTSPEED;
                locationy = locationy - 2 * KARTSPEED;
            }else if(direction == 15){
                locationx = locationx + 2 * KARTSPEED;
                locationy = locationy - 1 * KARTSPEED;
            }


            repaint();

        }
    }


    public void TurnKart(int turnDirection){
        //turnDirection: 0 --> left
        //turnDirection: 1 --> right
        if(turnDirection == 0){
            //this.turnLeft = true;
            //this.currentOrientation = (currentOrientation + 1) % 4;

            this.direction = (this.direction + 1) % TOTAL_IMAGES;
        }else if (turnDirection == 1){
            //this.turnRight = true;
            if(this.direction == 0){
                this.direction = TOTAL_IMAGES - 1;
            }else{
                this.direction = this.direction - 1;
            }

            //if(this.currentOrientation == 0){
            //    this.currentOrientation = 3;
            //}else{
            //    this.currentOrientation --;
            //}
        }
        //previousOrientation = currentOrientation;
        //currentOrientation = _newOrientation;
        //updateOrientation = true;
    }

    public int GetCurrentOrientation(){
        return currentOrientation;
    }


    public void SetKARTSPEED(int _speedx, int _speedy){
        //this.KARTSPEEDX = _speedx;
        //this.KARTSPEEDY = _speedy;
        KARTSPEED = _speedx;
    }



    public int getLocationx(){
        return this.locationx;
    }

    public int getLocationy(){
        return this.locationy;
    }
    public int getDirection(){
        return this.direction;
    }


    public void setLocationx(int _x){
        this.locationx = _x;
    }
    public void setLocationy(int _y){
        this.locationy = _y;
    }
    public void setDirection(int _d){
        this.direction = _d;
    }

    public void IncrementLap(){
        currentLap++;
    }

    public boolean FinishedLap(){
        if(currentLap == maxLap){
            return true;
        }else{
            return false;
        }

    }

}
