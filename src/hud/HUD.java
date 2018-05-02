package hud;

import org.joml.Vector3f;
import game.Army;
import game.Level;
import game.Nation;
import game.Population;
import game.SaveGame;
import game.Unit;
import graphics.Sprite;
import graphics.Text;
import graphics.Texture;
import gui.guiBuildMenu;
import gui.guiButton;
import gui.guiPieChart;
import gui.guiResourceList;
import gui.guiSprite;
import gui.guiTooltip;
import input.Keyboard;
import input.Mouse;
import main.Main;
import misc.Assets;
import misc.Settings;

public class HUD
{
	public boolean menuOpen = false;
	public boolean interfaceOpen = false;
	public boolean buttonClicked = false;
	private boolean pauseState;
	private guiResourceList resourceList;
	private guiBuildMenu buildMenu;
	private guiButton colonizeButton;
	private guiButton exitGameButton;
	private guiButton resumeGameButton;
	private guiButton saveGameButton;
	private guiButton mainMenuButton;
	private guiButton buildFactoryButton;
	private guiButton openDiplomacy;
	private guiButton recruitArmy;
	private guiPieChart piechart;
	private guiTooltip profit;
	private Diplomacy diplomacy;
	private Text provincePopulation;
	private Text provinceResource;
	private Text money;
	private Text population;
	private Text siegeProgress;
	private Text manpower;
	private guiSprite speed;
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
				provinceResource = new Text(0.0f, Main.window.getWindowHeight() - 50.0f, res);
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
					piechart = new guiPieChart();
					piechart.setX(250.0f);
					piechart.setY(730.0f);
					piechart.setRadius(30.0f);
					String s = "Siege progress: " + Level.selectedTile.getSiegeProgress() + "%";
					siegeProgress = new Text(0.0f, Main.window.getWindowHeight() - 80.0f, s);
					buildFactoryButton = new guiButton(0.0f, Main.window.getWindowHeight() - 110.0f, 
							new guiSprite(Assets.imgBuild));
					if(Level.selectedTile.getOwner() != Main.level.player)
					{
						openDiplomacy = new guiButton(0.0f, Main.window.getWindowHeight() - 150.0f, 
								new guiSprite(Assets.imgDiplomacy));
						openDiplomacy.setClickSound("Diplomacy");
					}
					else
					{
						recruitArmy = new guiButton(0.0f, Main.window.getWindowHeight() - 170.0f, 
								new guiSprite(Assets.imgRecruit));
					}
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
		provinceInfo();
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
				mainMenuButton = null;
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
				mainMenuButton.update();
				if(mainMenuButton.getActivated())
					Main.returnToMainMenu = true;
			}
		}
		else
		{
			if(Keyboard.getKeyReleased(Settings.keyExit) && !interfaceOpen)
			{
				exitGameButton = new guiButton(500.f, 330.f, "Exit game");
				resumeGameButton = new guiButton(500.f, 270.f, "Resume game");
				saveGameButton = new guiButton(500.f, 240.f, "Save game");
				mainMenuButton = new guiButton(500.f, 300.f, "Main menu");
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
		if(population == null)
		{
			String m = "Population: " + Integer.toString(Main.level.player.getPopulation());
			population = new Text(100.0f, 0.0f, m);
		}
		else
		{
			String m = "Population: " + Integer.toString(Main.level.player.getPopulation());
			population.setText(m);
		}
		if(manpower == null)
		{
			String m = "Manpower: " + Integer.toString(Main.level.player.getManpower());
			manpower = new Text(400.0f, 0.0f, m);
		}
		else
		{
			String m = "Manpower: " + Integer.toString(Main.level.player.getManpower());
			manpower.setText(m);
		}
		if(speed == null)
		{
			speed = new guiSprite(Assets.imgSpeed[0]);
			speed.setX(1085.0f);
			speed.setY(2.0f);
		}
		else
		{
			int id = 6 - Level.speed;
			if(Level.date.getPause())
				id = 0;
			speed.setImage(Assets.imgSpeed[id]);
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
			if(Mouse.isLeftButtonReleased())
				interfaceOpen = false;
			if(Level.selectedTile == null)
			{
				provinceResource = null;
				interfaceOpen = false;
			}
			else
			{
				provinceResource.setText(Level.selectedTile.getResourceType());
			}
		}
		if(siegeProgress != null)
		{
			if(Level.selectedTile == null || Level.selectedTile.getOwner() == null)
			{
				siegeProgress = null;
			}
			else
			{
				String s = "Siege progress: " + Level.selectedTile.getSiegeProgress() + "%";
				siegeProgress.setText(s);
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
				String p = "Population " + (int)pop;
				provincePopulation.setText(p);
			}
		}
		if(piechart != null)
		{
			if(Level.selectedTile == null || Level.selectedTile.getOwner() == null)
			{
				piechart = null;
			}
			else
			{
				for(Population pop: Level.selectedTile.getPops())
				{
					piechart.addValue(pop.getJob(), (int)pop.getAmount());
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
				if(recruitArmy.getActivated())
				{
					Unit u = new Unit(Main.level.player, new Sprite(new Texture("/images/sprInfantry.png")), 1.0f, 0.0f, 1.0f, 10, 10, 0.01f, 0.0f);
					u.getSprite().getModel().setPosition(new Vector3f(Level.selectedTile.getModel().getPosition()));
					u.getSprite().getModel().setZ(-0.1f);
					//u.getSprite().getModel().setPosition(new Vector3f(5.0f, 5.0f, -1.0f));
					Main.level.player.recruitArmy(new Army(u));
				}
			}
		}
		if(openDiplomacy != null)
		{
			if(Level.selectedTile == null || Level.selectedTile.getOwner() == Main.level.player ||
					Level.selectedTile.getOwner() == null)
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
					if(Level.selectedTile.colonize(Main.level.player))
						colonizeButton = null;
				}
			}
		}
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
		if(mainMenuButton != null)
			mainMenuButton.render();
		if(population != null)
			population.render();
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
		if(siegeProgress != null)
			if(Level.selectedTile != null)
				if(Level.selectedTile.getSiegeProgress() > 0)
					siegeProgress.render();
		if(speed != null)
			speed.render();
		if(piechart != null)
			piechart.render();
	}
	
	public HUD()
	{
		resourceList = new guiResourceList(200, 200, 200, 200);
		pauseState = Level.date.getPause();
	}
}
