package gui;

import java.util.ArrayList;
import java.util.List;

import game.Level;
import main.Main;

public class guiBuildMenu extends guiList
{
	private List<guiButton> buttons;
	
	private void init()
	{
		buttons.add(new guiButton(x, y + 0 * 25, "Build farm "));
		buttons.add(new guiButton(x, y + 1 * 25, "Build woodcutter "));
		buttons.add(new guiButton(x, y + 2 * 25, "Build food storage "));
		buttons.add(new guiButton(x, y + 3 * 25, "Build wood storage "));
	}
	
	public void update()
	{
		super.update();
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).update();
			if(buttons.get(i).getActivated() && Level.selectedTile != null 
					&& Level.selectedTile.getOwner() == Main.level.player)
			{
				if(i == 0)
				{
					Level.selectedTile.buildFarm();
				}
				else if(i == 1)
				{
					Level.selectedTile.buildWoodcutter();
				}
				else if(i == 2)
				{
					Level.selectedTile.buildFoodStorage();
				}
				else if(i == 3)
				{
					Level.selectedTile.buildWoodStorage();
				}
			}
			if(i == 0 && Level.selectedTile != null)
			{
				buttons.get(i).setText("Build farm " + Level.selectedTile.getNumberOfFarms());
			}
			else if(i == 1 && Level.selectedTile != null)
			{
				buttons.get(i).setText("Build woodcutter " + Level.selectedTile.getNumberWoodcutter());
			}
			else if(i == 2 && Level.selectedTile != null)
			{
				buttons.get(i).setText("Build food storage " + Level.selectedTile.getNumberOfFoodStorages());
			}
			else if(i == 3 && Level.selectedTile != null)
			{
				buttons.get(i).setText("Build wood storage " + Level.selectedTile.getNumberOfWoodStorage());
			}
		}
	}
	
	public void render()
	{
		super.render();
		for(int i = 0; i < buttons.size(); i++)
			buttons.get(i).render();
	}
	
	public guiBuildMenu(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		buttons = new ArrayList<>();
		init();
	}
}
