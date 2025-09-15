import java.awt.BorderLayout;
import java.awt.Label;

import javax.swing.JPanel;

public class Info extends JPanel {
    private int totalBombs;
    private int markedCells;
    private float timeElapsed;
    private Label bombLabel;
    private Label timeLabel;

    public Info() {
        bombLabel = new Label();
        timeLabel = new Label();
        add(bombLabel);
        add(timeLabel);
    }

    public int getTotalBombs() {
        return totalBombs;
    }

    public void setTotalBombs(int totalBombs) {
        this.totalBombs = totalBombs;
        bombLabel.setText("Remaining: " + totalBombs);
    }

    public int getMarkedCells() {
        return markedCells;
    }
    
    public void setMarkedCells(int markedCells) {
        this.markedCells = markedCells;
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
        timeLabel.setText("Elapsed Time: " + (int) timeElapsed + "s");
    }

    public void updateMarkedCells(boolean marked) {
        markedCells += 1 * (marked ? 1 : -1);
        bombLabel.setText("Remaining: " + (totalBombs - markedCells));
    }

    public void updateTime(float time) {
        timeElapsed += time;
        timeLabel.setText("Elapsed Time: " + (int) timeElapsed + "s");
    }
}
