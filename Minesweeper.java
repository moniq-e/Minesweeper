import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;


public class Minesweeper extends JPanel {
    private int size;
    private Cell[][] grid;
    private JFrame frame;
    private MyMouse mouse = new MyMouse(this);
    private Vector2i[] offsets = {
        Vector2i.NORTH,
        Vector2i.SOUTH,
        Vector2i.EAST,
        Vector2i.WEST,
        Vector2i.NORTHEAST,
        Vector2i.NORTHWEST,
        Vector2i.SOUTHEAST,
        Vector2i.SOUTHWEST,
    };
    private int bombs = 0;
    private int openCells = 0;
    private boolean first = true;

    public static void main(String[] args) {
        new Minesweeper(5);
    }

    public Minesweeper(int size) {
        this.size = size;
        grid = new Cell[size][size];

        new Timer(33, e -> {
            update();
        }).start();


        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(new ImageIcon("./assets/icon.png").getImage());

        setLayout(new GridLayout(size, size));
        frame.add(this, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setSize(new Dimension(size * 100, size * 100));
        frame.setLocation(getSize().width / 2, getSize().height / 2);
        play();
    }

    public void play() {
        bombs = 0;
        openCells = 0;
        first = true;
        removeAll();
        initializeGrid();
    }

    public void update() {
        revalidate();
        repaint();

        if (bombs + openCells == size * size) win();
    }

    public void checkForEmpty(Vector2i pos) {
        var toCheck = new Stack<Vector2i>();
        toCheck.add(pos);

        while (!toCheck.isEmpty()) {
            var p = toCheck.pop();
            var pc = grid[p.getX()][p.getY()];

            for (var offset : offsets) {
                var newPos = new Vector2i(p.getX() + offset.getX(), p.getY() + offset.getY());
                if (newPos.getX() >= grid.length) continue;
                if (newPos.getY() >= grid[0].length) continue;
                if (newPos.getX() < 0) continue;
                if (newPos.getY() < 0) continue;

                var c = grid[newPos.getX()][newPos.getY()];
                if (c.isBomb()) continue;

                if ((c.getNumber() == 0 || pc.getNumber() == 0 || isFirst()) && !c.isOpen()) {
                    c.setOpen(true);
                    toCheck.push(c.getPos());
                }
            }
        }
    }

    public void initializeGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                var c = new Cell(mouse, Vector2i.of(i, j), this);
                grid[i][j] = c;
                add(c);
            }
        }
    }

    public void generateBombs() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                var c = grid[i][j];
                if (c.isOpen()) continue;
                c.setBomb(Math.random() < 0.2);
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                var c = grid[i][j];
                var amount = 0;

                for (Vector2i offset : offsets) {
                    var newPos = new Vector2i(i + offset.getX(), j + offset.getY());
                    if (newPos.getX() >= grid.length) continue;
                    if (newPos.getY() >= grid[i].length) continue;
                    if (newPos.getX() < 0) continue;
                    if (newPos.getY() < 0) continue;

                    if (grid[newPos.getX()][newPos.getY()].isBomb()) amount++;
                }
                c.setNumber(amount);
            }
        }
    }

    public void lose() {
        JOptionPane.showMessageDialog(frame, "Voce perdeu.", "Minesweeper", 0);
        var again = JOptionPane.showConfirmDialog(frame, "Deseja jogar novamente?", "Minesweeper", JOptionPane.YES_NO_OPTION);
        if (again == 0) {
            play();
            return;
        }
        System.exit(0);
    }

    public void win() {
        JOptionPane.showMessageDialog(frame, "Voce ganhou!", "Minesweeper", 0);
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
}