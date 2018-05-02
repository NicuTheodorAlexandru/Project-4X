package game;

import java.util.ArrayList;
import java.util.List;

public class Battle 
{
	private Nation side1;
	private Nation side2;
	private List<Army> armies1;
	private List<Army> armies2;
	private boolean ended;
	
	public void update()
	{
		
	}
	
	public void updateOnHour()
	{
		for(int i = 0; i < armies1.size(); i++)
		{
			
		}
	}
	
	public void updateOnDay()
	{
		
	}
	
	public boolean getEnded()
	{
		return ended;
	}
	
	public void addArmy(Army army)
	{
		if(army.getOwner().getDiplomacy(side1).equals("War"))
		{
			if(!army.getBattle())
			{
				army.setBattle(true);
				armies2.add(army);
			}
		}
		else if(army.getOwner().getDiplomacy(side2).equals("War"))
		{
			if(!army.getBattle())
			{
				army.setBattle(true);
				armies1.add(army);
			}
		}
	}
	
	public Battle(Army army1, Army army2)
	{
		armies1 = new ArrayList<>();
		armies2 = new ArrayList<>();
		side1 = army1.getOwner();
		side2 = army2.getOwner();
		ended = false;
	}
}
