package screens;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.Game;
import client.Grid;
import client.KeyInput;
import client.Game.STATE;
import gameObjects.Cursor;
import managers.ObjectManager;
import managers.StageManager;

public class InGameScreen 
{
	private static InGameScreen instance = null;
	
	private Grid grid;
	private Grid overlay;
	
	private KeyInput keyInput;
	
	
	public InGameScreen()
	{	
		grid = StageManager.get().getCurrentGrid();
		overlay = StageManager.get().getOverlayGrid();
		
		keyInput = new KeyInput(grid, overlay);
		
		//ObjectManager.get().addObject(cursor = new Cursor(grid.getTile(0,0), ID.Cursor, false));
		
		StageManager.get().drawStage();
	}
	
	public void render(Graphics g)
	{	
		grid.render(g);
		ObjectManager.get().render(g);
		overlay.render(g);
		Cursor.get().render(Game.g);
	}
	
	public void tick()
	{
		if (StageManager.get().isVictoryConditionMet() && StageManager.get().isHasDrawnStage())
		{
			System.out.println("You beat stage " + StageManager.get().getCurrentStage() + "!");
			// Display Stuff Later
			StageManager.get().setCurrentStage(StageManager.get().getCurrentStage() + 1);
			StageManager.get().drawStage();
			
			Game.get().gameState = Game.STATE.Victory;
		}
	}
	
	public void keyPressed (KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_ESCAPE:
			handleEscapeKey();
			break;
			
		case KeyEvent.VK_I:
			handleIKey();
			break;
		}

	}

	private void handleEscapeKey() {
		Game.get().gameState = Game.STATE.PauseMenu;
		Game.paused = true;
	}
	
	private void handleIKey()
	{
		Game.get().gameState = Game.STATE.Inventory;
	}

	public KeyInput getKeyInput() {
		return keyInput;
	}
	
	public Grid getGrid() {
		return grid;
	}

	public static InGameScreen get()
	{
		if (instance == null)
			instance = new InGameScreen();
		
		return instance;
	}
		
}
