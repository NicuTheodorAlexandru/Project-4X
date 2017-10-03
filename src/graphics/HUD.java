package graphics;

import game.Level;
import gui.guiButton;
import gui.guiResourceList;
import gui.guiSprite;
import input.Keyboard;
import main.Main;
import misc.Assets;
import misc.Settings;

public class HUD
{
	public static boolean menuOpen = false;
	public static boolean interfaceOpen = false;
	public static boolean buttonClicked = false;
	private boolean pauseState;
	private guiResourceList resourceList;
	private guiButton colonizeButton;
	private guiButton exitGameButton;
	private guiButton resumeGameButton;
	private guiButton buildFactoryButton;
	private Text provincePopulation;
	private Text numberOfFactories;
	private Text provinceResource;
	private Text money;
	private Text resources;
	
	private void provinceInfo()
	{
		if(Level.selectedTile != null)
		{
			if(!interfaceOpen)
			{
				interfaceOpen = true;
				String res = Level.selectedTile.getResourceType();
				provinceResource = new Text(0.0f, Main.window.getWindowHeight() - 45.0f, res);
				if(Level.selectedTile.getOwner() == null)
				{
					if(colonizeButton == null)
					{
						colonizeButton = new guiButton(0.0f, Main.window.getWindowHeight() - 20.0f, "Colonize");
					}
				}
				else
				{
					interfaceOpen = true;
					int pop = (int)Level.selectedTile.getPopulation();
					String p = "" + pop;
					provincePopulation = new Text(0.0f, Main.window.getWindowHeight() - 20.0f, p);
					buildFactoryButton = new guiButton(0.0f, Main.window.getWindowHeight() - 80.0f, 
							new guiSprite(Assets.imgBuild));
					numberOfFactories = new Text(0.0f, Main.window.getWindowHeight() - 60.0f, "");
				}
			}
		}
	}
	
	public void update()
	{
		buttonClicked = false;
		resourceList.update();
		if(numberOfFactories != null)
		{
			if(Level.selectedTile != null && Level.selectedTile.getOwner() != null)
			{
				if(Keyboard.getKeyReleased(Settings.keyExit))
				{
					interfaceOpen = false;
					numberOfFactories = null;
				}
				else
				{
					numberOfFactories.setText(String.valueOf(Level.selectedTile.getNumberOfFactories()));
				}
			}
			else
			{
				numberOfFactories = null;
			}
		}
		if(buildFactoryButton != null)
		{
			if(Level.selectedTile != null && Level.selectedTile.getOwner() != null)
			{
				if(Keyboard.getKeyReleased(Settings.keyExit))
				{
					interfaceOpen = false;
					buildFactoryButton = null;
				}
				else
				{
					buildFactoryButton.update();
					if(buildFactoryButton.getActivated())
					{
						Level.selectedTile.buildFarm();
					}
				}
			}
			else
			{
				buildFactoryButton = null;
			}
		}
		if(menuOpen)
		{
			if(Keyboard.getKeyReleased(Settings.keyExit))
			{
				exitGameButton = null;
				resumeGameButton = null;
				menuOpen = false;
				if(!pauseState)
					Level.date.unpause();
			}
			else
			{
				exitGameButton.update();
				if(exitGameButton.getActivated())
				{
					Main.running = false;
					return;
				}
				resumeGameButton.update();
				if(resumeGameButton.getActivated())
				{
					exitGameButton = null;
					resumeGameButton = null;
					pauseState = Level.date.getPause();
					Level.date.unpause();
					menuOpen = false;
				}
			}
		}
		else
		{
			if(Keyboard.getKeyReleased(Settings.keyExit) && !interfaceOpen)
			{
				exitGameButton = new guiButton(500.f, 300.f, "Exit game");
				resumeGameButton = new guiButton(500.f, 260.f, "Resume game");
				pauseState = Level.date.getPause();
				Level.date.pause();
				menuOpen = true;
			}
		}
		if(resources == null)
		{
			String tmp  = "";
			tmp += "Population: " + String.valueOf(Level.player.getPopulation() + " ");
			tmp += "Food: " + String.format("%.3f", Level.player.getStockpile("Food")) + "t ";
			tmp += "Wood: " + String.format("%.3f", Level.player.getStockpile("Wood")) + "t ";
			resources = new Text(100.0f, 0.0f, tmp);
		}
		else
		{
			String tmp  = "";
			tmp += "Population: " + String.valueOf(Level.player.getPopulation()) + " ";
			tmp += "Food: " + String.format("%.3f", Level.player.getStockpile("Food")) + "t ";
			tmp += "Wood: " + String.format("%.3f", Level.player.getStockpile("Wood")) + "t ";
			resources.setText(tmp);
		}
		if(money == null)
		{
			String m = "" + String.format("%.2f", Level.player.getMoney()) + "$";
			money = new Text(0.0f, 0.0f, m);
		}
		else
		{
			String m = "" + String.format("%.2f", Level.player.getMoney()) + "$";
			money.setText(m);
		}
		if(provinceResource != null)
		{
			if(Level.selectedTile == null || Level.selectedTile.getOwner() == null)
			{
				provinceResource = null;
				interfaceOpen = false;
			}
			else
			{
				provinceResource.setText(Level.selectedTile.getResourceType());
			}
		}
		if(provincePopulation != null)
		{
			if(Level.selectedTile == null || Level.selectedTile.getOwner() == null)
			{
				provincePopulation = null;
				interfaceOpen = false;
			}
			else
			{
				float pop = (int)Level.selectedTile.getPopulation();
				String p = "" + pop;
				provincePopulation.setText(p);
			}
		}
		if(colonizeButton != null)
		{
			if(Level.selectedTile == null || Level.selectedTile.getOwner() != null ||
					Keyboard.getKey(Settings.keyExit))
			{
				colonizeButton = null;
				interfaceOpen = false;
			}
			else
			{
				colonizeButton.update();
				if(colonizeButton.getActivated())
				{
					Level.selectedTile.colonize(Level.player);
					colonizeButton = null;
				}
			}
		}
		provinceInfo();
	}
	
	public void render()
	{
		resourceList.render();
		if(numberOfFactories != null)
			numberOfFactories.render();
		if(buildFactoryButton != null)
			buildFactoryButton.render();
		if(resumeGameButton != null)
			resumeGameButton.render();
		if(exitGameButton != null)
			exitGameButton.render();
		if(resources != null)
			resources.render();
		if(money != null)
			money.render();
		if(provinceResource != null)
			provinceResource.render();
		if(colonizeButton != null)
			colonizeButton.render();
		if(provincePopulation != null)
			provincePopulation.render();
	}
	
	public HUD()
	{
		resourceList = new guiResourceList(200, 200, 200, 200);
	}
}
