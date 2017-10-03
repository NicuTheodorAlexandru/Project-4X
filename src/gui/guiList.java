package gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.nanovg.NanoVG;

import graphics.Text;
import input.Keyboard;
import main.Main;
import misc.Settings;

public class guiList 
{
	private List<Text> texts;
	private float x, y;
	private float width, height;
	private float scroll;
	private float scrollSpeed;
	
	private void scroll()
	{
		if(Keyboard.getKey(Settings.keyScrollUp))
			scroll -= scrollSpeed;
		else if(Keyboard.getKey(Settings.keyScrollDown))
			scroll += scrollSpeed;
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
		for(Text text: texts)
		{
			text.changeY(scroll);
			if(text.getY() >= y && text.getY() + text.getHeight() <= y)
				text.render();
			text.changeY(-scroll);
		}
	}
	
	public void delText(Text text)
	{
		if(texts.contains(text))
			texts.remove(text);
		else
		{
			int length = texts.size();
			for(int i = 0; i < length; i++)
			{
				Text t = texts.get(i);
				if(t.getText() == text.getText())
				{
					texts.remove(t);
					i--;
					length--;
				}
			}
		}
	}
	
	public void addText(Text text)
	{
		texts.add(text);
	}
	
	public guiList(float x, float y, float width, float height)
	{
		texts = new ArrayList<>();
		scroll = 0.0f;
		scrollSpeed = 0.1f;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
