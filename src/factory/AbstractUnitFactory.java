package factory;

import client.Tile;
import gameObjects.units.Unit;
import gameObjects.weapons.Weapon;

public interface AbstractUnitFactory 
{
	public enum UnitType {Warrior, Archer, Wizard, Orc, Skeleton};
	public enum WeaponType {Sword, Bow, Fireball, Spear};
	public enum Alignment {Ally, Enemy};
	
	public Unit createUnit(Tile tile, UnitType unitType, WeaponType weaponType, int level);
	public Weapon createWeapon(WeaponType weaponType);
}
