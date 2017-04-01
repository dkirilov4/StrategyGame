package screens;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import client.Game;
import client.Game.STATE;

public class HelpMenuScreen extends KeyAdapter
{
	private static HelpMenuScreen instance = null;
	
	private Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage> ();

	public HelpMenuScreen()
	{
		loadImages(Game.g);
	}
	
	public void loadImages(Graphics g)
	{
		try 
		{
			imageMap.put("Background", ImageIO.read(new File("res/background.png")));
			imageMap.put("Controls", ImageIO.read(new File("res/helpkeys.png")));
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void render(Graphics g)
	{
		drawHelpMenu(g);
	}
	
	public void tick()
	{
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		handleHelpMenu(key);
		
	}
	
	private void handleHelpMenu(int key) 
	{
		if (key == KeyEvent.VK_ESCAPE)
		{
			Game.get().gameState = Game.STATE.MainMenu;
		}
	}
	
	private void drawHelpMenu(Graphics g)
	{
			BufferedImage backgroundImage = imageMap.get("Background");
			BufferedImage helpfulKeys = imageMap.get("Controls");
			
			g.drawImage(backgroundImage, 0, 0, null);
			g.drawImage(helpfulKeys, 0, 0, null);
	}
	
	public static HelpMenuScreen get(Game game)
	{
		if (instance == null)
			instance = new HelpMenuScreen();
		
		return instance;
	}
}
