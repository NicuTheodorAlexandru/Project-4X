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
