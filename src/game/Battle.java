package game;

import java.util.List;

public class Battle 
{
	private Nation side1;
	private Nation side2;
	private List<Army> armies1;
	private List<Army> armies2;
	private int width, height;
	private Army[][] terrain;
	private boolean ended;
	
	public void addArmy(Army army)
	{
		if(army.getOwner() == side1)
			armies1.add(army);
		else if(army.getOwner() == side2)
			armies2.add(army);
	}
	
	public void update()
	{
		
	}
	
	public boolean getEnded()
	{
		return ended;
	}
	
	public Battle(int width, int height, List<Army> armies1, List<Army> armies2)
	{
		this.width = width;
		this.height = height;
		this.armies1 = armies1;
		this.armies2 = armies2;
		side1 = armies1.get(0).getOwner();
		side2 = armies2.get(0).getOwner();
		ended = false;
	}
}
