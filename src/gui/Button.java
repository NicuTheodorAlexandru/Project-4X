package gui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import graphics.HUD;
import graphics.Text;
import input.Mouse;
import main.Main;

public class Button
{
	private float x;
	private float y;
	private float width;
	private float height;
	private Text text;
	private boolean activated;
	
	private void clicked()
	{
		if(Mouse.isLeftButtonReleased())
		{
			float x = (float)Mouse.getMousePosition().x;
			float y = (float)Mouse.getMousePosition().y;
			if(this.x <= x && this.x + width >= x)
			{
				if(this.y >= y && this.y - height <= y)
				{
					activated = true;
					HUD.buttonClicked = true;
				}
			}
		}
	}
	
	public boolean getActivated()
	{
		return activated;
	}
	
	public void update()
	{
		activated = false;
		clicked();
	}
	
	public void render()
	{
		text.render();
		NanoVG.nvgBeginPath(Main.vg);
		NVGColor color = NVGColor.create();
		color.a(1.0f);
		color.r(0.0f);
		color.g(0.0f);
		color.b(0.0f);
		NanoVG.nvgRect(Main.vg, x, y, width, height);
	}
	
	
	public Button(float x, float y, String text)
	{
		activated = false;
		this.x = x;
		this.y = y;
		this.text = new Text(x, y, text);
		width = this.text.getWidth();
		height = this.text.getHeight();
	}
}
