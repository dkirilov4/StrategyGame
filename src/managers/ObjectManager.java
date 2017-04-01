package managers;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import factory.AbstractUnitFactory.Alignment;
import gameObjects.units.Unit;
import gameObjects.units.Unit.Player;
import screens.InGameScreen;

public class ObjectManager 
{
	private static ObjectManager instance = null;
	
	public LinkedList<Unit> units = new LinkedList<Unit>();
	
	public LinkedList<Unit> turnOrder = new LinkedList<Unit>();
	public Player currentPlayerTurn = Player.PlayerOne;
	
	public void tick()
	{
		for (int i = 0; i < units.size(); ++i)
		{
			Unit currentUnit = units.get(i);
			
			currentUnit.tick();
			
//			if (currentUnit.getBelongsTo() == Player.Computer && isUnitsTurn(currentUnit))
//			{
//				InGameScreen.get().getKeyInput().doAIMovement(currentUnit);
//			}
			
			if (isPlayerDone())
			{
				switchPlayerTurn();
			}
			 
		}
	}
	
	private void switchPlayerTurn()
	{
		for (int i = 0; i < units.size(); ++i)
		{
			Unit currentUnit = units.get(i);
			
			if ((currentUnit.getBelongsTo() == currentPlayerTurn))
			{
				currentUnit.setEndedTurn(false);	
			}
		}
		
		if (currentPlayerTurn == Player.PlayerOne)
			currentPlayerTurn = Player.Computer;
		else
			currentPlayerTurn = Player.Computer;
	}
	
	private boolean isPlayerDone()
	{
		for (int i = 0; i < units.size(); ++i)
		{
			Unit currentUnit = units.get(i);
			
			if ((currentUnit.getBelongsTo() == currentPlayerTurn) && !currentUnit.hasEndedTurn())
				return false;
		}
		
		return true;
	}
	
	public void render(Graphics g)
	{
		for (int i = 0; i < units.size(); ++i)
		{
			units.get(i).render(g);
		}
	}
	
	public void addObject(Unit object)
	{
		if (object.getBelongsTo() == Player.PlayerOne)
		{
			turnOrder.addFirst(object);
		}
		else if (object.getBelongsTo() == Player.Computer)
		{
			turnOrder.addLast(object);
		}
		this.units.add(object);
	}
	
	public void removeObject(Unit object)
	{
		this.units.remove(object);
		this.turnOrder.remove(object);
	}
	
	public boolean isEmpty()
	{
		return (units.isEmpty());
	}
	
	public boolean areEnemiesDead()
	{
		for (int i = 0; i < units.size(); ++i)
		{
			if (units.get(i).getAlignment() == Alignment.Enemy)
				return false;
		}
		
		return true;
	}
	
	public boolean isUnitsTurn(Unit unit)
	{
		for (int i = 0; i < units.size(); ++i)
		{
			Unit currentUnit = units.get(i);
			if (currentUnit.equals(unit))
			{
				if (currentUnit.getBelongsTo() == currentPlayerTurn)
					return (!currentUnit.hasAttacked() && !currentUnit.hasMoved());
			}
		}
		return false;
	}
	
	public void resetTurnOrder()
	{
		currentPlayerTurn = Player.PlayerOne;
		
		for (int i = 0; i < units.size(); ++i)
		{
			units.get(i).setEndedTurn(false);
		}
	}
	
	public Unit getNearestUnit(Unit curUnit)
	{
			Unit closestUnit = null;
			float minDistance = 10000;
		
		for (int i = 0; i < units.size(); ++i)
		{
			Unit tempUnit = units.get(i);
			if (!curUnit.equals(tempUnit))
			{
				float xdifference = curUnit.getX() - tempUnit.getX();
				float ydifference = curUnit.getY() - tempUnit.getY();
				
				float distance = (float) Math.sqrt(((xdifference * xdifference) + (ydifference * ydifference)));
				
				if (distance < minDistance)
				{
					closestUnit = tempUnit;
					minDistance = distance;
				}	
			}
		}
		
		//System.out.println("Closest Unit is " + minDistance + " away, and is located at x: " + closestUnit.getX() + " y: " + closestUnit.getY());
		
		return closestUnit;
	}
	
	public static ObjectManager get()
	{
		if (instance == null)
			instance = new ObjectManager();
		
		return instance;
	}
}
