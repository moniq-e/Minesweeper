package com.monique.minesweeper;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.mocha.actor.Box;
import org.mocha.actor.Sprite;
import org.mocha.util.math.Vector2;

import java.awt.Color;

public class Cell extends Box {
	public static final Sprite FLAG = new Sprite("flag.png", 0, 0);
	public static final Sprite MINE = new Sprite("mine.png", 0, 0);

    private boolean bomb = false;
    private boolean open = false;
    private boolean marked = false;
	private int number;
	private Minesweeper mine;
	private Sprite sprite;
	private Color backgroundColor;

    public Cell(int x, int y, MyMouse mouse, Minesweeper mine) {
		super(x, y, 50, 50);
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

		if (!isBomb()) {
			mine.incrementOpenCells();
			backgroundColor = Color.BLUE;
			setForeground(Color.WHITE);
			setFont(mine.getCellFont());
			if (getNumber() != 0) setText(String.valueOf(getNumber()));
		} else {
			sprite = MINE;
			backgroundColor = Color.RED;
		}
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
		mine.getInfo().updateMarkedCells(marked);
		sprite = marked ? FLAG : null;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
