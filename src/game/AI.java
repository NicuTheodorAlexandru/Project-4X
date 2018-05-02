package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import graphics.Sprite;
import graphics.Texture;
import main.Main;
import misc.Defines;

public class AI implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 335284711435725293L;
	private Nation nation;
	private boolean[][] sentTo;
	private boolean[][] positions;
	private List<Tile> provinces;
	
	private void colonizingAI()
	{
		if(nation.getMoney() < 1.0d)
			return;
		Tile tile = null;
		double score = 0;
		for(int i = 0; i < Main.level.getWorld().getWidth(); i++)
		{
			for(int j = 0; j < Main.level.getWorld().getHeight(); j++)
			{
				double s = 0;
				Tile t = Main.level.getWorld().getTile(i, j);
				if(t.getOwner() != null || !t.isNeighbour(nation))
					continue;
				s += World.market.getPrice(t.getResourceType());
				if(s > score)
				{
					tile = t;
					score = s;
				}
			}
		}
		if(tile != null)
			this.colonizeTile(tile);
	}
	
	private void buildingAI()
	{
		develop();
	}
	
	private void colonizeTile(Tile tile)
	{
		if(tile.getOwner() == null)
		{
			tile.colonize(nation);
			provinces.add(tile);
		}
	}
	
	private void develop()
	{
		for(Tile tile: provinces)
		{
			if(tile.getResourceType() == "Food" && tile.getPopulation() - tile.getNumberOfFarms() * 10 > 0)
			{
				World.market.buyResource(nation, "Wood", 0.1d);
				tile.buildFarm();
			}
			if(tile.getResourceType() == "Wood" && tile.getPopulation() - tile.getNumberWoodcutter() * 10 > 0)
			{
				World.market.buyResource(nation, "Wood", 0.1d);
				tile.buildWoodcutter();
			}
			for(String resource: Defines.resourceTypes)
			{
				if(nation.getStorage(resource) == nation.getStockpile(resource))
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
	
	private void sellAll()
	{
		for(String resource: Defines.resourceTypes)
		{
			World.market.sellResource(nation, resource, nation.getStockpile(resource));
		}
	}

	private void economyAI()
	{
		sellAll();
	}
	
	private void diplomacyAI()
	{
		for(Nation n: nation.getNations())
		{
			if(nation.getRelations(n) < 0 && !nation.getDiplomacy(n).equals("War"))
				nation.declareWar(n);
			else if(nation.getRelations(n) >= 0 && !nation.getDiplomacy(n).equals("Peace"))
				nation.declarePeace(n);
		}
	}
	
	private void recruitingAI()
	{
		if(nation.getManpower() > 0 && nation.getProfit() >= 0.1d)
		{
			Unit u = new Unit(nation, new Sprite(new Texture("/images/sprInfantry.png")), 1.0f, 0.0f, 1.0f, 10, 10, 0.01f, 0.0f);
			u.getSprite().getModel().setPosition(new Vector3f(nation.getCapital().getModel().getPosition()));
			u.getSprite().getModel().setZ(-0.1f);
			nation.recruitArmy(new Army(u));
		}
	}
	
	private Tile bestAttack()
	{
		int score = 0;
		Tile t = null;
		for(int i = 0; i < Main.level.getWorld().getWidth(); i++)
		{
			for(int j = 0; j < Main.level.getWorld().getHeight(); j++)
			{
				Tile tmp = Main.level.getWorld().getTile(i, j);
				int s = 0;
				if(tmp.getOwner() == null || tmp.getOwner() == nation)
					continue;
				if(nation.getDiplomacy(tmp.getOwner()).equals("War"))
				{
					if(positions[i][j] || sentTo[i][j])
						s -= 1000;
					s += (int)tmp.getPopulation();
				}
				else
					continue;
				if(s > score)
				{
					t = tmp;
					score = s;
				}
			}
		}
		return t;
	}
	
	private void updatePositions()
	{
		for(int i = 0; i < Main.level.getWorld().getWidth(); i++)
			for(int j = 0; j < Main.level.getWorld().getHeight(); j++)
			{
				positions[i][j] = false;
				sentTo[i][j] = false;
			}
		for(int i = 0; i < nation.getArmies().size(); i++)
		{
			Army a = nation.getArmies().get(i);
			Vector3f pos = a.getPos();
			int x = (int)(pos.x / Defines.tileWidth);
			int y = (int)(pos.y / Defines.tileHeight);
			if(x < 0)
				x = 0;
			else if(x >= Main.level.getWorld().getWidth())
				x = Main.level.getWorld().getWidth() - 1;
			if(y < 0)
				y = 0;
			else if(y >= Main.level.getWorld().getHeight())
				y = Main.level.getWorld().getHeight() - 1;
			positions[x][y] = true;
			pos = a.getTargetPos();
			if(x < 0)
				x = 0;
			else if(x >= Main.level.getWorld().getWidth())
				x = Main.level.getWorld().getWidth() - 1;
			if(y < 0)
				y = 0;
			else if(y >= Main.level.getWorld().getHeight())
				y = Main.level.getWorld().getHeight() - 1;
			sentTo[x][y] = true;
		}
	}
	
	private void warAI()
	{
		updatePositions();
		for(int i = 0; i < nation.getArmies().size(); i++)
		{
			Tile t = bestAttack();
			if(t != null)
			{
				nation.getArmies().get(i).moveTo(t.getMidPos());
				updatePositions();
			}
		}
	}
	
	public void updateOnHour()
	{
		if(nation.isDead())
			return;
		buildingAI();
		colonizingAI();
		recruitingAI();
		if(nation.atWar())
			warAI();
		economyAI();
		diplomacyAI();
	}
	
	private void startingTile()
	{
		Tile tile = null;
		while(tile == null)
		{
			int i = Main.randomGenerator.nextInt(Main.level.getWorld().getWidth());
			int j = Main.randomGenerator.nextInt(Main.level.getWorld().getHeight());
			tile = Main.level.getWorld().getTile(i, j);
			if(!(tile.getResourceType().equals("Food") && tile.getOwner() == null))
				tile = null;
		}
		int w, h;
		w = Main.level.getWorld().getWidth();
		h = Main.level.getWorld().getHeight();
		provinces = new ArrayList<>();		
		sentTo = new boolean[w][h];
		positions = new boolean[w][h];
		this.colonizeTile(tile);
	}
	
	public AI(Nation ai)
	{
		this.nation = ai;
		Main.level.addNation(ai);
		startingTile();
	}
}
