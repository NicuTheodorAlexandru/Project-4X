package buildings;

import java.io.Serializable;

import game.Nation;

public class Storage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7752018496241131802L;
	
	private Nation owner;
	
	public void render()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public Storage(float storageAmount, String resourceType, Nation owner)
	{
		this.owner = owner;
		this.owner.changeStorage(resourceType, storageAmount);
	}
}
