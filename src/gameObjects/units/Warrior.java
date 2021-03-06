package gameObjects.units;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.Tile;
import factory.AbstractUnitFactory.Alignment;
import factory.AbstractUnitFactory.UnitType;
import factory.AttackBehavior;
import factory.MoveBehavior;
import gameObjects.weapons.MeleeWeapon;
import screens.CharacterSelectionScreen;

public class Warrior extends Unit
{
	private MeleeWeapon weapon;
	private UnitType unitType;

	private int[] baseHealthArray = {-1, 200, 240, 280, 320, 360, 400, 440, 480, 520, 560};
	private int[] baseAttackArray = {-1, 80, 15, 20, 25, 30, 35, 40, 45, 50, 55};
	private int[] baseSTRArray    = {-1, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32};
	private int[] baseAGIArray    = {-1, 2, 4, 6, 8, 10, 12, 13, 14, 15, 16};
	private int[] baseINTArray    = {-1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

	public Warrior(UnitType unitType, Tile tile, int lv, MeleeWeapon wep) 
	{
		super(unitType, tile, lv);
		setBelongsTo(Player.PlayerOne);
		
		setUnitType(unitType);
		setAlignment(Alignment.Ally);
		weapon = wep;
		
		attackBehavior = new AttackBehavior(weapon.getWeaponRange(), baseAttackArray[getLevel()], weapon.getAttackRating());
		moveBehavior = new MoveBehavior(2);
		
		// Health:
		setBaseHealthPoints(baseHealthArray[getLevel()]);
		setCurrentHealthPoints(getBaseHealthPoints());
		setMaxHealthPoints(getCurrentHealthPoints());
		setBonusHealthPoints(0);
		
		// Attack:
		setBaseAttackRating(baseAttackArray[getLevel()]);
		setCurrentAttackRating(getBaseAttackRating());
		//bonus for weapon
		
		// Stats:
		setBaseSTR(baseSTRArray[getLevel()]);
		setCurrentSTR(getBaseSTR());
		setBonusSTR(0);
		
		setBaseAGI(baseAGIArray[getLevel()]);
		setCurrentAGI(getBaseAGI());
		setBonusAGI(0);
		
		setBaseINT(baseINTArray[getLevel()]);
		setCurrentINT(getBaseINT());
		setBonusINT(0);
	}

	public AttackBehavior attack() 
	{
		return attackBehavior;
	}

	
	public MoveBehavior move() 
	{
		return moveBehavior;
	}

	public void tick() {
		if (this.getCurrentEXP() >= this.EXPTable[this.getLevel()])
		{
			System.out.println("Warrior has leveled up!");
			this.setCurrentEXP(this.getCurrentEXP() - this.EXPTable[this.getLevel()]);
			this.setLevel(this.getLevel() + 1);
			
			this.setBaseAttackRating(baseAttackArray[getLevel()]);
			this.setCurrentAttackRating(this.getBaseAttackRating());
			
			this.setBaseHealthPoints((baseHealthArray[this.getLevel()]));
			this.setMaxHealthPoints(baseHealthArray[this.getLevel()]);
			this.setCurrentHealthPoints(this.getMaxHealthPoints());
			
			this.setBaseSTR(baseSTRArray[this.getLevel()]);
			this.setCurrentSTR(this.getBaseSTR());
			
			this.setBaseAGI(baseAGIArray[this.getLevel()]);
			this.setCurrentAGI(this.getBaseAGI());
			
			this.setBaseINT(baseINTArray[this.getLevel()]);
			this.setCurrentINT(this.getBaseINT());
		}
		
	}

	public void render(Graphics g) {
		try {
			BufferedImage warrior = ImageIO.read(new File("res/units/warrior.png"));
			
			g.drawImage(warrior, this.getX(), this.getY() - 32, null);
			
			g.setColor(Color.BLACK);
			g.fillRect(this.getX(), this.getY() - 40, 34, 10);
			Color healthColor = new Color(17, 142, 15);
			g.setColor(healthColor);
			g.fillRect(this.getX() + 1, this.getY() - 39, (32 * this.getCurrentHealthPoints()) / this.getMaxHealthPoints(), 8);
			
			Font levelFont = new Font("arial", 0, 10);
			g.setFont(levelFont);
			g.setColor(Color.yellow);
			g.drawString("" + this.getLevel(), this.getX(), this.getY() - 32);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public Rectangle getBounds() 
	{
		return new Rectangle(getX(), getY(), 1, 1);
	}

}
