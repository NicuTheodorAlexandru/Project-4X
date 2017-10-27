package gui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import graphics.Text;
import hud.HUD;
import input.Mouse;
import main.Main;

public class guiButton
{
	private float x;
	private float y;
	private float width;
	private float height;
	private float maxHeight, curHeight;
	private guiSprite sprite;
	private guiList list;
	private Text text;
	private boolean activated;
	private boolean pressed;
	
	public void setX(double x)
	{
		this.x = (float)x;
	}
	
	public Text getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text.setText(text);
	}
	
	public float getHeight()
	{
		return height;
	}
	
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
		else if(sprite != null)
		{
			sprite.render();
		}
		else if(list != null)
		{
			curHeight = list.getHeight();
			maxHeight = list.getCurrentHeight();
			float ratio = maxHeight / curHeight;
			height = curHeight / ratio;
			if(height > curHeight)
				height = curHeight;
			NanoVG.nvgBeginPath(Main.vg);
			NVGColor color = NVGColor.create();
			color.a(1.0f);
			color.b(0.0f);
			color.r(0.0f);
			color.g(0.0f);
			NanoVG.nvgRect(Main.vg, x, y, width, height);
			NanoVG.nvgFillColor(Main.vg, color);
			NanoVG.nvgFill(Main.vg);
		}
	}
	
	public guiButton(float x, float y, float width, guiList list)
	{
		pressed = activated = false;
		this.x = x;
		this.y = y;
		this.width = width;
		height = 0;
		this.curHeight = list.getHeight();
		this.maxHeight = list.getCurrentHeight();
		this.list = list;
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
