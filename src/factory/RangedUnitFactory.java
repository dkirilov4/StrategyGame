package factory;
import client.Tile;
import gameObjects.units.Archer;
import gameObjects.units.Unit;
import gameObjects.weapons.Bow;
import gameObjects.weapons.RangedWeapon;

public class RangedUnitFactory implements AbstractUnitFactory
{
	public Unit createUnit(Tile tile, UnitType unitType, WeaponType weaponType, int level) 
	{
		RangedWeapon weapon = createWeapon(weaponType);
		
		switch(unitType)
		{
		case Archer:
			return new Archer(unitType, tile, level, weapon);
		default:
			break;
		}
		
		return null;
	}

	public RangedWeapon createWeapon(WeaponType weaponType) 
	{
		switch(weaponType)
		{
		case Bow:
			return new Bow();
		default:
			break;
		}
		
		return null;
	}
	

}