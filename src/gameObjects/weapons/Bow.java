package gameObjects.weapons;
import gameObjects.weapons.RangedWeapon;

public class Bow extends RangedWeapon 
{
	public Bow() 
	{
		super();
		setWeaponRange(4);
		setAttackRating(20);
	}
}
