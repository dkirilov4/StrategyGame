package client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import items.Item;

public class Inventory
{
	private BufferedImage inventoryTab;
	private BufferedImage itemNavigator;
	
	private static final int inventoryCapacity = 10;
	private ArrayList<Item> inventory = new ArrayList<Item>(inventoryCapacity);

	private int  currentItemSlot;
	
	private boolean isOpen;
	
	public Inventory()
	{
		isOpen = false;
		currentItemSlot = 0;
		
		try 
		{
			inventoryTab = ImageIO.read(new File("res/inventory.png"));
			itemNavigator = ImageIO.read(new File("res/inventoryNavigator.png"));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void addItem(Item i)
	{
		if (inventory.size() < inventoryCapacity)
			inventory.add(i);
	}
	
	public void removeItem(Item i)
	{
		inventory.remove(i);
	}
	
	public void render()
	{
		Game.g.drawImage(inventoryTab, 0, 0, null);
		
		int itemSlot = 0;
		for (Item i : inventory)
		{
			i.render(Game.g, 14*32, itemSlot*32 + 64);
			itemSlot++;
		}
		
		drawItemNavigator(currentItemSlot);
	}
	
	public void drawItemNavigator(int currentItemSlot)
	{
		Game.g.drawImage(itemNavigator, 580, currentItemSlot*32 + 70, null);
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public boolean isFull()
	{
		return (inventory.size() >= inventoryCapacity);
	}
	
	public boolean containsItem()
	{
		return (inventory.size() > currentItemSlot && inventory.get(currentItemSlot) != null);
	}
	
	public void removeItemAtCurrentSlot()
	{
		inventory.remove(currentItemSlot);
	}

	public int getCurrentItemSlot() {
		return currentItemSlot;
	}

	public void setCurrentItemSlot(int currentItemSlot) {
		this.currentItemSlot = currentItemSlot;
	}
	
	public Item getItemAtCurrentSlot() {
		return inventory.get(currentItemSlot);
	}
}
