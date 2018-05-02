package game;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import graphics.Sprite;
import input.Mouse;
import main.Main;
import misc.Defines;

public class Army 
{
	private List<Unit> units;
	private Vector3f pos;
	private Vector3f targetPos;
	private Nation owner;
	private Tile location;
	private float speed;
	private boolean selected;
	private boolean inBattle;

	public void setBattle(boolean value)
	{
		inBattle = value;
	}
	
	public boolean getBattle()
	{
		return inBattle;
	}
	
	private void siege()
	{
		if(location.getOwner() == null || location.getOwner() == owner)
			return;
		if(location.getOwner().getDiplomacy(owner) == "War" && location.getController() == location.getOwner())
		{
			location.addSiegeProgress(this);
		}
	}
	
	public void reinforce(List<Tile> tiles)
	{
		for(Tile t: tiles)
		{
			for(int i = 0; i < t.getPops().size(); i++)
			{
				Population pop = t.getPops().get(i);
				if(pop.getJob() != "Soldier")
					continue;
				int amount = (int)pop.getAmount();
				if(amount > 0)
				{
					if(amount > getMaxManpower() - getManpower())
						amount = getMaxManpower() - getManpower();
					t.changePopulation(new Population(pop.getCulture(), pop.getReligion(), pop.getJob(), -amount));
					amount  -= getUnits().get(0).reinforce(amount);
					t.changePopulation(new Population(pop.getCulture(), pop.getReligion(), pop.getJob(), amount));
				}
				if(getManpower() == getMaxManpower())
					break;
			}
		}
	}
	
	public int getManpower()
	{
		int manpower = 0;
		
		for(Unit u: units)
			manpower += u.getManpower();
		
		return manpower;
	}
	
	public int getMaxManpower()
	{
		int maxManpower = 0;
		
		for(Unit u: units)
			maxManpower += u.getMaxManpower();
		
		return maxManpower;
	}
	
	public Sprite getSprite()
	{
		return units.get(0).getSprite();
	}
	
	public Vector3f getTargetPos()
	{
		return targetPos;
	}
	
	public void moveTo(Vector3f target)
	{
		targetPos = new Vector3f(target);
		targetPos.z = pos.z;
	}
	
	public Nation getOwner()
	{
		return owner;
	}
	
	public List<Unit> getUnits()
	{
		return units;
	}
	
	public boolean shouldDestroy()
	{
		if(units.size() <= 0)
			return true;
		return false;
	}
	
	public void addArmy(Army army)
	{
		if(army.getOwner() == owner)
		{
			for(Unit u: army.getUnits())
				u.getSprite().getModel().setPosition(new Vector3f(pos.x, pos.y, -0.1f));
			units.addAll(army.getUnits());
			army.getUnits().clear();
		}
	}
	
	public Vector3f getPos()
	{
		return pos;
	}
	
	public Army(Unit unit)
	{
		inBattle = false;
		units = new ArrayList<>();
		units.add(unit);
		pos = unit.getSprite().getModel().getPosition();
		targetPos = new Vector3f(pos);
		owner = unit.getOwner();
		selected = false;
		speed = 0.01f;
		int i = (int)(pos.x / Defines.tileWidth);
		int j = (int)(pos.y / Defines.tileHeight);
		if(i < 0)
			i = 0;
		else if(i >= Main.level.getWorld().getWidth())
			i = Main.level.getWorld().getWidth() - 1;
		if(j < 0)
			j = 0;
		else if(j >= Main.level.getWorld().getHeight())
			j = Main.level.getWorld().getHeight() - 1;
		location = Main.level.getWorld().getTile(i, j);
		location.enterArmy(this);
	}
	
	public void update()
	{
		if(units.get(0).getSprite().getModel().getSelected())
		{
			selected = true;
			Main.level.selectedArmy = this;
		}
		else
		{
			if(Mouse.isLeftButtonReleased())
			{
				selected = false;
				Main.level.selectedArmy = null;
			}
		}
	}
	
	public void updateOnDay()
	{
		for(int i = 0; i < units.size(); i++)
		{
			Unit unit = units.get(i);
			if(!inBattle)
				reinforce(Main.level.getWorld().listTiles);
			unit.maintenance();
			if(unit.getManpower() == 0)
			{
				units.remove(unit);
				i--; 
			}
		}
		if(!inBattle)
			siege();
	}
	
	public void updateOnHour()
	{
		if(!inBattle)
		{
			for(Army army: location.getArmies())
			{
				if(owner.getDiplomacy(army.getOwner()).equals("War"))
				{
					Main.level.getWorld().newBattle(new Battle(this, army));
				}
			}
		}
		else
		{
			
		}
		Sprite sprite = units.get(0).getSprite();
		if(selected)
		{
			if(Mouse.isLeftButtonReleased())
			{
				selected = false;
				Main.level.selectedArmy = null;
			}
		}
		if(!inBattle)
		{
			if(sprite.getModel().getPosition().x != targetPos.x)
			{
				float a = targetPos.x - sprite.getModel().getX();
				if(a > speed)
					a = speed;
				else if(a < -speed)
					a = -speed;
				sprite.getModel().moveX(a);
			}
			if(sprite.getModel().getPosition().y != targetPos.y)
			{
				float a = targetPos.y - sprite.getModel().getY();
				if(a > speed)
					a = speed;
				else if(a < -speed)
					a = -speed;
				sprite.getModel().moveY(a);
			}
			location.leaveArmy(this);
			int i = (int)(sprite.getModel().getX() / Defines.tileWidth);
			int j = (int)(sprite.getModel().getY() / Defines.tileHeight);
			if(i < 0)
				i = 0;
			else if(i >= Main.level.getWorld().getWidth())
				i = Main.level.getWorld().getWidth() - 1;
			if(j < 0)
				j = 0;
			else if(j >= Main.level.getWorld().getHeight())
				j = Main.level.getWorld().getHeight() - 1;
			location = Main.level.getWorld().getTile(i, j);
			location.enterArmy(this);
		}
	}
	
	public void render()
	{
		units.get(0).render();
	}
}
