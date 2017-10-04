package game;

import java.io.Serializable;
import java.util.HashMap;

import org.joml.Vector3f;

import misc.Defines;

public class Nation implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4365089421575696350L;
	private final Vector3f color;
	private String name;
	private String culture;
	private String religion;
	private HashMap<String, Double> stockpile;
	private HashMap<String, Double> storage;
	private double money;
	private int population;
	
	public String getReligion()
	{
		return religion;
	}
	
	public String getCulture()
	{
		return culture;
	}
	
	public void changePopulation(int amount)
	{
		population += amount;
	}
	
	public int getPopulation()
	{
		return population;
	}
	
	public void changeMoney(double value)
	{
		money += value;
	}
	
	public double getMoney()
	{
		return money;
	}
	
	public double getStorage(String resource)
	{
		return storage.get(resource);
	}
	
	public double getStockpile(String resource)
	{
		return stockpile.get(resource);
	}
	
	public void changeStockpile(String resource, double amount)
	{
		stockpile.put(resource, stockpile.get(resource) + amount);
		if(stockpile.get(resource) > storage.get(resource))
		{
			World.market.sellResource(this, resource, 
					stockpile.get(resource) - storage.get(resource));
		}
		if(stockpile.get(resource) < 0.0d)
		{
			World.market.buyResource(this, resource, -stockpile.get(resource));
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public Vector3f getColor()
	{
		return color;
	}
	
	public Nation(String name, String culture, String religion, Vector3f color)
	{	
		this.color = color;
		this.name = name;
		money = 1.0d;
		
		storage = new HashMap<>();
		stockpile = new HashMap<>();
		for(String resource: Defines.resourceTypes)
		{
			storage.put(resource, 1.0d);
			stockpile.put(resource, 0.0d);
		}
	}
}
