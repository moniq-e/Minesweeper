package com.monique.minesweeper;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class MyMouse extends MouseAdapter {
    private Minesweeper mine;

    public MyMouse(Minesweeper m) {
        mine = m;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        var c = (Cell) e.getSource();
        if (c.isOpen()) return;

        if (e.getButton() == MouseEvent.BUTTON1) {

            if (!c.isMarked()) {
                c.setOpen(true);

                if (mine.isFirst()) {
                    mine.setFirst(false);
                    mine.generateBombs();
                    if (c.getNumber() != 0) c.setText(String.valueOf(c.getNumber()));
                }

                if (c.isOpen() && c.isBomb()){
                    System.out.println("Resta nada pro beta");
                    mine.lose();
                    return;
                }
                mine.checkForEmpty(c.getPosition());   
            }
            return;
        }
        if (mine.isFirst()) return;

        if (e.getButton() == MouseEvent.BUTTON3) {

            if (!c.isMarked()) {
                c.setMarked(true);
                if (c.isBomb()) {
                    mine.incrementBombs();
                }
            } else if (c.isMarked()) {
                c.setMarked(false);
                if(c.isBomb()) {
                    mine.decrementBombs();
                }
            }
        }
    }
}
