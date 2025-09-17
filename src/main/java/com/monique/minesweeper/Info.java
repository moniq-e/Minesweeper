package com.monique.minesweeper;

import org.mocha.gui.CanvasLayer;
import org.mocha.gui.Label;

public class Info extends CanvasLayer {
    private int totalBombs;
    private int markedCells;
    private float timeElapsed;
    private Label bombLabel;
    private Label timeLabel;

    public Info(Minesweeper mine) {
        super(mine.getWidth(), 100);
        bombLabel = new Label("");
        timeLabel = new Label("");

        bombLabel.setSize(getWidth() / 2, 100);
        timeLabel.setSize(getWidth() / 2, 100);

        addChildren(bombLabel, timeLabel);
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
