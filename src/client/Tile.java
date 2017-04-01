package client;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import gameObjects.units.Unit;

public class Tile 
{
	private int x, y, type;
	Unit unit;
	private BufferedImage tileMap;
	private BufferedImage tileImage;

	public Tile(int x, int y, int type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		
		unit = null;
	}
	
	public Tile(int x, int y, int type, BufferedImage tileMap)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		this.tileMap = tileMap;
		
		unit = null;
	}
	
	public void setTileMap(BufferedImage map)
	{
		tileMap = map;
	}
	
	public void occupyTile(Unit u)
	{
		unit = u;
	}
	
	public Unit getUnit()
	{
		return unit;
	}
	
	public boolean isOccupied ()
	{
		return (unit != null);
	}
	
	public Unit releaseTile()
	{
		Unit releasedUnit = unit;
		unit = null;
		return releasedUnit;
	}
	
	public void render(int r, int c, Graphics g)
	{
		if (type ==  0)
		{
			Color transparent = new Color (0, 0, 0, 0);
			g.setColor(transparent);
		}
		if (type == 1)
		{
			tileImage = tileMap.getSubimage(1*32, 2*32, 32, 32);
			g.drawImage(tileImage, x, y, null);
		}
		if (type == 2)
		{
			tileImage = tileMap.getSubimage(7*32, 7*32, 32, 32);
			g.drawImage(tileImage, x, y, null);
		}
		if (type == 3)
		{
			Color transparentBlue = new Color(0, 0, 255, 90);
			g.setColor(transparentBlue);
			g.fillRect(x, y, 32, 32);
		}
		if(type == 4)
		{
			Color transparentRed = new Color(255, 0, 0, 127);
			g.setColor(transparentRed);
			g.fillRect(x, y, 32, 32);
		}
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int t)
	{
		type = t;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getGridX()
	{
		return x / 32;
	}
	
	public int getGridY()
	{
		return y / 32;
	}
	
	public int getXCenter()
	{
		return (x + 32/4);
	}
	
	public int getYCenter()
	{
		return (y - 32/4);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle((int)x,(int)y, 32, 32); 
	}
}
