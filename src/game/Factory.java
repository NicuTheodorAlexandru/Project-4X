package game;

import java.util.HashMap;

public class Factory
{
	private HashMap<String, Double> stockpile;
	private Tile tile;
	private float baseProduction;
	private int baseWorkspace;
	private float pay;
	private int workers;
	private String job;
	private String[] resourceOutput;
	private String[] resourceInput;
	private float[] resourceOutputAmount;
	private float[] resourceInputAmount;
	private double money;
	private boolean closed;
	private double profit = 0.0d;
	
	public double getProfit()
	{
		return profit;
	}
	
	private void fireWorkers()
	{
		int amount = (int)(money / pay);
		if(amount > workers)
			amount = workers;
		for(Population pop: tile.getPops())
		{
			if(amount <= 0)
				break;
			if(pop.getJob() != job)
				continue;
			if(amount > pop.getAmount())
			{
				workers -= (int)pop.getAmount();
				amount -= (int)pop.getAmount();
				tile.changePopulation(new Population(pop.getCulture(), pop.getReligion(), "Unemployed", 
						(int)pop.getAmount()));
				pop.set(pop.getAmount() - (int)pop.getAmount());
			}
			else
			{
				pop.set(pop.getAmount() - amount);
				tile.changePopulation(new Population(pop.getCulture(), pop.getReligion(), "Unemployed",
						amount));
				workers -= amount;
				amount = 0;
			}
		}
	}
	
	private void hireWorkers()
	{
		int amount = (int)(money / pay);
		if(amount > baseWorkspace - workers)
			amount = baseWorkspace - workers;
		for(int i = 0; i < tile.getPops().size(); i++)
		{
			Population p = tile.getPops().get(i);
			if(amount <= 0)
				break;
			if(p.getJob() == "Unemployed")
			{
				if(amount <= p.getAmount())
				{
					tile.changePopulation(new Population(p.getCulture(), p.getReligion(), job,
							amount));
					tile.changePopulation(new Population(p.getCulture(), p.getReligion(), "Unemployed", 
							-amount));
					workers += amount;
					amount = 0;
				}
				else
				{
					tile.changePopulation(new Population(p.getCulture(), p.getReligion(), job, 
							(int)p.getAmount()));
					amount -= (int)p.getAmount();
					workers += (int)p.getAmount();
					tile.changePopulation(new Population(p.getCulture(), p.getReligion(), "Unemployed",
							-(int)p.getAmount()));
				}
			}
		}
	}
	
	public void changeMoney(double value)
	{
		money += value;
	}
	
	public double getMoney()
	{
		return money;
	}
	
	public void changeStockpile(String resource, double amount)
	{
		if(stockpile.get(resource) == null)
			stockpile.put(resource, 0.0d);
		stockpile.put(resource, stockpile.get(resource) + amount);
	}
	
	public double getStockpile(String resource)
	{
		if(stockpile.get(resource) == null)
			stockpile.put(resource, 0.0d);
		return stockpile.get(resource);
	}
	
	private void closeFactory()
	{
		closed = true;
	}
	
	private void payWorkers()
	{
		changeMoney(-workers * pay);
	}
	
	private void outputResources()
	{
		for(int i = 0; i < resourceOutputAmount.length; i++)
		{
			float amount = resourceOutputAmount[i] * workers * baseProduction;
			changeStockpile(resourceOutput[i], amount);
			World.market.sellResource(this, resourceOutput[i], amount);
		}
	}
	
	private void inputResources()
	{
		for(int i = 0; i < resourceInputAmount.length; i++)
		{
			float amount = resourceInputAmount[i] * workers;
			World.market.buyResource(this, resourceInput[i], amount);
		}
	}
	
	public void updateOnDay()
	{
		if(closed)
			return;
		if(money < 0 && workers < 0)
			closeFactory();
		profit = money;
		inputResources();
		outputResources();
		payWorkers();
		profit = money - profit;
		if(money > 0)
			hireWorkers();
		else
			fireWorkers();
		if(money > 0.0d)
		{
			tile.getOwner().changeMoney(money);
			money = 0.0d;
		}
	}
	
	public void update()
	{
		
	}
	
	public void render()
	{
		
	}
	
	public Factory(Tile tile, String[] resourceInput, float[] resourceInputAmount, 
			String[] resourceOutput, float[] resourceOutputAmount, float baseProduction, 
			int baseWorkspace, float pay, float money, String job)
	{
		this.tile = tile;
		this.resourceInput = resourceInput;
		this.resourceInputAmount = resourceInputAmount;
		this.resourceOutput = resourceOutput;
		this.resourceOutputAmount = resourceOutputAmount;
		this.baseProduction = baseProduction;
		this.baseWorkspace = baseWorkspace;
		this.pay = pay;
		this.money = money;
		this.job = job;
		closed = false;
		
		stockpile = new HashMap<>();
	}
}
