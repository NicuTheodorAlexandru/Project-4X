package gui;

import org.lwjgl.nanovg.NanoVG;

import main.Main;

public class guiTooltip
{
	private String text;
	private boolean active;
	
	public void update()
	{
		
	}
	
	public void render()
	{
		NanoVG.nvgBeginPath(Main.vg);
		
	}
	
	public guiTooltip(String text)
	{
		active = false;
		this.text = text;
	}
}
