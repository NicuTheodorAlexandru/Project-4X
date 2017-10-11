package gui;

import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import graphics.Text;
import input.Mouse;
import main.Main;

public class guiTooltip
{
	private Text text;
	private boolean active;
	private Vector4f color;
	public float x, y;
	public float width, height;
	
	public void update()
	{
		active = false;
		if(x <= Mouse.getMousePosition().x && x + width >= Mouse.getMousePosition().x)
			if(y <= Mouse.getMousePosition().y && y + height >= Mouse.getMousePosition().y)
				active = true;
	}
	
	public String getText()
	{
		return text.getText();
	}
	
	public void setText(String text)
	{
		this.text.setText(text);
	}
	
	public void render()
	{
		if(!active)
			return;
		NanoVG.nvgBeginPath(Main.vg);
		NanoVG.nvgRect(Main.vg, (float)Mouse.getMousePosition().x, (float)Mouse.getMousePosition().y, text.getWidth(), text.getHeight());
		NVGColor color = NVGColor.create();
		color.r(this.color.x);
		color.g(this.color.y);
		color.b(this.color.z);
		color.a(this.color.w);
		NanoVG.nvgFillColor(Main.vg, color);
		NanoVG.nvgFill(Main.vg);
		
		text.setX((float)Mouse.getMousePosition().x);
		text.setY((float)Mouse.getMousePosition().y);
		text.render();
	}
	
	public guiTooltip(String text)
	{
		active = false;
		this.text = new Text(0.0f, 0.0f, text);
		color = new Vector4f(1.0f, 1.0f, 1.0f, 0.75f);
		x = y = width = height = 0.0f;
	}
}
