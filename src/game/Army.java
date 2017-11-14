package game;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import graphics.Sprite;
import input.Mouse;
import main.Main;

public class Army 
{
	private List<Unit> units;
	private Vector3f pos;
	private Vector3f targetPos;
	private Nation owner;
	private float speed;
	private boolean selected;
	
	public void reinforce(List<Tile> tiles)
	{
		for(int j = 0; j < tiles.size() && getManpower() < getMaxManpower() ; j++)
		{
			if(tiles.get(j).getOwner() != owner)
				continue;
			Population pop;
			for(int i = 0; i < tiles.get(j).getPops().size() && getManpower() < getMaxManpower(); i++)
			{
				pop = tiles.get(j).getPops().get(i);
				if(pop.getJob() != "Soldier")
					continue;
				int amount = (int)pop.getAmount();
				pop.change(-amount);
				for(int k = 0; k < units.size() && amount > 0; k++)
					if(units.get(k).getManpower() < units.get(k).getMaxManpower())
					{
						int a = units.get(k).reinforce(amount);
						amount -= a;
						
					}
				pop.change(amount);
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
	
	public void moveTo(Vector3f target)
	{
		targetPos = new Vector3f(target);
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
	
	public Army(Unit unit)
	{
		units = new ArrayList<>();
		units.add(unit);
		pos = unit.getSprite().getModel().getPosition();
		targetPos = new Vector3f(pos);
		owner = unit.getOwner();
		selected = false;
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
		for(Unit unit: units)
		{
			reinforce(Main.level.getWorld().listTiles);
			unit.maintenance();
		}
	}
	
	public void updateOnHour()
	{
		Sprite sprite = units.get(0).getSprite();
		if(selected)
		{
			if(Mouse.isLeftButtonReleased())
			{
				selected = false;
				Main.level.selectedArmy = null;
			}
		}
		if(sprite.getModel().getPosition().x != targetPos.x)
		{
			float a = targetPos.x - sprite.getModel().getX();
			if(a < 0)
			{
				a *= -1;
				if(a < speed)
					sprite.getModel().moveX(-a);
				else
					sprite.getModel().moveX(-speed);
			}
			else
			{
				if(a < speed)
					sprite.getModel().moveX(a);
				else
					sprite.getModel().moveX(speed);
			}
		}
		if(sprite.getModel().getPosition().y != targetPos.y)
		{
			float a = targetPos.y - sprite.getModel().getY();
			if(a < 0)
			{
				a *= -1;
				if(a < speed)
					sprite.getModel().moveY(-a);
				else
					sprite.getModel().moveY(-speed);
			}
			else
			{
				if(a < speed)
					sprite.getModel().moveY(a);
				else
					sprite.getModel().moveY(speed);
			}
		}
	}
	
	public void render()
	{
		units.get(0).render();
	}
}