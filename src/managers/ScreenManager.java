package managers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import client.Game;
import client.Game.STATE;
import screens.CharacterSelectionScreen;
import screens.HelpMenuScreen;
import screens.InGameScreen;
import screens.InventoryScreen;
import screens.MainMenuScreen;
import screens.PauseMenuScreen;
import screens.VictoryScreen;

public class ScreenManager extends KeyAdapter
{
	private static ScreenManager instance = null;
	
	public ScreenManager()
	{
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(Game.get().gameState)
		{
		case MainMenu:
			MainMenuScreen.get().keyPressed(e);
			break;
		case HelpMenu:
			HelpMenuScreen.get(Game.get()).keyPressed(e);
			break;
		case PauseMenu:
			PauseMenuScreen.get().keyPressed(e);
			break;
		case CharacterMenu:
			CharacterSelectionScreen.get().keyPressed(e);
			break;
		case Playing:
			InGameScreen.get().getKeyInput().keyPressed(e);
			break;
		case Inventory:
			InventoryScreen.get().keyPressed(e);
			break;
		case Victory:
			VictoryScreen.get().keyPressed(e);
			break;
		default:
			break;
		}
	}
	
	public void renderActiveScreen()
	{
		
		switch(Game.get().gameState)
		{
		case MainMenu:
			MainMenuScreen.get().render(Game.g);
			break;
			
		case HelpMenu:
			HelpMenuScreen.get(Game.get()).render(Game.g);
			break;
			
		case PauseMenu:
			PauseMenuScreen.get().render(Game.g);
			break;
			
		case CharacterMenu:
			CharacterSelectionScreen.get().render(Game.g);
			break;
			
		case Playing:
			InGameScreen.get().render(Game.g);
			break;
			
		case Inventory:
			InventoryScreen.get().render();
			break;
		case Victory:
			VictoryScreen.get().render();
			break;
			
		default:
			// Shouldn't ever get here;
			break;
		}
	}
	
	public void tickActiveScreen()
	{
		switch(Game.get().gameState)
		{
		case MainMenu:
			MainMenuScreen.get().tick();
			break;
			
		case HelpMenu:
			HelpMenuScreen.get(Game.get()).tick();
			break;
			
		case PauseMenu:
			PauseMenuScreen.get().tick();
			break;
			
		case CharacterMenu:
			CharacterSelectionScreen.get().tick();
			break;
			
		case Playing:
			InGameScreen.get().tick();
			break;
			
		default:
			// Shouldn't ever get here;
			break;
		}
	}
	
	public static ScreenManager get()
	{
		if (instance == null)
			instance = new ScreenManager();
		
		return instance;
	}
}
