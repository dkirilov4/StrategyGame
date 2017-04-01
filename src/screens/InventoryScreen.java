package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.AudioPlayer;
import client.Game;
import client.Inventory;
import client.Game.STATE;
import factory.AbstractUnitFactory.UnitType;
import gameObjects.units.Unit;

public class InventoryScreen
{
	private static InventoryScreen instance = null;
	
	private BufferedImage warriorInv;
	private BufferedImage archerInv;
	private BufferedImage wizardInv;
	
	Unit unit;
	
	public InventoryScreen()
	{
		try {
			warriorInv = ImageIO.read(new File("res/warriorinventory.png"));
			archerInv = ImageIO.read(new File("res/archerinventory.png"));
			wizardInv = ImageIO.read(new File("res/wizardinventory.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render()
	{		
		Game.g.setFont(new Font("tahoma", 1, 48));
		
		unit.getInventory().render();
		
		UnitType unitType = unit.getUnitType();
		switch(unitType)
		{
		case Warrior:
			Game.g.drawString("Warrior", 108, 65);
			Game.g.drawImage(warriorInv, 184, 136, null);
			break;
		case Archer:
			Game.g.drawString("Archer", 118, 65);
			Game.g.drawImage(archerInv, 156, 112, null);
			break;
		case Wizard:
			Game.g.drawString("Wizard", 118, 65);
			Game.g.drawImage(wizardInv, 158, 110, null);
			break;
		}
		
		Game.g.setColor(new Color(30, 20, 5));
		Game.g.setFont(new Font("tahoma", 1, 18));
		
		Game.g.drawString("Lv:     " + unit.getLevel(), 50, 180);
		Game.g.drawString("HP:    " + unit.getCurrentHealthPoints() + " / " + unit.getMaxHealthPoints(), 50, 200);
		Game.g.drawString("Atk:   " + unit.getCurrentAttackRating(), 50, 220);
		Game.g.drawString("Exp:   " + unit.getCurrentEXP(), 50, 240);
		
		Game.g.drawString("STR:   " + unit.getBaseSTR(), 50, 300);
		Game.g.drawString("AGI:   " + unit.getBaseAGI(), 50, 320);
		Game.g.drawString("INT:   " + unit.getBaseINT(), 50, 340);
		
		Game.g.setColor(new Color(25, 180, 15));
		Game.g.drawString("+ " + unit.getAttackBehavior().getDamage(), 145, 220);
	}
	
	public void keyPressed (KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_I:
			Game.get().gameState = Game.STATE.Playing;
			break;
		case KeyEvent.VK_DOWN:
			handleDownKey();
			break;
		case KeyEvent.VK_UP:
			handleUpKey();
			break;
		case KeyEvent.VK_SPACE:
			handleSpaceKey();
		default:
			// Other keys do nothing
			break;
		}

	}
	
	public void handleDownKey()
	{
		Inventory inventory = unit.getInventory();
		
		AudioPlayer.getSound("menuSelect").play();
		
		if (inventory.getCurrentItemSlot() + 1 >= 10)
			inventory.setCurrentItemSlot(0);
		else
			inventory.setCurrentItemSlot(inventory.getCurrentItemSlot() + 1);
	}
	
	public void handleUpKey()
	{
		Inventory inventory = unit.getInventory();
		
		AudioPlayer.getSound("menuSelect").play();

		if (inventory.getCurrentItemSlot() - 1 < 0)
			inventory.setCurrentItemSlot(10);
		else
			inventory.setCurrentItemSlot(inventory.getCurrentItemSlot() - 1);
	}
	
	public void handleSpaceKey()
	{
		Inventory inventory = unit.getInventory();
		
		if (inventory.containsItem())
			
		{
			AudioPlayer.getSound("menuSelect").play();
			inventory.getItemAtCurrentSlot().use(unit);
			inventory.removeItemAtCurrentSlot();
		}

	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public static InventoryScreen get()
	{
		if (instance == null)
			instance = new InventoryScreen();
		
		return instance;
	}
	
}
