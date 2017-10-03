package gui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import graphics.HUD;
import graphics.Text;
import input.Mouse;
import main.Main;

public class guiButton
{
	private float x;
	private float y;
	private float width;
	private float height;
	private guiSprite sprite;
	private Text text;
	private boolean activated;
	private boolean pressed;
	
	public boolean getPressed()
	{
		return pressed;
	}
	
	public void setY(double value)
	{
		y = (float)value;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public void changeY(double value)
	{
		y += value;
	}
	
	private void clicked()
	{
		if(Mouse.isLeftButtonReleased())
		{
			float x = (float)Mouse.getMousePosition().x;
			float y = (float)Mouse.getMousePosition().y;
			if(this.x <= x && this.x + width >= x)
			{
				if(this.y <= y && this.y + height >= y)
				{
					activated = true;
					pressed = false;
					HUD.buttonClicked = true;
				}
			}
		}
		if(Mouse.isLeftButtonPressed())
		{
			float x = (float)Mouse.getMousePosition().x;
			float y = (float)Mouse.getMousePosition().y;
			if(this.x <= x && this.x + width >= x)
			{
				if(this.y <= y && this.y + height >= y)
				{
					pressed = true;
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
		if(text != null)
		{
			NanoVG.nvgBeginPath(Main.vg);
			NVGColor color = NVGColor.create();
			color.a(1.0f);
			color.r(1.0f);
			color.g(1.0f);
			color.b(1.0f);
			NanoVG.nvgRect(Main.vg, x, y, width, height);
			NanoVG.nvgFillColor(Main.vg, color);
			NanoVG.nvgFill(Main.vg);
			text.render();
		}
		else
		{
			sprite.render();
		}
	}
	
	public guiButton(float x, float y, guiSprite sprite)
	{
		pressed = activated = false;
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		width = sprite.getWidth();
		height = sprite.getHeight();
		sprite.setX(x);
		sprite.setY(y);
	}
	
	public guiButton(float x, float y, String text)
	{
		pressed = activated = false;
		this.x = x;
		this.y = y;
		this.text = new Text(x, y, text);
		width = this.text.getWidth();
		height = this.text.getHeight();
	}
}
