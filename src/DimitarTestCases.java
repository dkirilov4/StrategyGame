//import static org.junit.Assert.assertEquals;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//import org.junit.Test;
//
//import client.Game;
//import client.Grid;
//import client.ID;
//import factory.AbstractUnitFactory.UnitType;
//import factory.AbstractUnitFactory.WeaponType;
//import factory.MeleeUnitFactory;
//import gameObjects.Cursor;
//import gameObjects.units.Unit;
//import managers.ObjectManager;
//
//public class DimitarTestCases {
//
//	@Test
//	public void loadMap()
//	{
//		try {
//			BufferedReader br = new BufferedReader(new FileReader("src/map.txt"));
//			
//			assert(br != null);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	@Test
//	public void getMapParemeters() throws IllegalArgumentException, IOException
//	{
//		try {
//			BufferedReader br = new BufferedReader(new FileReader("src/map.txt"));
//			
//			assert(br != null);
//			
//			int gridWidth = Integer.parseInt(br.readLine());
//			int gridHeight = Integer.parseInt(br.readLine());
//			
//			assertEquals(gridWidth, 20);
//			assertEquals(gridHeight, 15);
//			
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}	
//	}
//	
//	@Test
//	public void changeGameState()
//	{
//		Game game = new Game();
//		
//		game.gameState = Game.STATE.MainMenu;
//		
//		assert(game.gameState == Game.STATE.MainMenu);
//		
//		game.gameState = Game.STATE.Playing;
//		
//		assert(game.gameState == Game.STATE.Playing);
//	}
//	
//	@Test
//	public void addingObjects()
//	{
//		Game game = new Game();
//		ObjectManager handler = new ObjectManager();
//		Grid grid = new Grid("src/map.txt", "src/tilemap.png");
//		
//		handler.addObject(new Cursor(grid.getTile(0,0), ID.Cursor, false));
//		
//		assert(handler.isEmpty() == false);
//	}
//	
//	@Test
//	public void removingObjects()
//	{
//		Game game = new Game();
//		ObjectManager handler = new ObjectManager();
//		Grid grid = new Grid("src/map.txt", "src/tilemap.png");
//		
//		Cursor cursor = new Cursor(grid.getTile(0,0), ID.Cursor, false);
//		handler.addObject(cursor);
//		
//		assert(handler.isEmpty() == false);
//		
//		handler.removeObject(cursor);
//		assert(handler.isEmpty() == true);
//		
//	}
//	
//	@Test
//	public void getObjectCoordinates()
//	{
//		Game game = new Game();
//		ObjectManager handler = new ObjectManager();
//		Grid grid = new Grid("src/map.txt", "src/tilemap.png");
//		
//		Cursor cursor = new Cursor(grid.getTile(1,1), ID.Cursor, false);
//		
//		int x = cursor.getX();
//		int y = cursor.getY();
//		
//		assert(x == 32 && y == 32);
//	}
//	
//	@Test
//	public void cursorSelectesUnit()
//	{
//		Game game = new Game();
//		ObjectManager handler = new ObjectManager();
//		Grid grid = new Grid("src/map.txt", "src/tilemap.png");
//		
//		Cursor cursor = new Cursor(grid.getTile(1,1), ID.Cursor, false);
//		
//		MeleeUnitFactory meleeFactory = new MeleeUnitFactory();
//		
//		Unit warrior = meleeFactory.createUnit(grid.getTile(0, 0), UnitType.Warrior, WeaponType.Sword, 1);
//		cursor.setUnit(warrior);
//		
//		assert(cursor.isSelecting() == true);
//	}
//
//}
