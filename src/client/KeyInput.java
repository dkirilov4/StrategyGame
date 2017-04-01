package client;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import gameObjects.Cursor;
import gameObjects.GameObject;
import gameObjects.units.Unit;
import managers.ObjectManager;
import screens.InventoryScreen;

public class KeyInput extends KeyAdapter
{
	private boolean turnsDisabled = true;
	
	Grid grid;
	Grid overlay;
	
	public KeyInput(Grid grid, Grid overlay)
	{
		this.grid = grid;
		this.overlay = overlay;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_ESCAPE)
		{
			Game.get().gameState = Game.STATE.PauseMenu;
			Game.paused = true;
		}
		else
		{
			handleCursor(key);	
		}

	}
	
	private void handleCursor(int key)
	{	
		switch(key)
		{
		case KeyEvent.VK_UP:
			moveCursorUp();
			break;
		case KeyEvent.VK_DOWN:
			moveCursorDown();
			break;	
		case KeyEvent.VK_LEFT:
			moveCursorLeft();
			break;
		case KeyEvent.VK_RIGHT:
			moveCursorRight();
			break;
		case KeyEvent.VK_SPACE:
			handleSpaceKey();
			break;
		case KeyEvent.VK_I:
			handleInventory();
		case KeyEvent.VK_M:
			doAIMovement();
			break;
		default:
			// Do nothing for any other key
			break;
			
		}	
	}

	private void handleInventory()
	{
		if (Cursor.get().isSelecting()) 
		{
			Unit selectedUnit = Cursor.get().getUnit();
			
			if (selectedUnit.getInventory().isOpen() == false)
			{
				InventoryScreen.get().setUnit(selectedUnit);
				selectedUnit.getInventory().setOpen(true);
				
				Game.get().gameState = Game.STATE.Inventory;
				// Once we are done with the Inventory, it means we closed it, so set the open to false again
				selectedUnit.getInventory().setOpen(false);
			}
		}
	}

	private void handleSpaceKey() 
	{
		Tile cursorTile = grid.getTile(Cursor.get().getRow(), Cursor.get().getCol());
		Unit cursorUnit = cursorTile.getUnit();
		
			if (cursorTile.isOccupied())
			{	
				if (!Cursor.get().isSelecting())
					selectUnit(cursorTile, cursorUnit);
				else
					attackUnit(cursorTile, cursorUnit);
			}
			else if (Cursor.get().isSelecting())
			{
				moveUnit(cursorTile);
			}
	}
		
	private void moveCursorLeft() {
		if (Cursor.get().isValidMove((int)Cursor.get().getX() - 32, (int)Cursor.get().getY()))
		{
			AudioPlayer.getSound("cursorMove").play();
			Cursor.get().setX(Cursor.get().getX() - 32);
		}

	}

	private void moveCursorRight() {
		if (Cursor.get().isValidMove((int)Cursor.get().getX() + 32, (int)Cursor.get().getY()))
		{
			AudioPlayer.getSound("cursorMove").play();
			Cursor.get().setX(Cursor.get().getX() + 32);
		}

	}

	private void moveCursorDown() {
		if (Cursor.get().isValidMove((int)Cursor.get().getX(), (int)Cursor.get().getY() + 32))
		{
			AudioPlayer.getSound("cursorMove").play();
			Cursor.get().setY(Cursor.get().getY() + 32);
		}

	}

	private void moveCursorUp() {
		if (Cursor.get().isValidMove(Cursor.get().getX(), Cursor.get().getY() - 32))
		{
			AudioPlayer.getSound("cursorMove").play();
			Cursor.get().setY(Cursor.get().getY() - 32);
		}

	}

	private void moveUnit(Tile curTile) 
	{
		Unit curUnit = Cursor.get().getUnit();
		Tile fromTile = grid.getTile(curUnit.getRow(), curUnit.getCol());
		
		if (!curUnit.hasEndedTurn() && ObjectManager.get().isUnitsTurn(curUnit) || turnsDisabled)
		{
			int x = curUnit.getX();
			int y = curUnit.getY();
			
			int moveRange = curUnit.getMoveBehavior().getRadius();
			
			if (isValidMove(x, y, curTile.getX(), curTile.getY(), moveRange))
			{
				curUnit.setX(Cursor.get().getX());
				curUnit.setY(Cursor.get().getY());
				curTile.occupyTile(curUnit);
				fromTile.releaseTile();
				curUnit.setIsSelected(false);
				Cursor.get().setUnit(null); 
				overlay.resetTiles();
				
				curUnit.setMoved(true);
			}
			else
			{
				System.out.println("Not a valid move!");
			}
		}
	}

	private void attackUnit(Tile curTile, Unit curUnit) 
	{
		Unit attackingUnit = Cursor.get().getUnit();
		Unit attackedUnit  = curTile.getUnit();
		
		if (!attackingUnit.hasEndedTurn() && ObjectManager.get().isUnitsTurn(attackingUnit) || turnsDisabled)
		{
			int fromX = Cursor.get().getUnit().getX();
			int fromY = Cursor.get().getUnit().getY();
			int toX = curUnit.getX();
			int toY = curUnit.getY();
			
			int attackRange = Cursor.get().getUnit().getAttackBehavior().getRadius();
			
			if (isValidAttack(fromX, fromY, toX, toY, attackRange) && !attackingUnit.equals(attackedUnit)) 
			{

				
				attackedUnit.setCurrentHealthPoints((attackedUnit).getCurrentHealthPoints() - attackingUnit.getCurrentAttackRating());
				
				if (attackedUnit.isDead()) 
				{
					attackingUnit.setCurrentEXP(attackingUnit.getCurrentEXP() + attackedUnit.getEXPOnDeath());
					System.out.println("Unit gained: " + attackedUnit.getEXPOnDeath() + " and is now at: " + attackingUnit.getCurrentEXP() + " exp");
					curTile.releaseTile();
					ObjectManager.get().removeObject(attackedUnit);
				}
				
				attackingUnit.setAttacked(true);
				curUnit.setIsSelected(false);
				Cursor.get().setUnit(null); 
				overlay.resetTiles();
			}
			else
			{
				System.out.println("Unit not in range. Can't attack!");
			}
		}

	}

	private void selectUnit(Tile curTile, Unit curUnit)
	{
		if (!curUnit.hasEndedTurn() && ObjectManager.get().isUnitsTurn(curUnit) || turnsDisabled)
		{
			Cursor.get().setUnit(curUnit);
			curUnit.setIsSelected(true);
			
			// Display Available Moves
			int x = curTile.getX();
			int y = curTile.getY();
			
			Unit tileUnit = curTile.getUnit();
			
			int unitMoveRange = tileUnit.getMoveBehavior().getRadius();
			int unitAttackRange = tileUnit.getAttackBehavior().getRadius();
			int moveRange = (unitMoveRange * 32);
			int attackRange = (unitAttackRange * 32);
			
			
			drawUnitRange(x, y, moveRange, attackRange);
			
			//curTile.releaseTile();
		}
	}

	private void drawUnitRange(int x0, int y0, int moveRange, int attackRange) 
	{		
		if (moveRange > attackRange) {
			drawMoveRange(x0, y0, moveRange);
			drawAttackRange(x0, y0, attackRange);
		}
		else
		{
			drawAttackRange(x0, y0, attackRange);
			drawMoveRange(x0, y0, moveRange);
		}
	}

	private void drawAttackRange(int unitX, int unitY, int attackRange) {
		for (int i = -attackRange; i <= attackRange; i += 32)
		{
			for (int j = -attackRange; j <= attackRange; j += 32)
			{
				if (Math.abs(i) + Math.abs(j) <= attackRange + attackRange*0.7f)
				{
					if (unitX + i >= 0 && unitY + j >= 0 && unitX + i < (Game.WIDTH) && unitY + j < (Game.HEIGHT))
					{
						int row = unitY/32 + j/32;
						int col = unitX/32 + i/32;
						overlay.getTile(row, col).setType(4);
					}
				}
			}
		}
	}

	private void drawMoveRange(int unitX, int unitY, int moveRange) {
		for (int i = -moveRange; i <= moveRange; i += 32)
		{
			for (int j = -moveRange; j <= moveRange; j += 32)
			{
				if (Math.abs(i) + Math.abs(j) <= moveRange + moveRange*0.7f)
				{
					if (unitX + i >= 0 && unitY + j >= 0 && unitX + i < (Game.WIDTH) && unitY + j < (Game.HEIGHT))
					{
						int row = unitY/32 + j/32;
						int col = unitX/32 + i/32;
						overlay.getTile(row, col).setType(3);
					}

				}
			}
		}
	}
	
	public boolean isValidMove(int unitX, int unitY, int curX, int curY, int moveRange)
	{
		moveRange = moveRange * 32;
		
		for (int i = -moveRange; i <= moveRange; i += 32)
		{
			for (int j = -moveRange; j <= moveRange; j += 32)
			{
				if (Math.abs(i) + Math.abs(j) <= moveRange + moveRange*0.7f)
				{
					if (curX == unitX + i && curY == unitY + j && !grid.getTile(curY / 32, curX / 32).isOccupied())
						return true;
				}
			}
		}
		return false;
	}
		
	
	public boolean isValidAttack(int unitX, int unitY, int curX, int curY, int attackRange)
	{
		attackRange = attackRange * 32;
		
		for (int i = -attackRange; i <= attackRange; i += 32)
		{
			for (int j = -attackRange; j <= attackRange; j += 32)
			{
				if (Math.abs(i) + Math.abs(j) <= attackRange + attackRange*0.7f)
				{
					if (curX == unitX + i && curY == unitY + j)
						return true;
				}
			}
		}
		return false;
	}
	
	public boolean isCursorOver(int curX, int curY, int unitX, int unitY)
	{
		return true;
	}
	
	public void doAIMovement() // parameter Unit smartUnit
	{
		if (Cursor.get().isSelecting())
		{
			Unit smartUnit = Cursor.get().getUnit();
			
			System.out.println("Current Unit's x: " + smartUnit.getX() + " y: " + smartUnit.getY());
			
			Unit closestUnit = ObjectManager.get().getNearestUnit(smartUnit);
			
			moveToCosestAvailableTile(smartUnit, closestUnit);
		}
	}
	
	public void moveToCosestAvailableTile(Unit curUnit, Unit closestUnit)
	{
		Tile closestTile = null;
		
		int xDirection = 0, yDirection = 0;
		
		final int leftDirection  = -32;
		final int rightDirection =  32;
		final int downDirection  =  32;
		final int upDirection    = -32;
		
		int curX = curUnit.getX();
		int curY = curUnit.getY();
		
		int closestX = closestUnit.getX();
		int closestY = closestUnit.getY();
		
		int deltax = curX - closestX;
		int deltay = curY - closestY;
		
		if (deltax > 0) // Enemy is to the right of player
			xDirection = leftDirection;
		if (deltax < 0) // Enemy is to the left of player
			xDirection = rightDirection;
		if (deltay > 0) // Enemy is below player
			yDirection = upDirection;
		if (deltay < 0)  // Enemy is above the player
			yDirection = downDirection;
		
		
		int x = curX, y = curY;
		
		if (x == closestX || y == closestY)
		{
			System.out.println("Straight line");
			
			deltax = x - closestX;
			deltay = y - closestY;
			
			if (deltax > 0) // Enemy is to the right of player
				xDirection = leftDirection;
			if (deltax < 0) // Enemy is to the left of player
				xDirection = rightDirection;
			if (deltay > 0) // Enemy is below player
				yDirection = upDirection;
			if (deltay < 0)  // Enemy is above the player
				yDirection = downDirection;
			
			if (x == closestX) // If x's are the same, then we only want to move in the y direction
			{
				while (y != closestY)
				{
					int nextY = y + yDirection;
					
					if (isValidMove(curUnit.getX(), curUnit.getY(), x, nextY, curUnit.getMoveBehavior().getRadius()))
					{
						y = nextY;
						
						grid.getTile(y/32, x/32).setType(3);
					}
					else
					{	
						moveUnit(curUnit, x, y);	
						break;
					}
				}
				
			}
			
			else // Otherwise, we are moving in the x direction
			{
				while (x != closestX)
				{
					int nextX = x + xDirection;
					
					if (isValidMove(curUnit.getX(), curUnit.getY(), nextX, y, curUnit.getMoveBehavior().getRadius()))
					{
						x = nextX;
						
						grid.getTile(y/32, x/32).setType(3);
					}
					else
					{	
						moveUnit(curUnit, x, y);	
						break;
					}
				}
			}
			moveUnit(curUnit, x, y);
		}
		else
		{
			while (x != closestX && y != closestY)
			{	
				int nextX = x + xDirection;
				int nextY = y + yDirection;
				
				if (isValidMove(curUnit.getX(), curUnit.getY(), nextX, nextY, curUnit.getMoveBehavior().getRadius()))
				{
					x = nextX;
					y = nextY;
					
					grid.getTile(y/32, x/32).setType(3);
				}
				else
				{	
					//moveUnit(curUnit, x, y);	
					break;
				}
			}
			moveUnit(curUnit, x, y);
		}
		

		System.out.println("x: " + x + " y: " + y);
	}

	private void moveUnit(Unit curUnit, int x, int y) {
		int prevX = curUnit.getX();
		int prevY = curUnit.getY();
		
		if (prevX == x && prevY == y)	// can't move on top of itself
		{
			curUnit.setIsSelected(false);
			Cursor.get().setUnit(null);
			overlay.resetTiles();
			return;
		}
		
		curUnit.setX(x);
		curUnit.setY(y);
		grid.getTile(y / 32, x / 32).occupyTile(curUnit);
		grid.getTile(prevY / 32, prevX / 32).releaseTile();
		curUnit.setIsSelected(false);
		Cursor.get().setUnit(null);
		overlay.resetTiles();
		
		curUnit.setMoved(true);
	}
}
