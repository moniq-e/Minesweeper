package com.monique.minesweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.mocha.util.math.Vector2;

import javax.swing.ImageIcon;


public class Minesweeper extends JPanel {
    private int size;
    private Cell[][] grid;
    private ArrayList<Cell> cells;
    private JFrame frame;
    private Timer timer;
    private Info info;
    private MyMouse mouse = new MyMouse(this);
    private Vector2[] offsets = {
        new Vector2(0, -1), //NORTH
        new Vector2(0, 1), //SOUTH
        new Vector2(1, 0), //EAST
        new Vector2(-1, 0), //WEST
        new Vector2(1, -1), //NORTHEAST
        new Vector2(-1, -1), //NORTHWEST
        new Vector2(1, 1), //SOUTHWEST
        new Vector2(1, 1), //SOUTHEAST
    };
    private int totalBombs = 0;
    private int bombs = 0;
    private int openCells = 0;
    private boolean first = true;

    public static void main(String[] args) {
        new Minesweeper();
    }

    public Minesweeper() {
        timer = new Timer(33, e -> {
            update();
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
               //cellFont = new Font("Arial", Font.BOLD, getWidth() / size / 4);
            }
        });

        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(new ImageIcon("./assets/icon.png").getImage());

        frame.add(this, BorderLayout.CENTER);

        info = new Info();
        frame.add(info, BorderLayout.NORTH);

        frame.setVisible(true);
        frame.setSize(new Dimension(500, 500));

        var screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) screen.getWidth() / 2 - getSize().width / 2, (int) screen.getHeight() / 2 - getSize().height / 2);
        play();
    }

    public void play() {
        chooseDificulty();
        info.setTotalBombs(totalBombs);
        info.setMarkedCells(0);
        info.setTimeElapsed(0);
        setLayout(new GridLayout(size, size));

        bombs = 0;
        openCells = 0;
        first = true;
        removeAll();
        initializeGrid();
        timer.start();
    }

    public void chooseDificulty() {
        var options = new String[] {
            "Easy",
            "Medium",
            "Hard",
            "Huge"
        };
        var res = JOptionPane.showOptionDialog(frame, "Choose a dificulty level: ", "Minesweeper - Dificulty", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, 0);

        switch (res) {
            case -1:
                System.exit(0);
                break;
            case 0:
                totalBombs = 10;
                size = 7;
                break;
            case 1:
                totalBombs = 50;
                size = 12;
                break;
            case 2:
                totalBombs = 100;
                size = 15;
                break;
            case 3:
                totalBombs = 150;
                size = 18;
                break;
        }
    }

    public void update() {
        revalidate();
        repaint();
        info.updateTime(.033f);

        if (bombs + openCells == size * size) win();
    }

    public void checkForEmpty(Vector2 pos) {
        var toCheck = new Stack<Vector2>();
        toCheck.add(pos);

        while (!toCheck.isEmpty()) {
            var p = toCheck.pop();
            var pc = grid[p.getFloorX()][p.getFloorY()];

            for (var offset : offsets) {
                var newPos = new Vector2(p.getX() + offset.getX(), p.getY() + offset.getY());
                if (newPos.getX() >= grid.length) continue;
                if (newPos.getY() >= grid[0].length) continue;
                if (newPos.getX() < 0) continue;
                if (newPos.getY() < 0) continue;

                var c = grid[newPos.getFloorX()][newPos.getFloorY()];
                if (c.isBomb()) continue;

                if ((c.getNumber() == 0 || pc.getNumber() == 0 || isFirst()) && !c.isOpen()) {
                    c.setOpen(true);
                    toCheck.push(c.getPosition());
                }
            }
        }
    }

    public void initializeGrid() {
        grid = new Cell[size][size];
        cells = new ArrayList<Cell>(size * size);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                var c = new Cell(i, j, mouse, this);
                grid[i][j] = c;
                cells.add(c);
                add(c);
            }
        }
    }

    public void generateBombs() {
        while (totalBombs-- > 0) {
           var c = popRandom(cells);
           if (c.isOpen()) continue;
           c.setBomb(true);
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                var c = grid[i][j];
                var amount = 0;

                for (Vector2 offset : offsets) {
                    var newPos = new Vector2(i + offset.getX(), j + offset.getY());
                    if (newPos.getX() >= grid.length) continue;
                    if (newPos.getY() >= grid[i].length) continue;
                    if (newPos.getX() < 0) continue;
                    if (newPos.getY() < 0) continue;

                    if (grid[newPos.getFloorX()][newPos.getFloorY()].isBomb()) amount++;
                }
                c.setNumber(amount);
            }
        }
    }

    public <T> T popRandom(ArrayList<T> array) {
        var pos = (int) Math.floor(Math.random() * array.size());
        return array.remove(pos);
    }

    public void lose() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].setOpen(true);
            }
        }

        timer.stop();
        JOptionPane.showMessageDialog(frame, "Voce perdeu.", "Minesweeper", JOptionPane.ERROR_MESSAGE);
        var again = JOptionPane.showConfirmDialog(frame, "Deseja jogar novamente?", "Minesweeper", JOptionPane.YES_NO_OPTION);
        if (again == 0) {
            play();
            return;
        }
        System.exit(0);
    }

    public void win() {
        timer.stop();
        JOptionPane.showMessageDialog(frame, "Voce ganhou!", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
        var again = JOptionPane.showConfirmDialog(frame, "Deseja jogar novamente?", "Minesweeper", JOptionPane.YES_NO_OPTION);
        if (again == 0) {
            play();
            return;
        }
        System.exit(0);
    }

    public void incrementBombs() {
        bombs++;
        System.out.println("Bombs: " + bombs + " | Open Cells: " + openCells);
    }

    public void decrementBombs() {
        bombs--;
        System.out.println("Bombs: " + bombs + " | Open Cells: " + openCells);
    }

    public void incrementOpenCells() {
        openCells++;
        System.out.println("Bombs: " + bombs + " | Open Cells: " + openCells);
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Info getInfo() {
        return info;
    }
}