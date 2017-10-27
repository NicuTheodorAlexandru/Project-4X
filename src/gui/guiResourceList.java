package gui;

import java.util.ArrayList;
import java.util.List;
import game.World;
import graphics.Text;
import hud.HUD;
import input.Keyboard;
import main.Main;
import misc.Defines;
import misc.Settings;
import misc.Utils;

public class guiResourceList extends guiList
{
	private List<guiButton> buttons;
	private List<Text> texts;
	private boolean open;
	
	public boolean getOpen()
	{
		return open;
	}
	
	private void init()
	{
		open = false;
		for(int i = 0; i < Defines.resourceTypes.length; i++)
		{
			float a = x;
			float b = y + 40 * i;
			
			texts.add(new Text(a, b, Defines.resourceTypes[i] + ": " + String.format("%.3f", Main.level.player.getStockpile(Defines.resourceTypes[i]))));
			buttons.add(new guiButton(a + 130, b, new guiSprite(Utils.getNanoVGImage("/images/sprPlus.png", 16 * 1024))));
			buttons.add(new guiButton(a + 130, b + 20, new guiSprite(Utils.getNanoVGImage("/images/sprMinus.png", 16 * 1024))));
		}
		for(int i = 0; i < texts.size(); i++)
			maxHeight = Math.max(maxHeight, texts.get(i).getY() - y + texts.get(i).getHeight());
		for(int i = 0; i < buttons.size(); i++)
			maxHeight = (float)Math.max(maxHeight, buttons.get(i).getY() - y + buttons.get(i).getHeight());
	}
	
	public void render()
	{
		if(!open)
			return;
		super.render();
		for(int i = 0, j = 0; i < Defines.resourceTypes.length; i++, j += 2)
		{
			if(texts.get(i).getY() <= y + width)
			{
				texts.get(i).render();
				buttons.get(j).render();
				buttons.get(j + 1).render();
			}
		}
	}
	
	private void activate()
	{
		if(Keyboard.getKeyReleased(Settings.keyOpenResourceList) && HUD.interfaceOpen == false && HUD.menuOpen == false)
		{
			open = true;
			HUD.interfaceOpen = true;
		}
		else if((Keyboard.getKeyReleased(Settings.keyOpenResourceList) || Keyboard.getKeyReleased(Settings.keyExit)) && open)
		{
			open = false;
			HUD.interfaceOpen = false;
		}
	}
	
	public void update()
	{
		activate();
		if(!open)
			return;
		float s = scroll;
		float amount = 0.001f;
		super.update();
		s = scroll - s;
		for(int i = 0, j = 0; i < Defines.resourceTypes.length; i++, j += 2)
		{
			texts.get(i).setText(Defines.resourceTypes[i] + ": " + String.format("%.3f", Main.level.player.getStockpile(Defines.resourceTypes[i])));
			texts.get(i).changeY(s);
			buttons.get(j).changeY(s);
			if(buttons.get(j).getY() >= y && buttons.get(j).getY() <= y + width)
			{
				buttons.get(j).update();
				if(buttons.get(j).getPressed())
					World.market.buyResource(Main.level.player, Defines.resourceTypes[i], amount);
			}
			buttons.get(j + 1).changeY(s);
			if(buttons.get(j + 1).getY() >= y && buttons.get(j + 1).getY() <=  y + width)
			{
				buttons.get(j + 1).update();
				if(buttons.get(j + 1).getPressed())
					World.market.sellResource(Main.level.player, Defines.resourceTypes[i], amount);
			}
		}
	}
	
	public guiResourceList(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		buttons = new ArrayList<>();
		texts = new ArrayList<>();
		init();
	}
}
