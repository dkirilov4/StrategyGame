package gameObjects;
import java.awt.Graphics;
import java.awt.Rectangle;

import client.ID;
import client.Tile;
import screens.InGameScreen;

public abstract class GameObject 
{
	private Tile tile;
	protected int x, y;
	private ID id;
	
	protected boolean isSelected;
	
	public GameObject(Tile tile)
	{
		this.tile = tile;
		this.x = tile.getX();
		this.y = tile.getY();
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
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
	
	public int getRow()
	{
		return y / 32;
	}
	
	public int getCol()
	{
		return x / 32;
	}
	
	public boolean isValidMove(int toX, int toY)
	{
		if (toX > 620  || toY > 430 || toX < 0 || toY < 0)
			return false;
		return true;
	}
	
	public boolean getIsSelected()
	{
		return isSelected;
	}
	
	public void setIsSelected(boolean s)
	{
		isSelected = s;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Tile getTile() {
		return InGameScreen.get().getGrid().getTile(y / 32, x / 32);
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

}
