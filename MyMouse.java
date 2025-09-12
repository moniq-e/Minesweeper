import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

public class MyMouse extends MouseAdapter {
    private Minesweeper mine;

    public MyMouse(Minesweeper m) {
        mine = m;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        var c = (Cell) e.getSource();
        if (e.getButton() == MouseEvent.BUTTON1) {
            if(!c.isMarked()) {
                c.setOpen(true);

                if (mine.isFirst()) {
                    mine.setFirst(false);
                    mine.generateBombs();
                }

                if (c.getNumber() == 0) mine.checkForEmpty(c.getPos());

                if (c.isOpen() && !c.isBomb()) {
                    c.setText(String.valueOf(c.getNumber()));
                }
                if (c.isOpen() && c.isBomb()){
                    System.out.print("Resta nada pro beta");
                    c.setIcon(new ImageIcon("./assets/mine.png"));
                    c.setBackground(Color.RED);
                    mine.lose();
                }   
            }
            return;
        }
        if (!mine.isFirst()) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                
                /*if (c.isOpen()) {
                    c.setText(null);
                }
                else {
                    c.setText(String.valueOf(c.getNumber()));
                }
                if (!c.isMarked()) {
                    c.setIcon(new ImageIcon("./assets/flag.png"));
                    c.setMarked(true);
                    if(c.isBomb()) {
                        mine.incrementBombs();
                    }
                }
                else {
                    c.setMarked(false);
                    c.setIcon(null);
                }
                if (c.isMarked() && c.isBomb()) {
                    c.setIcon(null);
                    c.setMarked(false);
                    mine.decrementBombs();
                }*/

            if (!c.isMarked() && !c.isOpen()) {
                    c.setMarked(true);
                    c.setIcon(new ImageIcon("./assets/flag.png"));
                    if (c.isBomb()) {
                        mine.incrementBombs();
                    }
            }
            else if(c.isMarked() && !c.isOpen()){
                    c.setMarked(false);
                    c.setIcon(null);
                    if(c.isBomb()) {
                        mine.decrementBombs();
                    }
                }
            }

        }
    }
}
