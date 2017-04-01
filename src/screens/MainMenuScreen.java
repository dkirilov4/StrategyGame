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
import managers.ScreenManager;

public class MainMenuScreen extends KeyAdapter
{
	private static MainMenuScreen instance = null;
	private Game game;
	
	private Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage> ();
	
	private static final String[] mainMenuOptions = {"Play", "Help", "Quit"};
	int mainMenuOption;
	
	public MainMenuScreen(Game game)
	{
		this.game = game;
		
		mainMenuOption  = 0;
		
		loadImages(Game.g);
		AudioPlayer.getMusic("menuMusic").play();
	}
	
	public void loadImages(Graphics g)
	{
		try 
		{
			imageMap.put("Background", ImageIO.read(new File("res/background.png")));
			imageMap.put("Title", ImageIO.read(new File("res/ttext.png")));
			imageMap.put("MenuBox", ImageIO.read(new File("res/menubox.png")));
			imageMap.put("Border", ImageIO.read(new File("res/menuborder.png")));
			
			imageMap.put("Navigator", ImageIO.read(new File("res/navigator.png")));
		}
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public void render(Graphics g)
	{
		drawMainMenu(g);
		drawMainMenuNavigator(g);
	}
	
	public void tick()
	{
		
	}
	
	public void keyPressed (KeyEvent e)
	{
		int key = e.getKeyCode();
		
		handleMainMenu(key);
	}
	
	public void handleMainMenu(int key)
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
		if (mainMenuOption == 2) { mainMenuOption = 0; }
		else { mainMenuOption++; }
		
		AudioPlayer.getSound("menuSelect").play();
	}
	
	private void handleUpKey() {
		if (mainMenuOption == 0) { mainMenuOption = 2; }
		else { mainMenuOption--; }
		
		AudioPlayer.getSound("menuSelect").play();
	}

	private void handleSpaceKey() 
	{
		switch(mainMenuOption)
		{
		// Play Option
		case 0:
			AudioPlayer.getSound("menuSelect").play();
			
			if(Game.paused == false)
				game.gameState = Game.STATE.CharacterMenu;
			else
				game.gameState = Game.STATE.Playing;
			break;
			
		// Help Option
		case 1:
			selectHelpMenu();
			break;
			
		// Quit Option
		case 2:
			System.exit(1);
			break;
			
		default:
			System.out.println("In MainMenuScreen, unavailable option was selected!");
		}
	}

	private void selectHelpMenu() 
	{
		AudioPlayer.getSound("menuSelect").play();
		
		game.gameState = Game.STATE.HelpMenu;
	}
	
	private void drawMainMenu(Graphics g)
	{
		BufferedImage backgroundImage = imageMap.get("Background");
		BufferedImage menuBox = imageMap.get("MenuBox");
		BufferedImage titleText = imageMap.get("Title");
		BufferedImage menuBorder = imageMap.get("Border");
		
		Font optionFont = new Font("tahoma", 1, 30);
		Color optionColor = new Color(195, 210, 205, 200);
		g.setFont(optionFont);
		g.setColor(optionColor);
		
		g.drawImage(backgroundImage, 0, 0, null);
		g.drawImage(menuBorder, 0, 0, null);
		g.drawImage(titleText, 110, 0, null);
		
		g.drawImage(menuBox, 200, 150, null);
		g.drawString("Play", 275, 183);
		
		g.drawImage(menuBox, 200, 250, null);
		g.drawString("Help", 275, 283);
		
		g.drawImage(menuBox, 200, 350, null);
		g.drawString("Quit", 275, 383);
	}
	
	private void drawMainMenuNavigator(Graphics g)
	{
			BufferedImage menuNavigator = imageMap.get("Navigator");
			
			switch(mainMenuOption)
			{
			// Navigator on Play Button
			case 0:
				g.drawImage(menuNavigator, 150, 160, null);
				break;
				
			// Navigator on Help Button
			case 1:
				g.drawImage(menuNavigator, 150, 260, null);
				break;
			
			// Navigator on Exit Button
			case 2:
				g.drawImage(menuNavigator, 150, 360, null);
				break;
				
			default:
			}
	}
	
	public static MainMenuScreen get()
	{
		if (instance == null)
			instance = new MainMenuScreen(Game.get());
		
		return instance;
	}
	
	
}
