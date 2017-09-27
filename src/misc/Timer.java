package misc;

public class Timer
{
	private double lastLoopTime;
	
	public double getLastLoopTime()
	{
		return lastLoopTime;
	}
	
	public double getTime()
	{
		return System.nanoTime() / 1000000000.0;
	}
	
	public float getElapsedTime()
	{
		double time = getTime();
		float elapsedTime = (float)(time - lastLoopTime);
		lastLoopTime = time;
		
		return elapsedTime;
	}
	
	public void init()
	{
		lastLoopTime = getTime();
	}
}
