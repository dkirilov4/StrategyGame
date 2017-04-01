package gameObjects.weapons;

public class Spell extends Weapon 
{
	private int spellRange;
	
	public Spell()
	{
		spellRange = 3;
	}

	public int getSpellRange() 
	{
		return spellRange;
	}

	public void setSpellRange(int spellRange) 
	{
		this.spellRange = spellRange;;
	}
	
}
