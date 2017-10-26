package gui;

import java.util.ArrayList;
import java.util.List;

public class guiSavesList extends guiList
{
	private List<guiButton> saves;
	private guiButton selectedSave;
	
	public void empty()
	{
		saves.clear();
		maxHeight = 0;
	}
	
	public guiButton getSelectedSave()
	{
		return selectedSave;
	}
	
	public void addSave(guiButton save)
	{
		//save.setY(save.getY() + y);
		//save.setX(save.getX() + x);
		saves.add(save);
		maxHeight = (float)Math.max(maxHeight, save.getY() - y + save.getHeight());
	}
	
	public void render()
	{
		super.render();
		for(int i = 0; i < saves.size(); i++)
		{
			if(saves.get(i).getY() <= y + width)
			{
				saves.get(i).render();
			}
		}
	}
	
	public void update()
	{
		super.update();
		for(int i = 0; i < saves.size(); i++)
		{
			saves.get(i).update();
			if(saves.get(i).getActivated())
			{
				selectedSave = saves.get(i);
			}
		}
	}
	
	public guiSavesList(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		saves = new ArrayList<>();
		selectedSave = null;
	}

}
