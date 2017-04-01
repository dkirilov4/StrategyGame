package items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameObjects.units.Unit;

public class DamagePotion extends Item
{
	private int damageAdded;
	
	
	public DamagePotion(String name, String type) 
	{
		super(name, type);
		
		damageAdded = 50;
		this.setDescription("Adds " + damageAdded + " damage upon use.");
		
		try
		{
			BufferedImage icon = ImageIO.read(new File("res/items/P_Damage.png"));
			this.setIcon(icon);
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void use(Unit unit)
	{
		unit.setCurrentAttackRating(unit.getBaseAttackRating() + 20);
	}
	
}
