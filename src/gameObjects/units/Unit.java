package gameObjects.units;

import client.Inventory;
import client.Tile;
import factory.AbstractUnitFactory.Alignment;
import factory.AbstractUnitFactory.UnitType;
import factory.AttackBehavior;
import factory.MoveBehavior;
import gameObjects.GameObject;

public abstract class Unit extends GameObject
{
	public enum Player {PlayerOne, Computer};
	
	Player belongsTo;
	private boolean hasMoved;
	private boolean hasAttacked;
	private boolean hasEndedTurn;
	
	protected AttackBehavior attackBehavior;
	protected MoveBehavior   moveBehavior;
	
	UnitType unitType;
	Alignment alignment;
	
	private int level;
	private int baseHealthPoints, currentHealthPoints, bonusHealthPoints, maxHealthPoints;
	private int baseAttackRating, currentAttackRating, bonusAttackRating;
	
	private int baseSTR, currentSTR, bonusSTR;
	private int baseAGI, currentAGI, bonusAGI;
	private int baseINT, currentINT, bonusINT;
	
	protected int[] EXPTable = {-1, 15, 34, 57, 92, 135, 372, 560, 840, 1242, 1789};
	private int currentEXP;
	private int EXPOnDeath;
	
	private Inventory inventory;
	
	public Unit(UnitType unitType, Tile tile, int lv) 
	{
		super(tile);
		setIsSelected(false);
		
		setMoved(false);
		setAttacked(false);
		setEndedTurn(false);
		
		level = lv;
		currentEXP = 0;
		EXPOnDeath = 0;
		
		inventory = new Inventory();
		setX(tile.getX());
		setY(tile.getY());
	}
	
	public abstract AttackBehavior attack();
	
	public abstract MoveBehavior move();
	
	public boolean isDead()
	{
		return (currentHealthPoints <= 0);
	}

	//
	// Getters & Setters
	//
	
	public Player getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Player belongsTo) {
		this.belongsTo = belongsTo;
	}

	public boolean hasEndedTurn() {
		return (hasMoved() || hasAttacked());
	}

	public void setEndedTurn(boolean hasEndedTurn) 
	{
		hasMoved = hasEndedTurn;
		hasAttacked = hasEndedTurn;
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	public void setMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public boolean hasAttacked() {
		return hasAttacked;
	}

	public void setAttacked(boolean hasAttacked) {
		this.hasAttacked = hasAttacked;
	}

	public int getBaseHealthPoints() {
		return baseHealthPoints;
	}

	public void setBaseHealthPoints(int baseHealthPoints) {
		this.baseHealthPoints = baseHealthPoints;
	}

	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public void setMaxHealthPoints(int maxHealthPoints) {
		this.maxHealthPoints = maxHealthPoints;
	}

	public int getBaseAttackRating() {
		return baseAttackRating;
	}

	public void setBaseAttackRating(int baseAttackRating) {
		this.baseAttackRating = baseAttackRating;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCurrentHealthPoints() {
		return currentHealthPoints;
	}

	public void setCurrentHealthPoints(int currentHealthPoints) {
		this.currentHealthPoints = currentHealthPoints;
	}

	public int getCurrentAttackRating() {
		return currentAttackRating;
	}

	public void setCurrentAttackRating(int currentAttackRating) {
		this.currentAttackRating = currentAttackRating;
	}

	public int getBonusHealthPoints() {
		return bonusHealthPoints;
	}

	public void setBonusHealthPoints(int bonusHealthPoints) {
		this.bonusHealthPoints = bonusHealthPoints;
	}

	public int getBonusAttackRating() {
		return bonusAttackRating;
	}

	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	public void setBonusAttackRating(int bonusAttackRating) {
		this.bonusAttackRating = bonusAttackRating;
	}

	public AttackBehavior getAttackBehavior() {
		return attackBehavior;
	}

	public void setAttackBehavior(AttackBehavior attackBehavior) {
		this.attackBehavior = attackBehavior;
	}

	public MoveBehavior getMoveBehavior() {
		return moveBehavior;
	}

	public void setMoveBehavior(MoveBehavior moveBehavior) {
		this.moveBehavior = moveBehavior;
	}

	public int getBaseSTR() {
		return baseSTR;
	}

	public void setBaseSTR(int baseSTR) {
		this.baseSTR = baseSTR;
	}

	public int getCurrentSTR() {
		return currentSTR;
	}

	public void setCurrentSTR(int currentSTR) {
		this.currentSTR = currentSTR;
	}

	public int getBonusSTR() {
		return bonusSTR;
	}

	public void setBonusSTR(int bonusSTR) {
		this.bonusSTR = bonusSTR;
	}

	public int getBaseAGI() {
		return baseAGI;
	}

	public void setBaseAGI(int baseAGI) {
		this.baseAGI = baseAGI;
	}

	public int getCurrentAGI() {
		return currentAGI;
	}

	public void setCurrentAGI(int currentAGI) {
		this.currentAGI = currentAGI;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getBonusAGI() {
		return bonusAGI;
	}

	public void setBonusAGI(int bonusAGI) {
		this.bonusAGI = bonusAGI;
	}

	public int getBaseINT() {
		return baseINT;
	}

	public void setBaseINT(int baseINT) {
		this.baseINT = baseINT;
	}

	public int getCurrentINT() {
		return currentINT;
	}

	public void setCurrentINT(int currentINT) {
		this.currentINT = currentINT;
	}

	public int getBonusINT() {
		return bonusINT;
	}

	public void setBonusINT(int bonusINT) {
		this.bonusINT = bonusINT;
	}

	public int getCurrentEXP() {
		return currentEXP;
	}

	public void setCurrentEXP(int currentEXP) {
		this.currentEXP = currentEXP;
	}

	public int getEXPOnDeath() {
		return EXPOnDeath;
	}

	public void setEXPOnDeath(int eXPOnDeath) {
		EXPOnDeath = eXPOnDeath;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
}
