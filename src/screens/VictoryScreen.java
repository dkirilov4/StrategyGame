package screens;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.AudioPlayer;
import client.Game;
import client.Game.STATE;
import items.Item;
import managers.ObjectManager;

public class VictoryScreen extends KeyAdapter
{
	private static VictoryScreen instance = null;
	
	private BufferedImage victoryScreen;
	private BufferedImage itemNavigator;
	private BufferedImage[] itemIcons;
	private Item[] itemRewards;
	
	private int itemIndex;
	
	public VictoryScreen()
	{
		itemIndex = 0;
		itemIcons = new BufferedImage[3];
		itemRewards = new Item[3];
		
		loadImages();
	}
	
	public void loadImages()
	{
		try 
		{
			victoryScreen = ImageIO.read(new File("res/victoryscreen.png"));
			itemNavigator = ImageIO.read(new File("res/victorynavigator.png"));
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		handleVictoryScreen(key);
	}

	public void handleVictoryScreen(int key)
	{
		switch(key)
		{
		case KeyEvent.VK_DOWN:
			handleDownKey();
			break;
		case KeyEvent.VK_UP:
			handleUpKey();
			break;
			
		case KeyEvent.VK_SPACE:
			handleSpaceKey();
			break;
		}
	}
	
	public void handleDownKey()
	{
		if (itemIndex == 2) { itemIndex = 0; }
		else { itemIndex++; }
		AudioPlayer.getSound("menuSelect").play();
	}
	
	public void handleUpKey()
	{
		if (itemIndex == 0) { itemIndex = 2; }
		else { itemIndex--; }
		AudioPlayer.getSound("menuSelect").play();
	}
	
	public void handleSpaceKey()
	{
		Game.get().gameState = STATE.Playing;
	}
	
	public void drawItemNavigator()
	{	
		switch(itemIndex)
		{
		case 0:
			Game.g.drawImage(itemNavigator, 178, 221, null);
			break;
		case 1:
			Game.g.drawImage(itemNavigator, 178, 299, null);
			break;
		case 2:
			Game.g.drawImage(itemNavigator, 178, 373, null);
			break;
		}
	}
	
	public void render()
	{
		InGameScreen.get().getGrid().render(Game.g);
		ObjectManager.get().render(Game.g);
		Game.g.drawImage(victoryScreen, 0, 0, null);
		
		drawItemNavigator();
	}
	
	public void tick()
	{
		
	}
	
	public static VictoryScreen get()
	{
		if (instance == null)
			instance = new VictoryScreen();
		
		return instance;
	}
}