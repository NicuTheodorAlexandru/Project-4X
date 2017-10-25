package buildings;

import java.io.Serializable;

import game.Nation;

public class Storage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7752018496241131802L;
	
	private float storageAmount;
	private String resourceType;
	private Nation nation;
	
	public void render()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public Storage(float storageAmount, String resourceType, Nation nation)
	{
		this.storageAmount = storageAmount;
		this.resourceType = resourceType;
		this.nation = nation;
		nation.changeStorage(resourceType, storageAmount);
	}
}
