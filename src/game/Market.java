package game;

import java.io.Serializable;
import java.util.HashMap;

import buildings.Factory;
import misc.Defines;

public class Market implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 759093621811953565L;
	private HashMap<String, Double> prices;
	private final double priceChangePerUnit;
	
	public void sellResource(Factory factory, String resource, double amount)
	{
		double money = amount * prices.get(resource);
		double price = prices.get(resource) - priceChangePerUnit * amount;
		if(price < 0.01d)
			price = 0.01d;
		prices.put(resource, price);
		factory.changeStockpile(resource, -amount);
		factory.changeMoney(money);
	}
	
	public void buyResource(Factory factory, String resource, double amount)
	{
		double money;
		money = amount * prices.get(resource);
		factory.changeMoney(-money);
		factory.changeStockpile(resource, amount);
		prices.put(resource, prices.get(resource) + priceChangePerUnit * amount);
	}
	
	public void buyResource(Nation nation, String resource, double amount)
	{
		double money;
		if(nation.getMoney() <= 0.0d)
		{
			return;
		}
			
		if(amount + nation.getStockpile(resource) > nation.getStorage(resource))
		{
			amount = nation.getStorage(resource) - nation.getStockpile(resource);
		}
		if(nation.getMoney() < amount * prices.get(resource))
		{
			money = nation.getMoney();
			amount = money / prices.get(resource);
		}
		
		money = amount * prices.get(resource);
		nation.changeMoney(-money);
		nation.changeStockpile(resource, amount);
		prices.put(resource, prices.get(resource) + priceChangePerUnit * amount);
	}
	
	public void sellResource(Nation nation, String resource, double amount)
	{
		double money = 0.0d;
		
		if(nation.getStockpile(resource) < amount)
		{
			amount = nation.getStockpile(resource);
		}
		money = amount * prices.get(resource);
		double price = prices.get(resource) - priceChangePerUnit * amount;
		if(price < 0.01d)
			price = 0.01d;
		prices.put(resource, price);
		nation.changeStockpile(resource, -amount);
		
		nation.changeMoney(money);
	}
	
	public void render()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public Market()
	{
		priceChangePerUnit = 0.01d;
		prices = new HashMap<>();
		
		for(String resource: Defines.resourceTypes)
		{
			prices.put(resource, 1.00d);
		}
	}
}
