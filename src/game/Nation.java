package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector4f;

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
	private HashMap<String, Integer> relations;
	private HashMap<String, String> diplomacy;
	private List<Army> armies;
	private List<Tile> tiles;
	private long tag;
	private double money;
	private int population;
	private int manpower;
	
	public void recruitArmy(Army army)
	{
		int amount = army.getMaxManpower();
		for(Tile t: tiles)
		{
			if(t)
		}
	}
	
	public List<Army> getArmies()
	{
		return armies;
	}
	
	public int changeManpower(int amount)
	{
		if(amount >= manpower)
		{
			manpower -= amount;
			return amount;
		}
		else
		{
			amount = manpower;
			manpower = 0;
			return amount;
		}
	}
	
	public int getManpower()
	{
		return manpower;
	}
	
	public void setDiplomacy(Nation nation, String value)
	{
		diplomacy.put(nation.getName(), value);
	}
	
	public void addDiplomacy(Nation nation)
	{
		diplomacy.put(nation.getName(), "Peace");
	}
	
	public void addRelation(Nation nation)
	{
		relations.put(nation.getName(), 0);
	}
	
	public void changeRelations(Nation nation, int value)
	{
		int base = 0;
		base += relations.get(nation.getName());
		relations.put(nation.getName(), base + value);
	}
	
	public String getDiplomacy(Nation nation)
	{
		String res = diplomacy.get(nation.getName());
		if(res == null)
		{
			addDiplomacy(nation);
			res = diplomacy.get(nation.getName());
		}
		return res;
	}
	
	public int getRelations(Nation nation)
	{
		if(relations.get(nation.getName()) == null)
		{
			addRelation(nation);
		}
		return relations.get(nation.getName());
	}
	
	public void changeStorage(String resourceType, double amount)
	{
		storage.put(resourceType, storage.get(resourceType) + amount);
	}
	
	public long getTag()
	{
		return tag;
	}
	
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
	
	public Vector4f getColor()
	{
		return new Vector4f(color, 1.0f);
	}
	
	public Nation(String name, String culture, String religion, Vector3f color)
	{	
		this.color = color;
		this.name = name;
		money = 1.0d;
		
		armies = new ArrayList<>();
		relations = new HashMap<>();
		diplomacy = new HashMap<>();
		storage = new HashMap<>();
		stockpile = new HashMap<>();
		for(String resource: Defines.resourceTypes)
		{
			storage.put(resource, 1.0d);
			stockpile.put(resource, 0.0d);
		}
	}
}
