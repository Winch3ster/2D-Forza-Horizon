import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUILayer extends JPanel {

    int MAXLAP;
    private int currentLapPlayer1;
    private int currentLapPlayer2;
    int time;

    boolean incrementCurrentLap;

    private JLabel lapDisplay;
    private JLabel lapDisplay2;

    private Image player1;
    private Image player2;

    public GameUILayer(int maxlap){
        this.MAXLAP = maxlap;
        this.incrementCurrentLap = false;

        this.player1 = new ImageIcon(getClass().getResource("assets/kartOne/kartCyan0.png")).getImage();

        this.player2 = new ImageIcon(getClass().getResource("assets/kartTwo/kartOrange0.png")).getImage();



        setOpaque(false);
        setBounds(0,0, 850, 80);
        setLayout(null);

        lapDisplay = new JLabel();
        lapDisplay.setBounds(100, 20, 200, 50);
        lapDisplay.setFont(new Font("Calibri", Font.PLAIN, 20));
        lapDisplay.setText("Player1 lap: " + currentLapPlayer1 + " / " + MAXLAP);


        lapDisplay2 = new JLabel();
        lapDisplay2.setBounds(600, 20, 200, 50);
        lapDisplay2.setFont(new Font("Calibri", Font.PLAIN, 20));
        lapDisplay2.setText("Player2 lap: " + currentLapPlayer2 + " / " + MAXLAP);


        add(lapDisplay);
        add(lapDisplay2);


    }



    public void setCurrentLapPlayer1(int _currentLap){
        this.currentLapPlayer1 = _currentLap;
        lapDisplay.setText("Player1 lap: " + currentLapPlayer1 + " / " + MAXLAP);


    }


    public void setCurrentLapPlayer2(int _currentLap){
        this.currentLapPlayer2 = _currentLap;
        lapDisplay2.setText("Player2 lap: " + currentLapPlayer2 + " / " + MAXLAP);

    }



    public int getCurrentMaxLap(){
        return Math.max(currentLapPlayer1, currentLapPlayer2);
    }


    public int getWinner(){
        if(currentLapPlayer1 > currentLapPlayer2){
            return 1;
        }else{
            return 2;
        }
    }
    public void paintComponent(Graphics g){


        super.paintComponent(g);


        g.drawImage(player1, 30, 15, 50, 50, this);
        g.drawImage(player2, 540, 15, 50, 50, this);

    }
}
