package game;

import misc.Defines;

public class World
{
	private Tile[][] tiles;
	private int widthTiles, heightTiles;
	public static Market market;
	
	
	private void generateWorld(int widthTiles, int heightTiles)
	{
		for(int i = 0; i < widthTiles; i++)
		{
			for(int j = 0; j < heightTiles; j++)
			{
				tiles[i][j] = new Tile(i * Defines.tileWidth, j * Defines.tileHeight);
			}
		}
		for(int i = 0; i < widthTiles; i++)
		{
			for(int j = 0; j < heightTiles; j++)
			{
				Tile north, west, south, east;
				if(i == 0)
					west = null;
				else
					west = tiles[i - 1][j];
				if(i == widthTiles - 1)
					east = null;
				else
					east = tiles[i + 1][j];
				if(j == 0)
					south = null;
				else
					south = tiles[i][j - 1];
				if(j == heightTiles - 1)
					north = null;
				else
					north = tiles[i][j + 1];
				tiles[i][j].east = east;
				tiles[i][j].west = west;
				tiles[i][j].north = north;
				tiles[i][j].south = south;
			}
		}
	}
	
	public void updateOnDay()
	{
		for(int i = 0; i < widthTiles; i++)
		{
			for(int j = 0; j < heightTiles; j++)
			{
				tiles[i][j].updateOnDay();
			}
		}
	}
	
	public void updateOnHour()
	{
		for(int i = 0; i < widthTiles; i++)
		{
			for(int j = 0; j < heightTiles; j++)
			{
				tiles[i][j].updateOnHour();
			}
		}
	}
	
	public void update()
	{	
		for(int i = 0; i < widthTiles; i++)
		{
			for(int j = 0; j < heightTiles; j++)
			{
				tiles[i][j].update();
			}
		}
		
		market.update();
	}
	
	public void render()
	{
		for(int i = 0; i < widthTiles; i++)
		{
			for(int j = 0; j < heightTiles; j++)
			{
				tiles[i][j].render();
			}
		}
		market.render();
	}
	
	public World(int widthTiles, int heightTiles)
	{
		this.widthTiles = widthTiles;
		this.heightTiles = heightTiles;
		tiles = new Tile[widthTiles][heightTiles];
		generateWorld(widthTiles, heightTiles);
		market = new Market();
	}
}
