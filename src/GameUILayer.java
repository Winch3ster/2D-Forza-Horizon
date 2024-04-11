import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUILayer extends JPanel {

    int MAXLAP;
    private int currentLap;
    int time;

    boolean incrementCurrentLap;

    private JLabel lapDisplay;



    public GameUILayer(int maxlap){
        this.MAXLAP = maxlap;
        this.incrementCurrentLap = false;

        setOpaque(false);
        setBounds(0,0, 850, 80);
        setLayout(null);

        lapDisplay = new JLabel();
        lapDisplay.setBounds(600, 20, 200, 50);
        lapDisplay.setFont(new Font("Calibri", Font.PLAIN, 30));
        lapDisplay.setText("Lap: " + currentLap + " / " + MAXLAP);

        add(lapDisplay);

    }



    public void setCurrentLap(int _currentLap){
        this.currentLap = _currentLap;
        lapDisplay.setText("Lap: " + currentLap + " / " + MAXLAP);

        if(currentLap == MAXLAP){
            //display end game scene

        }
    }

    public int getCurrentLap(){
        return currentLap;
    }

}
