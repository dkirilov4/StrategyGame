package items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameObjects.units.Unit;

public class HealingPotion extends Item
{
	private int amountToHeal;
	
	
	public HealingPotion(String name, String type) 
	{
		super(name, type);
		
		amountToHeal = 50;
		this.setDescription("Heals " + amountToHeal + " health upon use.");
		
		try
		{
			BufferedImage icon = ImageIO.read(new File("res/items/P_Healing_Small.png"));
			this.setIcon(icon);
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void use(Unit unit)
	{
		unit.setCurrentHealthPoints(Math.min(unit.getCurrentHealthPoints() + 50, unit.getMaxHealthPoints()));
	}
	
}
