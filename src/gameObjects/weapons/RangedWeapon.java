package gameObjects.weapons;

public class RangedWeapon extends Weapon 
{
	private int weaponRange;
	
	public RangedWeapon()
	{
		weaponRange = 4;
	}

	public int getWeaponRange() 
	{
		return weaponRange;
	}

	public void setWeaponRange(int weaponRange) 
	{
		this.weaponRange = weaponRange;
	}
	
}