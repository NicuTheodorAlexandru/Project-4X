package game;

public class Population
{
	private String culture, job, religion;
	private double amount;
	public boolean fed;
	
	public void set(double value)
	{
		amount = value;
	}
	
	public void div(double value)
	{
		amount /= value;
	}
	
	public void mul(double value)
	{
		amount *= value;
	}
	
	public String getReligion()
	{
		return religion;
	}
	
	public String getJob()
	{
		return job;
	}
	
	public String getCulture()
	{
		return culture;
	}
	
	public double getAmount()
	{
		return amount;
	}
	
	public void change(double value)
	{
		amount += value;
	}
	
	public Population(String culture, String religion, String job, double amount)
	{
		this.culture = culture;
		this.religion = religion;
		this.job = job;
		this.amount = amount;
	}
}
