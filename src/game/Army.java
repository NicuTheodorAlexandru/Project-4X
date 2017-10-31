package game;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import graphics.Sprite;
import input.Keyboard;
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
				u.getSprite().getModel().setPosition(new Vector3f(pos));
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
		Sprite sprite = units.get(0).getSprite();
		selected = sprite.getModel().getSelected();
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
