package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import buildings.Factory;
import buildings.Storage;
import graphics.Model;
import graphics.Renderer;
import graphics.Sprite;
import graphics.Texture;
import main.Main;
import misc.Defines;

public class Tile implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7602316466663527490L;
	private List<Population> pops;
	private List<Factory> factories;
	private List<Storage> storages;
	private List<Army> armies;
	private transient Sprite sprite;
	private transient Sprite sprController;
	private String resourceType;
	private String terrainType;
	private Nation owner;
	public Tile north, west, south, east;
	private float x, y;
	private float populationGrowth;
	private float resourceOutput;
	private float productionPerPop;
	private float foodPerPop;
	private int numberOfFarms, numberOfWoodcutters;
	private int numberOfFoodStorages, numberOfWoodStorages;
	private Nation controller;
	private Nation sieger;
	private int siegeProgress;
	private int lastSiegeProgress;
	private int maxSiegeProgress;
	private short lastColonized = 0;
	
	public boolean isNeighbour(Nation neighbour)
	{
		if(east != null && east.getOwner() == neighbour)
			return true;
		if(west != null && west.getOwner() == neighbour)
			return true;
		if(north != null && north.getOwner() == neighbour)
			return true;
		if(south != null && south.getOwner() == neighbour)
			return true;
		return false;
	}
	
	public void setNeighbours(Tile north, Tile south, Tile east, Tile west)
	{
		this.east = east;
		this.west = west;
		this.north = north;
		this.south = south;
	}
	
	public Sprite getSprController()
	{
		return sprController;
	}
	
	private void changeController(Nation nation)
	{
		this.controller = nation;
		//sprController.getModel().getMesh().setColor(nation.getColor());
	}
	
	public void addSiegeProgress(Army army)
	{
		sieger = army.getOwner();
		siegeProgress++;
	}
	
	public int getSiegeProgress()
	{
		return siegeProgress;
	}
	
	public void setController(Nation controller)
	{
		this.controller = controller;
	}
	
	public Nation getController()
	{
		return controller;
	}
	
	public List<Army> getArmies()
	{
		return armies;
	}
	
	public void enterArmy(Army army)
	{
		if(!armies.contains(army))
			armies.add(army);
	}
	
	public void leaveArmy(Army army)
	{
		if(armies.contains(army))
			armies.remove(army);
	}
	
	public int getPops(String job)
	{
		int amount = 0;
		
		for(Population p: pops)
		{
			if(p.getJob() != job)
				continue;
			amount += (int)p.getAmount();
		}
		
		return amount;
	}
	
	public int getNumberOfWoodStorage()
	{
		return numberOfWoodStorages;
	}
	
	public int getNumberOfFoodStorages()
	{
		return numberOfFoodStorages;
	}
	
	public int getNumberWoodcutter()
	{
		return numberOfWoodcutters;
	}
	
	public int getNumberOfFarms()
	{
		return numberOfFarms;
	}
	
	private void updateBorder()
	{
		
	}
	
	public List<Population> getPops()
	{
		return pops;
	}
	
	public int getNumberOfFactories()
	{
		return factories.size();
	}
	
	public void buildWoodStorage()
	{
		if(owner.getStockpile("Wood") >= 0.01f)
		{
			owner.changeStockpile("Wood", -0.01f);
		}
		else 
			return;
		storages.add(new Storage(1.0f, "Wood", owner));
		numberOfWoodStorages++;
	}
	
	public void buildFoodStorage()
	{
		if(owner.getStockpile("Wood") >= 0.01f)
		{
			owner.changeStockpile("Wood", -0.01f);
		}
		else 
			return;
		storages.add(new Storage(1.0f, "Food", owner));
		numberOfFoodStorages++;
	}
	
	public void buildWoodcutter()
	{
		if(this.getResourceType() == "Wood" && owner.getStockpile("Wood") >= 0.01f)
		{
			owner.changeStockpile("Wood", -0.01f);
		}
		else
			return;
		numberOfWoodcutters++;
		float money = 0.01f;
		float pay = 0.001f;
		int baseWorkspace = 10;
		float baseProduction = 1.0f;
		float[] resourceOutputAmount = new float[]
				{
						0.01f
				};
		String[] resourceOutput = new String[]
				{
					"Wood"	
				};
		float[] resourceInputAmount = new float[]
				{
			
				};
		String[] resourceInput = new String[]
				{
						
				};
		factories.add(new Factory(this, resourceInput, resourceInputAmount, resourceOutput, 
				resourceOutputAmount, baseProduction, baseWorkspace, pay, money, "Woodcutter"));
	}
	
	public void buildFarm()
	{
		if(this.getResourceType() == "Food" && owner.getStockpile("Wood") >= 0.01f)
		{
			owner.changeStockpile("Wood", -0.01f);
		}
		else
			return;
		numberOfFarms++;
		float money = 0.01f;
		float pay = 0.001f;
		int baseWorkspace = 10;
		float baseProduction = 1.0f;
		float[] resourceOutputAmount = new float[]
				{
					0.01f	
				};
		String[] resourceOutput = new String[]
				{
					"Food"
				};
		float[] resourceInputAmount = new float[]
				{
						
				};
		String[] resourceInput = new String[]
				{
					
				};
		factories.add(new Factory(this, resourceInput, resourceInputAmount, resourceOutput,
				resourceOutputAmount, baseProduction, baseWorkspace, pay, money, "Farmer"));
	}
	
	public void addFactory(Factory factory)
	{
		factories.add(factory);
	}
	
	public void removeFactory(Factory factory)
	{
		factories.remove(factory);
	}
	
	public boolean colonize(Nation nation)
	{
		if(nation.getMoney() >= 1.0d && this.owner == null)
			nation.changeMoney(-1.0d);
		else
			return false;
		this.changeOwner(nation);
		this.changeController(nation);
		nation.addTiles(this);
		changePopulation(new Population(nation.getCulture(), nation.getReligion(), "Unemployed", 2.0d));
		if(this.getOwner() == Main.level.player)
		{
			Level.deselectTile();
			lastColonized = 2;
		}
		if(this.getOwner().getCapital() == null)
			this.getOwner().setCapital(this);
		return true;
	}
	
	public void changePopulationGrowth(float value)
	{
		populationGrowth += value;
	}
	
	private void setResourceOutput()
	{
		int pop = 0;
		for(Population p: pops)
			if(p.getJob() == "Unemployed")
				pop += (int)p.getAmount();
		resourceOutput = pop * productionPerPop;
	}
	
	public float getResourceOutput()
	{
		return resourceOutput;
	}
	
	public void changePopulation(Population pop)
	{
		boolean exists = false;
		for(Population p: pops)
		{
			if(p.getCulture() == pop.getCulture() &&
					p.getReligion() == pop.getReligion() &&
					p.getJob() == pop.getJob())
			{
				exists = true;
				double tmp = p.getAmount();
				p.change(pop.getAmount());
				owner.changePopulation(((int)p.getAmount() - (int)tmp));
				if(pop.getJob() == "Soldier")
					owner.changeManpower(((int)p.getAmount() - (int)tmp));
			}
		}
		if(!exists)
		{
			pops.add(pop);
			owner.changePopulation((int)pop.getAmount());
			if(pop.getJob() == "Soldier")
				owner.changeManpower((int)pop.getAmount());
		}
	}
	
	public void setPopulation(Population pop)
	{
		boolean exists = false;
		for(Population p: pops)
		{
			if(p.getCulture() == pop.getCulture() &&
					p.getReligion() == pop.getReligion() &&
					p.getJob() == pop.getJob())
			{
				exists = true;
				double tmp = p.getAmount();
				p.set(pop.getAmount());
				owner.changePopulation((int)(p.getAmount() - tmp));
			}
		}
		if(!exists)
		{
			owner.changePopulation((int)pop.getAmount());
			pops.add(pop);
		}
	}
	
	private void popGrowth()
	{
		for(int i = 0; i < pops.size(); i++)
		{
			Population pop = pops.get(i);
			double temp = pop.getAmount() * populationGrowth / 720;//720;
			changePopulation(new Population(pop.getCulture(), pop.getReligion(), "Unemployed", temp));
		}
	}
	
	public double getPopulation()
	{
		double p = 0.0f;
		for(Population pop: pops)
			p += (float)pop.getAmount();
		return p;
	}
	
	private void feedPopulation()
	{
		for(Population pop: pops)
		{
			owner.changeStockpile("Food", -foodPerPop * pop.getAmount());
			pop.fed = true;
			if(owner.getStockpile("Food") < 0.0d)
			{
				pop.set(pop.getAmount() / 10);
				pop.fed = false;
			}
		}
	}
	
	public Model getModel()
	{
		return sprite.getModel();
	}
	
	public Nation getOwner()
	{
		return owner;
	}
	
	public void changeOwner(Nation newOwner)
	{
		owner = newOwner;
	}
	
	public String getResourceType()
	{
		return resourceType;
	}
	
	private void outputResource()
	{
		owner.changeStockpile(resourceType, getResourceOutput());
	}
	
	public void initGraphics()
	{
		if(terrainType.compareTo("Plain") == 0)
		{
			sprite = new Sprite(new Texture("/images/sprTerrainPlain.png"));
		}
		else if(terrainType.compareTo("Desert") == 0)
		{
			sprite = new Sprite(new Texture("/images/sprTerrainDesert.png"));
		}
		else if(terrainType.compareTo("Water") == 0)
		{
			sprite = new Sprite(new Texture("/images/sprTerrainWater.png"));
		}
		sprite.getModel().setX(this.x);
		sprite.getModel().setY(this.y);
		sprite.getModel().setZ(0.0f);
		/*sprController = new Sprite(new Texture("/images/sprController.png"));
		sprController.getModel().setX(this.x);
		sprController.getModel().setY(this.y);
		sprController.getModel().setZ(-0.00001f);
		if(controller != owner)
			sprController.getModel().getMesh().setColor(new Vector4f(owner.getColor()));*/
	}
	
	public Tile(float x, float y)
	{
		numberOfFarms = numberOfWoodcutters = 0;
		factories = new ArrayList<>();
		storages = new ArrayList<>();
		armies = new ArrayList<>();
		resourceOutput = 0.0f;
		populationGrowth = 1f;
		productionPerPop = 0.0001f;
		foodPerPop = 0.00005f;
		owner = null;
		pops = new ArrayList<>();
		controller = owner;
		siegeProgress = lastSiegeProgress = 0;
		maxSiegeProgress = 100;
		this.x = x;
		this.y = y;
		terrainType = new String();
		terrainType = Defines.terrainTypes[Main.randomGenerator.nextInt(
				Defines.terrainTypes.length)];
		resourceType = Defines.resourceTypes[Main.randomGenerator.nextInt(
				Defines.resourceTypes.length)];
		north = west = south = east = null;
		initGraphics();
	}
	
	public void render()
	{
		Renderer.provinces.add(this);
	}
	
	public void updateOnHour()
	{
		if(owner != null)
		{
			updateBorder();
		}
	}
	
	private void recruit()
	{
		int amount = this.getPops("Soldier");
		int a = (int)this.getPopulation() / 10;
		int hire = a - amount;
		if(hire > 0)
		{
			for(int i = 0; i < pops.size(); i++)
			{
				Population pop = pops.get(i);
				if(pop.getJob() != "Unemployed")
					continue;
				int aa = hire;
				if(hire > pop.getAmount())
					aa = (int)pop.getAmount();
				this.changePopulation(new Population(pop.getCulture(), pop.getReligion(), "Unemployed", -aa));
				this.changePopulation(new Population(pop.getCulture(), pop.getReligion(), "Soldier", aa));
				hire -= aa;
				if(hire <= 0)
					break;
			}
		}
	}
	
	public void updateOnDay()
	{
		if(owner != null)
		{
			setResourceOutput();
			outputResource();
			
			for(Factory f: factories)
			{
				f.updateOnDay();
			}
			
			feedPopulation();
			popGrowth();
			recruit();
		}
		if(lastSiegeProgress != siegeProgress)
		{
			if(siegeProgress >= maxSiegeProgress)
			{
				this.changeOwner(sieger);
				siegeProgress = 0;
			}
			lastSiegeProgress = siegeProgress;
		}
		else
			lastSiegeProgress = siegeProgress = 0;
	}
	
	public Vector3f getMidPos()
	{
		Vector3f p = new Vector3f();
		p.x = x + Defines.tileWidth / 2;
		p.y = y + Defines.tileHeight / 2;
		return p;
	}
	
	public void update()
	{
		if(lastColonized != 0 && getOwner() == Main.level.player)
		{
			if(getOwner() == Main.level.player && lastColonized == 1)
				Level.selectTile(this);
			lastColonized--;
				
		}
		if(sprite.getModel().getSelected() && Level.selectedTile != this)
			Level.selectTile(this);
	}
}
