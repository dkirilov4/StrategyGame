package managers;

import java.util.HashMap;
import java.util.Map;

import client.Grid;
import client.ID;
import factory.MagicUnitFactory;
import factory.MeleeUnitFactory;
import factory.RangedUnitFactory;
import factory.AbstractUnitFactory.UnitType;
import factory.AbstractUnitFactory.WeaponType;
import gameObjects.Cursor;
import gameObjects.units.Unit;
import items.DamagePotion;
import items.HealingPotion;
import screens.CharacterSelectionScreen;

public class StageManager
{
	private static StageManager instance = null;
	
	private Map<String, Grid> stageMap = new HashMap<String, Grid> ();
	private Map<String, Grid> overlayMap = new HashMap<String, Grid> ();
	
	private int currentStage;
	private boolean hasDrawnStage;
	
	private Unit player;
	
	private Grid currentGrid;
	private Grid overlayGrid;
	
	public StageManager()
	{
		currentStage = 1;
		hasDrawnStage = false;
		
		loadStageMaps();
		
		currentGrid = stageMap.get("StageOne");
		overlayGrid = overlayMap.get("OverlayOne");
		
		//drawStage();
	}
	
	public void loadStageMaps()
	{
		stageMap.put("StageOne", new Grid("src/map.txt", "src/tilemap.png"));
		overlayMap.put("OverlayOne", new Grid("src/overlay.txt", "src/tilemap.png"));
		
	}
	

	
	public void drawStage()
	{
		switch(currentStage)
		{
		case 1:
			//System.out.println("Stage 1");
			player = drawUnits(1, 1, CharacterSelectionScreen.get().getStartingCharacter(), CharacterSelectionScreen.get().getStartingWeapon(), 1);
			player.setId(ID.Player);
			
			
			drawUnits(2, 6, UnitType.Skeleton, WeaponType.Sword, 1);
			drawUnits(4, 14, UnitType.Orc, WeaponType.Spear, 1);
			drawUnits(5, 14, UnitType.Orc, WeaponType.Spear, 1);
			break;
		case 2:
			System.out.println("Stage 2");
			ObjectManager.get().resetTurnOrder();
			Cursor.get().setUnit(null);
			
			player.setX(3*32);
			player.setY(5*32);
			Cursor.get().setX(3*32);
			Cursor.get().setY(5*32);
			currentGrid.getTile(player.getRow(), player.getCol()).occupyTile(player);
			overlayGrid.resetTiles();
			drawUnits(6, 14, UnitType.Orc, WeaponType.Spear, 1);
			break;
		default:
			
			break;
		}
		
		hasDrawnStage = true;
	}
	
	public boolean isVictoryConditionMet()
	{
		switch(currentStage)
		{
		case 1:
			if (ObjectManager.get().areEnemiesDead())
				return true;
			break;
		case 2:
			if (ObjectManager.get().areEnemiesDead())
				return true;
			break;
			
		default:
			break;
		}
		
		return false;
	}
	
	
	private Unit drawUnits(int unitRow, int unitCol, UnitType unitType, WeaponType wepType, int level)
	{
		MeleeUnitFactory meleeFactory  = new MeleeUnitFactory();
		MagicUnitFactory magicFactory  = new MagicUnitFactory();
		RangedUnitFactory rangeFactory = new RangedUnitFactory();
		
		Unit unit = null;
		
		switch(unitType)
		{
		case Warrior:
		case Skeleton:
		case Orc:
			unit = meleeFactory.createUnit(currentGrid.getTile(unitRow, unitCol), unitType, wepType, level);
			break;
		case Archer:
			unit = rangeFactory.createUnit(currentGrid.getTile(unitRow, unitCol), unitType, wepType, level);
			unit.getInventory().addItem(new HealingPotion("", ""));
			unit.getInventory().addItem(new DamagePotion("", ""));
			break;
		case Wizard:
			unit = magicFactory.createUnit(currentGrid.getTile(unitRow, unitCol), unitType, wepType, level);
			break;		
		}
		
		ObjectManager.get().addObject(unit);
		currentGrid.getTile(unitRow, unitCol).occupyTile(unit);
		return unit;
	}

	public int getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}

	public Grid getCurrentGrid() {
		return currentGrid;
	}

	public Grid getOverlayGrid() {
		return overlayGrid;
	}
	
	public boolean isHasDrawnStage() {
		return hasDrawnStage;
	}

	public void setHasDrawnStage(boolean hasDrawnStage) {
		this.hasDrawnStage = hasDrawnStage;
	}

	public static StageManager get()
	{
		if (instance == null)
			instance = new StageManager();
		
		return instance;
	}
	

}
