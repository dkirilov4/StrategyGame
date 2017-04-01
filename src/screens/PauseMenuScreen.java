package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import client.AudioPlayer;
import client.Game;
import client.Game.STATE;

public class PauseMenuScreen extends KeyAdapter
{
	private static PauseMenuScreen instance = null;
	
	private Game game;
	
	private Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage> ();
	
	private String[] pauseMenuOptions;
	int pauseMenuOption;
	
	public PauseMenuScreen(Game game)
	{
		this.game = game;
		
		pauseMenuOption = 0;
		
		loadImages(Game.g);
	}
	
	public void loadImages(Graphics g)
	{
		try 
		{
			imageMap.put("Background", ImageIO.read(new File("res/background.png")));
			imageMap.put("MenuBox", ImageIO.read(new File("res/menubox.png")));
			imageMap.put("Border", ImageIO.read(new File("res/menuborder.png")));
			
			imageMap.put("Navigator", ImageIO.read(new File("res/navigator.png")));
		}
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public void render(Graphics g)
	{
		drawPauseMenu(g);
		drawPauseMenuNavigator(g);
	}
	
	public void tick()
	{
		
	}
	
	public void keyPressed (KeyEvent e)
	{
		int key = e.getKeyCode();
		
		handlePauseMenu(key);
	}
	
	private void handlePauseMenu(int key) 
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
		default:
			// Other keys shouldn't do anything
		}
	}

	private void handleDownKey() {
		AudioPlayer.getSound("menuSelect").play();
		
		if (pauseMenuOption == 1) { pauseMenuOption = 0; }
		else { pauseMenuOption++; }
	}
	
	private void handleUpKey() {
		AudioPlayer.getSound("menuSelect").play();
		
		if (pauseMenuOption == 0) { pauseMenuOption = 1; }
		else { pauseMenuOption--; }
	}
	
	private void handleSpaceKey() {
		AudioPlayer.getSound("menuSelect").play();
		
		if (pauseMenuOption == 0) // Resume
		{
			game.gameState = Game.STATE.Playing;
		}
		
		if (pauseMenuOption == 1) // Menu
		{
			game.gameState = Game.STATE.MainMenu;
		}
	}
	
	private void drawPauseMenu(Graphics g)
	{
			BufferedImage backgroundImage = imageMap.get("Background");
			BufferedImage menuBox = imageMap.get("MenuBox");
			BufferedImage menuBorder = imageMap.get("Border");
			
			Font optionFont = new Font("tahoma", 1, 30);
			Color optionColor = new Color(195, 210, 205, 200);
			g.setFont(optionFont);
			g.setColor(optionColor);
			
			g.drawImage(backgroundImage, 0, 0, null);
			g.drawImage(menuBorder, 0, 0, null);
			
			g.drawImage(menuBox, 200, 150, null);
			g.drawString("Resume", 250, 183);
			
			g.drawImage(menuBox, 200, 250, null);
			g.drawString("Menu", 270, 283);
	}
	
	private void drawPauseMenuNavigator(Graphics g)
	{
			BufferedImage menuNavigator = imageMap.get("Navigator");
			
			if (pauseMenuOption == 0)
			{
				g.drawImage(menuNavigator, 150, 160, null);
			}
			else if (pauseMenuOption == 1)
			{
				g.drawImage(menuNavigator, 150, 260, null);
			}
	}

	public static PauseMenuScreen get()
	{
		if (instance == null)
			instance = new PauseMenuScreen(Game.get());
		
		return instance;
	}
}
