package gui;

import java.util.ArrayList;
import java.util.List;
import game.Army;

public class guiArmy 
{
	private List<Army> armies;
	private boolean open;
	
	public void render()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void reopen(List<Army> armies)
	{
		close();
		open(armies);
	}
	
	public void open(List<Army> armies)
	{
		if(open)
			return;
		this.armies.addAll(armies);
		open = true;
	}
	
	public void close()
	{
		if(!open)
			return;
		open = false;
		armies.clear();
	}
	
	public boolean getOpen()
	{
		return open;
	}
	
	public guiArmy()
	{
		armies = new ArrayList<>();
		open = false;
	}
}
