package items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameObjects.units.Unit;

public abstract class Item 
{	
	private BufferedImage icon;
	
	private String name;
	private String type;	// healing or stat booster
	private String description;
	
	public Item(String name, String type)
	{
		this.name = name;
		this.type = type;
	}
	
	public void render(Graphics g, int x, int y)
	{
		g.drawImage(icon, x, y, null);
	}
	
	public abstract void use(Unit unit);

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public BufferedImage getIcon() {
		return icon;
	}

	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}
}
