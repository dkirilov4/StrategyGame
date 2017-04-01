package factory;

public class AttackBehavior 
{
	private int radius;
	private int damage;
	
	public AttackBehavior(int r, int unitAttack, int weaponAttack)
	{
		radius = r;
		
		damage = calculateDamage(unitAttack, weaponAttack);
	}
	
	private int calculateDamage(int unitAttack, int weaponAttack)
	{
		return (unitAttack + weaponAttack);
	}
	
	public int getRadius() 
	{
		return radius;
	}

	public void setRadius(int radius) 
	{
		this.radius = radius;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
