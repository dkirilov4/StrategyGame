package factory;
import client.Tile;
import gameObjects.units.Unit;
import gameObjects.units.Wizard;
import gameObjects.weapons.Fireball;
import gameObjects.weapons.Spell;

public class MagicUnitFactory implements AbstractUnitFactory
{
	public Unit createUnit(Tile tile, UnitType unitType, WeaponType weaponType, int level) 
	{
		Spell spell = createWeapon(weaponType);
		
		switch(unitType)
		{
		case Wizard:
			return new Wizard(unitType, tile, level, spell);
		default:
			break;
		}
		
		return null;
	}

	public Spell createWeapon(WeaponType weaponType) 
	{
		switch(weaponType)
		{
		case Fireball:
			return new Fireball();
		default:
			break;
		}
		
		return null;
	}
	

}