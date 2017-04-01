package gameObjects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.Game;
import client.ID;
import client.Tile;
import gameObjects.units.Unit;

public class Cursor
{
	public static Cursor instance = null;
	
	private int x, y;
	
	Unit unit;
	BufferedImage cursorIcon;
	
	public Cursor() 
	{
		x = 0;
		y = 0;
		
		unit = null;
		
		try {
			cursorIcon = ImageIO.read(new File("res/cursor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() 
	{
		
	}

	public void render(Graphics g) 
	{
		g.setColor(Color.WHITE);
		g.drawRect((int)x, (int)y, 32, 32);
		Game.g.drawImage(cursorIcon, x + 16, y + 16, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public boolean isValidMove(int toX, int toY)
	{
		if (toX > 620  || toY > 430 || toX < 0 || toY < 0)
			return false;
		return true;
	}
	
	public Unit getUnit()
	{
		return unit;
	}
	
	public void setUnit(Unit u)
	{
		unit = u;
	}
	
	public boolean isSelecting()
	{
		if (unit != null)
			return true;
		return false;
	}
	
	public void unselectUnit()
	{
		unit = null;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getRow()
	{
		return y / 32;
	}
	
	public int getCol()
	{
		return x / 32;
	}

	public static Cursor get()
	{
		if (instance == null)
			instance = new Cursor();
		return instance;
	}

}
