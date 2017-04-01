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
import factory.AbstractUnitFactory.UnitType;
import factory.AbstractUnitFactory.WeaponType;

public class CharacterSelectionScreen extends KeyAdapter
{
	private static CharacterSelectionScreen instance = null;
	private Game game;
	
	private Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage> ();
	
	private UnitType startingCharacter = null;
	private WeaponType startingWeapon    = null;

	private static final UnitType[] characterOptions = {UnitType.Warrior, UnitType.Archer, UnitType.Wizard};
	private static final WeaponType[] weaponOptions    = {WeaponType.Sword, WeaponType.Bow, WeaponType.Fireball}; 
	int characterIndex;
	
	public CharacterSelectionScreen(Game game)
	{
		this.game = game;
		
		characterIndex = 0;
		
		loadImages(Game.g);
	}
	
	public void loadImages(Graphics g)
	{
		try
		{
			imageMap.put("Background", ImageIO.read(new File("res/background.png")));
			imageMap.put("GreyWarrior", ImageIO.read(new File("res/warriorgrey.png")));
			imageMap.put("GreyArcher", ImageIO.read(new File("res/archergrey.png")));
			imageMap.put("GreyWizard", ImageIO.read(new File("res/wizardgrey.png")));
			
			imageMap.put("MenuBox", ImageIO.read(new File("res/menubox.png")));
			
			imageMap.put("SelectedWarrior", ImageIO.read(new File("res/warrioroption.png")));
			imageMap.put("SelectedArcher", ImageIO.read(new File("res/archeroption.png")));
			imageMap.put("SelectedWizard", ImageIO.read(new File("res/wizardoption.png")));
		} 
		catch (IOException e) { e.printStackTrace(); }

	}
	
	public void render(Graphics g)
	{
		drawCharacterMenu(g);
		drawCharacterMenuNavigator(g);
	}
	
	public void tick()
	{
		
	}
	
	public void keyPressed (KeyEvent e)
	{
		int key = e.getKeyCode();
		
		handleCharacterMenu(key);
	}
	
	private void handleCharacterMenu(int key) 
	{
		switch(key)
		{
		case KeyEvent.VK_LEFT: 
			handleLeftKey();
			break;
		case KeyEvent.VK_RIGHT: 
			handleRightKey();
			break;
		case KeyEvent.VK_SPACE:
			handleSpaceKey();
			break;
		case KeyEvent.VK_ESCAPE:
			game.gameState = Game.STATE.MainMenu;
			break;
		default:
			// Other keys shouldn't do anything
		}
	}

	private void handleSpaceKey() 
	{		
		AudioPlayer.getSound("menuSelect").play();
		game.gameState = Game.STATE.Playing;
		
		startingCharacter = characterOptions[characterIndex];
		startingWeapon    = weaponOptions[characterIndex];
			
	}

	private void handleRightKey() {
		if (characterIndex == 2) { characterIndex = 0; }
		else { characterIndex++; }
		AudioPlayer.getSound("menuSelect").play();
	}

	private void handleLeftKey() {
		if (characterIndex == 0) { characterIndex = 2; }
		else { characterIndex--; }
		AudioPlayer.getSound("menuSelect").play();
	}
	
	private void drawCharacterMenu(Graphics g)
	{
			BufferedImage backgroundImage = imageMap.get("Background");
			BufferedImage warriorOption = imageMap.get("GreyWarrior");
			BufferedImage archerOption = imageMap.get("GreyArcher");
			BufferedImage wizardOption = imageMap.get("GreyWizard");
			
			g.drawImage(backgroundImage, 0, 0, null);
			g.drawImage(warriorOption, 10, 50, null);
			g.drawImage(archerOption, 220, 50, null);
			g.drawImage(wizardOption, 430, 50, null);
	}
	
	private void drawCharacterMenuNavigator(Graphics g)
	{
			BufferedImage menuBox = imageMap.get("MenuBox");
			g.drawImage(menuBox, 210, 320, null);
			
			Font optionFont = new Font("tahoma", 1, 30);
			Color optionColor = new Color(195, 210, 205, 200);
			g.setFont(optionFont);
			g.setColor(optionColor);
			
			g.drawString("Enter", 280, 355);
			
			if (characterIndex == 0)
			{
				BufferedImage selectedWarrior = imageMap.get("SelectedWarrior");
				g.drawImage(selectedWarrior, 10, 50, null);
			}
			if (characterIndex == 1)
			{
				BufferedImage selectedArcher = imageMap.get("SelectedArcher");
				g.drawImage(selectedArcher, 220, 50, null);
			}
			if (characterIndex == 2)
			{
				BufferedImage selectedWizard = imageMap.get("SelectedWizard");
				g.drawImage(selectedWizard, 430, 50, null);
			}
	}
	
	public UnitType getStartingCharacter() {
		return startingCharacter;
	}
	
	public WeaponType getStartingWeapon() {
		return startingWeapon;
	}

	public static CharacterSelectionScreen get()
	{
		if (instance == null)
			instance = new CharacterSelectionScreen(Game.get());
		
		return instance;
	}
}
