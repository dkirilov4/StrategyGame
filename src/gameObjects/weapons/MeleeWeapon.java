package gameObjects.weapons;

public class MeleeWeapon extends Weapon 
{
	private int weaponRange;
	
	public MeleeWeapon()
	{
		weaponRange = 1;
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