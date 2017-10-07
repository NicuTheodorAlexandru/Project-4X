package gui;

import java.util.ArrayList;
import java.util.List;

import game.Level;

public class guiBuildMenu extends guiList
{
	private List<guiButton> buttons;
	
	private void init()
	{
		buttons.add(new guiButton(x, y + 0 * 25, "Build farm "));
		buttons.add(new guiButton(x, y + 1 * 25, "Build woodcutter "));
	}
	
	public void update()
	{
		super.update();
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).update();
			if(buttons.get(i).getActivated())
			{
				if(i == 0 && Level.selectedTile != null)
				{
					Level.selectedTile.buildFarm();
				}
				else if(i == 1 && Level.selectedTile != null)
				{
					Level.selectedTile.buildWoodcutter();
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
