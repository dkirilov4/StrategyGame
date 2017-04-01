package gameObjects.weapons;

import factory.AttackBehavior;

public abstract class Weapon 
{
	private AttackBehavior attackBehavior;
	
	private int attackRating;
	
	public Weapon()
	{
		attackRating = 0;
	}
	
	
	public AttackBehavior getAttackBehavior() {
		return attackBehavior;
	}

	public void setAttackBehavior(AttackBehavior attackBehavior) {
		this.attackBehavior = attackBehavior;
	}

	public int getAttackRating() {
		return attackRating;
	}

	public void setAttackRating(int attackRating) {
		this.attackRating = attackRating;
	}

}
