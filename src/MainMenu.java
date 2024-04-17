import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainMenu extends JFrame implements ActionListener {
    CustomButton playButton;
    CustomButton tutorialButton;
    CustomButton hostGameButton;
    Image bgImage;
    Image frameIcon;
    Clip clip;
    File audioFile;
    private boolean playMusic;
    public MainMenu(){
        this.setSize(850, 650);
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("2D Forza Horizon");
        this.playMusic = true;
        frameIcon =  new ImageIcon(getClass().getResource("assets/images/frameIcon.png")).getImage();
        setIconImage(frameIcon);
        //This is invisible
        JLayeredPane frame = new JLayeredPane();
        frame.setLocation(0,0);
        frame.setSize(850, 650);
        this.add(frame);
        setLocationRelativeTo(null);






        bgImage = new ImageIcon("assets/images/mainMenuBg.jpg").getImage();
        BgPanel bg = new BgPanel();




        playButton = new CustomButton("Join Game", new Color(224, 101,76), Color.white);
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playButton.addActionListener(this);



        tutorialButton = new CustomButton("Offline PVP", new Color(224, 101,76), Color.white);
        tutorialButton.setLocation(30, 100);



        tutorialButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tutorialButton.addActionListener(this);


        hostGameButton = new CustomButton("Host Game", new Color(224, 101,76), Color.white);

        hostGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        hostGameButton.addActionListener(this);


        JPanel titleContainer = new JPanel();
        titleContainer.setOpaque(false);
        JLabel title = new JLabel("2D Forza");
        JLabel title2 = new JLabel("Horizon");
        //title.setPreferredSize(new Dimension(450, 300));
        //JLabel titleTwo = new JLabel("2D Forza");


        title.setFont(new Font("Calibri", Font.PLAIN, 100));
        title.setForeground(Color.WHITE);
        title.setBounds(100, 90, 450, 300);

        title2.setFont(new Font("Calibri", Font.PLAIN, 100));
        title2.setForeground(Color.WHITE);
        title2.setBounds(100, 160, 450, 300);



        titleContainer.setBounds(100, 140, 450, 200 );
        titleContainer.setLayout(new GridLayout(2,1));
        titleContainer.setBackground(Color.RED);
        titleContainer.add(title);
        titleContainer.add(title2);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(3, 1, 0, 20));

        buttonContainer.setSize(new Dimension(220, 200));
        buttonContainer.setLocation(100, 360);
        buttonContainer.setOpaque(false);
        buttonContainer.add(playButton);
        buttonContainer.add(hostGameButton);
        buttonContainer.add(tutorialButton);
        //buttonContainer.add(tutorialButton);
        frame.add(buttonContainer);
        frame.add(titleContainer);
        frame.add(bg);




        this.setVisible(true);

        try {

            audioFile = new File("src/assets/audio/mainMenuTheme.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            clip = AudioSystem.getClip();
            clip.open(audioStream);


        }catch (Exception e){

        }
        PlayMainMenuTheme();
    }

    private void PlayMainMenuTheme() {
        try {


            // Start playing the audio on a separate thread
            new Thread(() -> {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                while (playMusic) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                clip.stop();
                clip.close();
            }).start();
            // Simulate a condition for stopping the audio
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playButton){
            System.out.println("PlayButton clicked");
            JoinGame w = new JoinGame();
            playMusic = false;
            dispose();
        }

        if(e.getSource() == hostGameButton){
            System.out.println("host game btn clicked");
            HostGame g = new HostGame();
            playMusic = false;
            dispose();
        }

        if(e.getSource() == tutorialButton){
            System.out.println("tutorialButton clicked");
            PracticeWindow t = new PracticeWindow();
            playMusic = false;
            dispose();
        }

    }


}
