package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	private HashMap<Nation, Integer> relations;
	private HashMap<Nation, String> diplomacy;
	private HashMap<Nation, String> peaceTreaty;
	private List<Army> armies;
	private List<Tile> tiles;
	private long tag;
	private double money;
	private double exMoney, exexMoney;
	private int population;
	private int manpower;
	private Tile capital;
	private boolean dead;
	
	public List<Nation> getNations()
	{
		List<Nation> list = new ArrayList<>();
		list.addAll(relations.keySet());
		return list;
	}
	
	public boolean neighboursNation(Nation n)
	{
		for(int i = 0; i < tiles.size(); i++)
			if(tiles.get(i).isNeighbour(n))
				return true;
		return false;
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public double getProfit()
	{
		double a = exMoney - exexMoney;
		return a;
	}
	
	public boolean atWar()
	{
		Iterator<Map.Entry<Nation, String>> iter = diplomacy.entrySet().iterator();
		while(iter.hasNext())
		{
		    Map.Entry<Nation, String> entry = iter.next();
		    if(entry.getValue().equals("War"))
		    {
		    	return true;
		    }
		}
		return false;
	}
	
	public void addTiles(Tile t)
	{
		tiles.add(t);
	}
	
	public void render()
	{
		for(Army a: armies)
			a.render();
	}
	
	private void updateRelations()
	{
		Iterator<Map.Entry<Nation, String>> iter = diplomacy.entrySet().iterator();
		while(iter.hasNext())
		{
		    Map.Entry<Nation, String> entry = iter.next();
		    if(neighboursNation(entry.getKey()))
		    	changeRelations(entry.getKey(), -1);
		    else
		    	changeRelations(entry.getKey(), 1);
		}
	}
	
	public void updateOnDay()
	{
		exexMoney = exMoney;
		exMoney = money;
		updatePeaceTreaties();
		updateRelations();
		for(int i = 0; i < armies.size(); i++)
		{
			Army a = armies.get(i);
			a.updateOnDay();
			if(a.shouldDestroy())
			{
				armies.remove(a);
				i--;
			}
		}
	}
	
	public void updateOnHour()
	{
		for(Army a: armies)
			a.updateOnHour();
		if(capital.getOwner() != this)
		{
			for(int i = 0; i < tiles.size(); i++)
				this.setCapital(tiles.get(i));
			if(capital == null)
				dead = true;
		}
	}
	
	public void recruitArmy(Army army)
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
					if(amount > army.getMaxManpower() - army.getManpower())
						amount = army.getMaxManpower() - army.getManpower();
					t.changePopulation(new Population(pop.getCulture(), pop.getReligion(), pop.getJob(), -amount));
					amount  -= army.getUnits().get(0).reinforce(amount);
					t.changePopulation(new Population(pop.getCulture(), pop.getReligion(), pop.getJob(), amount));
				}
				if(army.getManpower() == army.getMaxManpower())
					break;
			}
		}
		armies.add(army);
	}
	
	public List<Army> getArmies()
	{
		return armies;
	}
	
	public void changeManpower(int amount)
	{
		manpower += amount;
	}
	
	public int getManpower()
	{
		return manpower;
	}
	
	private void updatePeaceTreaties()
	{
		Iterator<Map.Entry<Nation, String>> iter = peaceTreaty.entrySet().iterator();
		while(iter.hasNext())
		{
		    Map.Entry<Nation, String> entry = iter.next();
		    if(entry.getValue().compareTo(Level.date.getDate()) < 0)
		    {
		        iter.remove();
		    }
		}
	}
	
	public void declarePeaceUpon(Nation nation, String date)
	{
		diplomacy.put(nation, "Peace");
		peaceTreaty.put(nation, date);
	}
	
	public void declarePeace(Nation nation)
	{
		if(!diplomacy.get(nation).equals("War"))
			return;
		diplomacy.put(nation, "Peace");
		String date = "";
		date = Integer.toString(1 + Level.date.getYear()) + "-" + Integer.toString(Level.date.getMonth())
			+ "-" + Integer.toString(Level.date.getDay());
		peaceTreaty.put(nation, date);
		nation.declarePeaceUpon(this, date);
	}
	
	public void declareWarUpon(Nation nation)
	{
		diplomacy.put(nation, "War");
	}
	
	public void declareWar(Nation nation)
	{
		if(peaceTreaty.containsKey(nation) || !diplomacy.get(nation).equals("Peace"))
			return;
		diplomacy.put(nation, "War");
		nation.declareWarUpon(this);
	}
	
	public void setDiplomacy(Nation nation, String value)
	{
		if(value == "War")
			declareWar(nation);
		else if(value == "Peace")
			declarePeace(nation);
		else
			diplomacy.put(nation, value);
	}
	
	public void changeRelations(Nation nation, int value)
	{
		int base = 0;
		base += relations.get(nation);
		relations.put(nation, base + value);
	}
	
	public String getDiplomacy(Nation nation)
	{
		String res = diplomacy.get(nation);
		if(res == null)
		{
			discoverNation(nation);
			res = diplomacy.get(nation);
		}
		return res;
	}
	
	public void discoverNationUpon(Nation nation)
	{
		if(diplomacy.containsKey(nation))
			return;
		diplomacy.put(nation, "Peace");
		relations.put(nation, 0);
	}
	
	public void discoverNation(Nation nation)
	{
		if(diplomacy.containsKey(nation))
			return;
		diplomacy.put(nation, "Peace");
		relations.put(nation, 0);
		nation.discoverNationUpon(this);
	}
	
	public int getRelations(Nation nation)
	{
		if(relations.get(nation) == null)
		{
			discoverNation(nation);
		}
		return relations.get(nation);
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
	
	public Tile getCapital()
	{
		return capital;
	}
	
	public void setCapital(Tile tile)
	{
		capital = tile;
	}
	
	public Nation(String name, String culture, String religion, Vector3f color)
	{	
		capital = null;
		dead = false;
		this.color = color;
		this.name = name;
		money = 1.0d;
		exexMoney = exMoney = money;
		tiles = new ArrayList<>();
		armies = new ArrayList<>();
		relations = new HashMap<>();
		diplomacy = new HashMap<>();
		storage = new HashMap<>();
		stockpile = new HashMap<>();
		peaceTreaty = new HashMap<>();
		for(String resource: Defines.resourceTypes)
		{
			storage.put(resource, 1.0d);
			stockpile.put(resource, 0.0d);
		}
	}
}
