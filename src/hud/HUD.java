package hud;

import game.Level;
import game.Nation;
import game.SaveGame;
import graphics.Text;
import gui.guiBuildMenu;
import gui.guiButton;
import gui.guiResourceList;
import gui.guiSprite;
import gui.guiTooltip;
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
	private guiBuildMenu buildMenu;
	private guiButton colonizeButton;
	private guiButton exitGameButton;
	private guiButton resumeGameButton;
	private guiButton saveGameButton;
	private guiButton buildFactoryButton;
	private guiButton openDiplomacy;
	private guiButton recruitArmy;
	private guiTooltip profit;
	private Diplomacy diplomacy;
	private Text provincePopulation;
	private Text provinceResource;
	private Text money;
	private Text manpower;
	private float exMoney;
	private float exexMoney;
	
	public void openDiplomacy(Nation nation)
	{
		diplomacy = new Diplomacy(100, 100, 200, 400, nation);
		interfaceOpen = true;
	}
	
	public void closeDiplomacy()
	{
		diplomacy = null;
		interfaceOpen = false;
	}
	
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
					recruitArmy = new guiButton(0.0f, Main.window.getWindowHeight() - 160.0f, 
							new guiSprite(Assets.imgRecruit));
					if(Level.selectedTile.getOwner() != Main.level.player)
						openDiplomacy = new guiButton(0.0f, Main.window.getWindowHeight() - 120.0f, 
								new guiSprite(Assets.imgDiplomacy));
				}
			}
		}
	}
	
	public void updateOnDay()
	{
		if(profit != null)
		{
			exexMoney = exMoney;
			exMoney = (float)Main.level.getPlayerNation().getMoney();
		}
	}
	
	public void updateOnHour()
	{
		if(profit != null)
		{
			float change = exexMoney - exMoney;
			profit.setText(String.format("%.2f", change));
			profit.width = money.getWidth();
			profit.height = money.getHeight();
		}
	}
	
	public void update()
	{
		buttonClicked = false;
		resourceList.update();
		if(buildMenu != null)
		{
			buildMenu.update();
		}
		if(buildFactoryButton != null)
		{
			if(Level.selectedTile != null && Level.selectedTile.getOwner() != null)
			{
				if(Keyboard.getKeyReleased(Settings.keyExit))
				{
					interfaceOpen = false;
					buildFactoryButton = null;
					buildMenu = null;
				}
				else
				{
					buildFactoryButton.update();
					if(buildFactoryButton.getActivated())
					{
						if(buildMenu != null)
						{
							buildMenu = null;
						}
						else
						{
							buildMenu = new guiBuildMenu(0, Main.window.getWindowHeight() - 300, 200, 200);
						}
					}
				}
			}
			else
			{
				buildFactoryButton = null;
				buildMenu = null;
			}
		}
		if(menuOpen)
		{
			if(Keyboard.getKeyReleased(Settings.keyExit))
			{
				exitGameButton = null;
				resumeGameButton = null;
				saveGameButton = null;
				menuOpen = false;
				if(!pauseState)
					Level.date.unpause();
			}
			else
			{
				saveGameButton.update();
				if(saveGameButton.getActivated())
					SaveGame.saveGame();
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
					saveGameButton = null;
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
				resumeGameButton = new guiButton(500.f, 270.f, "Resume game");
				saveGameButton = new guiButton(500.f, 240.f, "Save game");
				pauseState = Level.date.getPause();
				Level.date.pause();
				menuOpen = true;
			}
		}
		if(money == null)
		{
			String m = "" + String.format("%.2f", Main.level.player.getMoney()) + "$";
			money = new Text(0.0f, 0.0f, m);
		}
		else
		{
			String m = "" + String.format("%.2f", Main.level.player.getMoney()) + "$";
			money.setText(m);
		}
		if(manpower == null)
		{
			String m = "Manpower: " + Integer.toString(Main.level.player.getManpower());
			manpower = new Text(100.0f, 0.0f, m);
		}
		else
		{
			String m = "Manpower: " + Integer.toString(Main.level.player.getManpower());
			manpower.setText(m);
		}
		if(profit == null)
		{
			exMoney = (float)Main.level.getPlayerNation().getMoney();
			exexMoney = (float)Main.level.getPlayerNation().getMoney();
			float change = exexMoney - exMoney;
			profit = new guiTooltip(String.format("%.2f", change));
			profit.x = profit.y = 0.0f;
			profit.width = money.getWidth();
			profit.height = money.getHeight();
		}
		else
		{
			profit.update();
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
				String p = "" + (int)pop;
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
					Level.selectedTile.colonize(Main.level.player);
					colonizeButton = null;
				}
			}
		}
		if(recruitArmy != null)
		{
			if(Level.selectedTile == null || Level.selectedTile.getOwner() != Main.level.player)
				recruitArmy = null;
			else
			{
				recruitArmy.update();
			}
		}
		if(openDiplomacy != null)
		{
			if(Level.selectedTile == null || Level.selectedTile.getOwner() == Main.level.player)
			{
				openDiplomacy = null;
			}
			else
			{
				openDiplomacy.update();
				if(openDiplomacy.getActivated())
				{
					if(diplomacy == null)
					{
						openDiplomacy(Level.selectedTile.getOwner());
					}
					else
					{
						if(diplomacy.getNation().getName() == Level.selectedTile.getOwner().getName())
						{
							closeDiplomacy();
						}
						else
						{
							openDiplomacy(Level.selectedTile.getOwner());
						}
					}
				}
			}
		}
		if(diplomacy != null)
		{
			if(Keyboard.getKeyPressed(Settings.keyExit))
			{
				closeDiplomacy();
			}
			else
			{
				diplomacy.update();
			}
		}
		provinceInfo();
	}
	
	public void render()
	{
		resourceList.render();
		if(buildMenu != null)
			buildMenu.render();
		if(recruitArmy != null)
			recruitArmy.render();
		if(buildFactoryButton != null)
			buildFactoryButton.render();
		if(saveGameButton != null)
			saveGameButton.render();
		if(resumeGameButton != null)
			resumeGameButton.render();
		if(exitGameButton != null)
			exitGameButton.render();
		if(money != null)
			money.render();
		if(manpower != null)
			manpower.render();
		if(provinceResource != null)
			provinceResource.render();
		if(colonizeButton != null)
			colonizeButton.render();
		if(provincePopulation != null)
			provincePopulation.render();
		if(openDiplomacy != null)
			openDiplomacy.render();
		if(profit != null)
			profit.render();
		if(diplomacy != null)
			diplomacy.render();
	}
	
	public HUD()
	{
		resourceList = new guiResourceList(200, 200, 200, 200);
		pauseState = Level.date.getPause();
	}
}
