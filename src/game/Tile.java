package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector4f;

import graphics.Border;
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
	private transient Sprite sprite;
	private String resourceType;
	private String terrainType;
	private Nation owner;
	private transient Border borderNorth, borderWest, borderSouth, borderEast;
	public Tile north, west, south, east;
	private float x, y;
	private float populationGrowth;
	private float resourceOutput;
	private float productionPerPop;
	private float foodPerPop;
	
	private void updateBorder()
	{
		if(north != null && north.getOwner() != owner && borderNorth == null)
		{
			Vector3f pos1 = new Vector3f(x, y, 0);
			Vector3f pos2 = new Vector3f(x + 2.0f, y + 2.0f, 0);
			borderNorth = new Border(pos1, pos2, new Vector4f(owner.getColor(), 1.0f));
		}
	}
	
	public List<Population> getPops()
	{
		return pops;
	}
	
	public int getNumberOfFactories()
	{
		return factories.size();
	}
	
	public void buildWoodcutter()
	{
		
	}
	
	public void buildFarm()
	{
		if(owner.getStockpile("Wood") >= 0.01f && owner.getMoney() >= 0.01f)
		{
			owner.changeStockpile("Wood", -0.01f);
			owner.changeMoney(-0.01f);
		}
		else
			return;
		float money = 0.01f;
		float pay = 0.00001f;
		int baseWorkspace = 1000;
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
	
	public void colonize(Nation nation)
	{
		if(nation.getMoney() >= 1.0d)
			nation.changeMoney(-1.0d);
		else
			return;
		this.changeOwner(nation);
		
		changePopulation(new Population(nation.getCulture(), nation.getReligion(), "Unemployed", 2.0d));
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
			}
		}
		if(!exists)
		{
			pops.add(pop);
			owner.changePopulation((int)pop.getAmount());
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
		for(Population pop: pops)
		{
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
		if(terrainType == "Plain")
		{
			sprite = new Sprite(new Texture("/images/sprTerrainPlain.png"));
		}
		else if(terrainType == "Desert")
		{
			sprite = new Sprite(new Texture("/images/sprTerrainDesert.png"));
		}
		else if(terrainType == "Water")
		{
			sprite = new Sprite(new Texture("/images/sprTerrainWater.png"));
		}
		
		sprite.getModel().setX(this.x);
		sprite.getModel().setY(this.y);
	}
	
	public Tile(float x, float y)
	{
		factories = new ArrayList<>();
		resourceOutput = 0.0f;
		populationGrowth = 1f;
		productionPerPop = 0.0001f;
		foodPerPop = 0.00005f;
		owner = null;
		pops = new ArrayList<>();
		
		this.x = x;
		this.y = y;
		terrainType = new String();
		terrainType = Defines.terrainTypes[Main.randomGenerator.nextInt(
				Defines.terrainTypes.length)];
		resourceType = Defines.resourceTypes[Main.randomGenerator.nextInt(
				Defines.resourceTypes.length)];
		
		initGraphics();
	}
	
	public void render()
	{
		Renderer.provinces.add(this);
		if(borderNorth != null)
			Renderer.models.add(borderNorth.getModel());
	}
	
	public void updateOnHour()
	{
		if(owner != null)
		{
			updateBorder();
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
		}
	}
	
	public void update()
	{
		if(sprite.getModel().getSelected() && Level.selectedTile != this)
			Level.selectedTile = this;
	}
}
