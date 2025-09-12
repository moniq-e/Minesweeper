import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class Cell extends JButton {
    private boolean bomb = false;
    private boolean open = false;
    private boolean marked = false;
	private int number;
	private final Vector2i pos;
	private Minesweeper mine;

    public Cell(MyMouse mouse, Vector2i pos, Minesweeper mine) {
		setSize(50, 50);
        addMouseListener(mouse);
		this.pos = pos;
		this.mine = mine;
    }

	public boolean isBomb() {
		return bomb;
	}

	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
		mine.incrementOpenCells();
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 24));
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Vector2i getPos() {
		return pos;
	}
}
