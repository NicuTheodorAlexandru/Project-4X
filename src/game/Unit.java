package game;

import java.io.Serializable;

import graphics.Renderer;
import graphics.Sprite;

public class Unit implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3887377273036147701L;
	private Sprite sprite;
	private Nation owner;
	private float damage, defense, range, morale, maxMorale;
	private int reinforceRate;
	private float moraleRegen;
	private float speed;
	private float dailyMaintenance;
	private int maxManpower, manpower;
	
	public int getMaxManpower()
	{
		return maxManpower;
	}
	
	public float getRange()
	{
		return range;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public void morale()
	{
		if(morale < maxMorale)
		{
			float amount = moraleRegen;
			if(amount > maxMorale - morale)
				amount = maxMorale - morale;
			morale += amount;
		}
	}
	
	public void counterattack(Unit unit)
	{
		float damage = this.damage * manpower / maxManpower;
		unit.defense(damage);
	}
	
	public void attack(Unit unit)
	{
		float damage = this.damage * manpower / maxManpower;
		unit.counterattack(this);
		unit.defense(damage);
	}
	
	public void defense(float damage)
	{
		damage -= defense;
		if(damage < 0)
			damage = 0;
		morale -= damage;
		manpower -= damage;
	}
	
	public Nation getOwner()
	{
		return owner;
	}

	public Sprite getSprite()
	{
		return sprite;
	}
	
	public float getMorale()
	{
		return morale;
	}
	
	public int getManpower()
	{
		return manpower;
	}
	
	public void maintenance()
	{
		float money = manpower * dailyMaintenance;
		owner.changeMoney(money);
	}
	
	public int reinforce(int amount)
	{
		if(manpower < maxManpower)
		{
			if(amount > reinforceRate)
				amount = reinforceRate;
			if(amount > maxManpower - manpower)
				amount = maxManpower - manpower;
			if(amount > owner.getManpower())
				amount = owner.getManpower();
			float money = amount * dailyMaintenance;
			owner.changeMoney(money);
			manpower += amount;
		}
		return amount;
	}
	
	public void render()
	{
		Renderer.models.add(sprite.getModel());
	}
	
	public Unit(Nation owner, Sprite sprite, float damage, float defense, float range, int maxManpower, float maxMorale, float speed, 
			float dailyMaintenance)
	{
		this.owner = owner;
		this.sprite = sprite;
		this.damage = damage;
		this.defense = defense;
		this.range = range;
		this.maxManpower = maxManpower;
		manpower = 0;
		this.maxMorale = maxMorale;
		this.speed = speed;
		moraleRegen = maxMorale / maxManpower;
		reinforceRate = 1;
		this.sprite.getModel().setZ(-0.1f);
	}
}
