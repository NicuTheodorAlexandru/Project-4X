package gui;

import org.lwjgl.nanovg.NanoVG;
import input.Mouse;
import main.Main;

public class guiList 
{
	protected float x, y;
	protected float width, height;
	protected float scroll;
	protected float scrollSpeed;
	private float exY;
	private guiButton scrollButton;
	
	private void follow()
	{
		scrollButton.setY(Mouse.getMousePosition().y);
	}
	
	private void scroll()
	{
		if(scrollButton.getActivated())
		{
			follow();
		}
		scroll += scrollButton.getY() - exY;
		exY = (float)scrollButton.getY();
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
		scrollButton = new guiButton(x + width, y, "S" + System.lineSeparator() + "C");
		
		scroll = 0.0f;
		scrollSpeed = 0.1f;
		exY = y;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
