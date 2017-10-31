package game;

import java.io.Serializable;

import org.joml.Vector3f;

import graphics.Sprite;
import main.Main;

public class Unit implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3887377273036147701L;
	private Sprite sprite;
	private Vector3f targetPos;
	private Nation owner;
	private float damage, defense, range, morale, maxMorale;
	private float moraleRegen;
	private float speed;
	private float dailyMaintenance;
	private int maxManpower, manpower;
	
	public void attack(Unit unit)
	{
		
	}
	
	public void defense(float damage)
	{
		damage -= defense;
		if(damage < 0)
			damage = 0;
	}
	
	public Nation getOwner()
	{
		return owner;
	}
	
	public void moveTo(Vector3f pos)
	{
		targetPos = new Vector3f(pos);
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
	
	public void update()
	{
		
	}
	
	public void render()
	{
		sprite.render();
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
		manpower = maxManpower;
		this.maxMorale = maxMorale;
		this.speed = speed;
		moraleRegen = maxMorale / maxManpower;
		targetPos = new Vector3f(sprite.getModel().getPosition());
	}
}
