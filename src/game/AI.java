package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import main.Main;
import misc.Defines;

public class AI implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 335284711435725293L;
	private Nation ai;
	
	private List<Tile> provinces;
	
	private void colonizeResource(String resource)
	{
		for(int i = 0; i < Main.level.getWorld().getHeight(); i++)
		{
			for(int j = 0; j < Main.level.getWorld().getWidth(); j++)
			{
				if(Main.level.getWorld().getTile(i, j).getOwner() == null)
				{
					Main.level.getWorld().getTile(i, j).colonize(ai);
				}
			}
		}
	}
	
	private void colonize()
	{
		colonizeResource("Food");
	}
	
	private void develop()
	{
		for(Tile tile: provinces)
		{
			if(tile.getResourceType() == "Food" && tile.getPopulation() - tile.getNumberOfFarms() * 10 > 0)
			{
				World.market.buyResource(ai, "Wood", 0.1d);
				tile.buildFarm();
			}
			if(tile.getResourceType() == "Wood" && tile.getPopulation() - tile.getNumberWoodcutter() * 10 > 0)
			{
				World.market.buyResource(ai, "Wood", 0.1d);
				tile.buildWoodcutter();
			}
			for(String resource: Defines.resourceTypes)
			{
				if(ai.getStorage(resource) == ai.getStockpile(resource))
				{
					switch(resource)
					{
					case "Food": tile.buildFoodStorage(); break;
					case "Wood": tile.buildWoodStorage(); break;
					}
				}
			}
		}
	}
	
	private void sell()
	{
		for(String resource: Defines.resourceTypes)
		{
			World.market.sellResource(ai, resource, ai.getStockpile(resource));
		}
	}
	
	public void update()
	{
		if(ai.getMoney() >= 1.0d)
		{
			colonize();
		}
		develop();
		sell();
	}
	
	public AI(Nation ai, Tile tile)
	{
		provinces = new ArrayList<>();
		this.ai = ai;
		tile.colonize(this.ai);
		provinces.add(tile);
	}
}
