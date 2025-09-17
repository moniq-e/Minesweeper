package com.monique.minesweeper;

import org.mocha.actor.Box;
import org.mocha.actor.Sprite;
import org.mocha.gui.Label;
import org.mocha.util.GraphicsUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Cell extends Box {
	public static final Sprite FLAG = new Sprite("flag.png", 0, 0);
	public static final Sprite MINE = new Sprite("mine.png", 0, 0);
	public static final Font CELL_FONT = new Font("Cascadia Mono", 0, 24);

    private boolean bomb = false;
    private boolean open = false;
    private boolean marked = false;
	private int number;
	private Minesweeper mine;
	private Sprite sprite;
	private Color backgroundColor;
	private Label label;

    public Cell(int x, int y, MyMouse mouse, Minesweeper mine) {
		super(x, y, 50, 50);
		this.mine = mine;
		label = new Label("");
		label.setAlignment(.5f, .5f);
		mine.getCellManagerCanvasLayer().addChild(label);
    }

	@Override
	public void draw(Graphics2D g2) {
		var color = g2.getColor();
		g2.setColor(backgroundColor);
		GraphicsUtil.drawRotatedRect(getPosition().getFloorX(), getPosition().getFloorY(), getWidth(), getHeight(), rotation, anchor, g2);
		g2.setColor(color);

		if (sprite != null) {
			sprite.setPosition(getPosition());
			sprite.draw(g2);
		}
	}

	public void setText(String text) {
		label.setText(text);
		label.setSize(width, height);
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
			label.setForeground(Color.WHITE);
			label.setFont(CELL_FONT);
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
