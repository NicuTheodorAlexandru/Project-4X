package gui;

import org.lwjgl.nanovg.NanoVG;
import input.Mouse;
import main.Main;

public class guiList 
{
	protected float x, y;
	protected float width, height;
	protected float maxHeight;
	protected float curHeight;
	protected float scroll;
	protected float scrollSpeed;
	private float exY;
	private guiButton scrollButton;
	
	public float getCurrentHeight()
	{
		return curHeight;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	private void follow()
	{
		scrollButton.setY(Mouse.getMousePosition().y);
	}
	
	private void scroll()
	{
		if(scrollButton.getActivated())
		{
			follow();
			if(scrollButton.getY() + scrollButton.getHeight() >= y + height)
				scrollButton.setY(y + height - scrollButton.getHeight());
			scroll += (scrollButton.getY() - exY) * (curHeight / height);
			exY = (float)scrollButton.getY();
		}
	}
	
	public void update()
	{
		scroll();
	}
	
	public void render()
	{
		NanoVG.nvgBeginPath(Main.vg);
		NanoVG.nvgRect(Main.vg, x, y, width, height);
		NanoVG.nvgFill(Main.vg);
		
		scrollButton.render();
	}
	
	public guiList(float x, float y, float width, float height)
	{
		scrollButton = new guiButton(x + width, y, 20, this);
		
		scroll = 0.0f;
		scrollSpeed = 0.1f;
		exY = y;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		maxHeight = 0.0f;
	}
}
