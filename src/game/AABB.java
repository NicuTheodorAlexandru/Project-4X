package game;

public class AABB 
{
	private float x, y;
	private float width, height;
	
	public boolean collision(AABB box)
	{
		float x = box.getX();
		float y = box.getY();
		float width = box.getWidth();
		float height = box.getHeight();
		if(this.x >= x && this.x <= x + width)
			if(this.y >= y && this.y <= y + height)
				return true;
		if(x >= this.x && x <= this.x + this.width)
			if(y >= this.y && y <= this.y + this.height)
				return true;
		return false;
	}
	
	public void update(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public AABB(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
