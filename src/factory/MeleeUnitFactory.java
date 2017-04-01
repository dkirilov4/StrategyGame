package factory;

import client.Tile;
import gameObjects.units.Orc;
import gameObjects.units.Skeleton;
import gameObjects.units.Unit;
import gameObjects.units.Warrior;
import gameObjects.weapons.MeleeWeapon;
import gameObjects.weapons.Spear;
import gameObjects.weapons.Sword;

public class MeleeUnitFactory implements AbstractUnitFactory
{
	public Unit createUnit(Tile tile, UnitType unitType, WeaponType weaponType, int level) 
	{
		MeleeWeapon weapon = createWeapon(weaponType);
		
		switch(unitType)
		{
		case Warrior:
			return new Warrior(unitType, tile, level, weapon);
		case Orc:
			return new Orc(unitType, tile, level, weapon);
		case Skeleton:
			return new Skeleton(unitType, tile, level, weapon);
		default:
			break;
		}
		
		return null;
	}

	public MeleeWeapon createWeapon(WeaponType weaponType) 
	{
		switch(weaponType)
		{
		case Sword:
			return new Sword();
		case Spear:
			return new Spear();
		default:
			break;
		}
		
		return null;
	}
	

}
