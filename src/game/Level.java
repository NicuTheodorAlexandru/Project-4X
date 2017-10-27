package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joml.Vector3f;

import graphics.Model;
import graphics.Renderer;
import hud.HUD;
import input.Keyboard;
import input.Mouse;
import main.Main;
import misc.MouseBoxSelection;
import misc.Settings;

public class Level implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1115634716574780107L;
	private World world;
	private List<Nation> nations;
	private List<AI> ais;
	public Nation player;
	public static Calendar date;
	private MouseBoxSelection mouseBoxSelection;
	public static Tile selectedTile;
	public static int FRAMES_PER_HOUR = 10;
	public static int HOURS_PER_FRAME = 1;
	private int hour, day;
	
	public World getWorld()
	{
		return world;
	}
	
	public void load()
	{
		world.load();
	}
	
	public List<Nation>getNationsList()
	{
		return nations;
	}
	
 	public Nation getPlayerNation()
	{
		return player;
	}
	
	public void addNation(Nation nation)
	{
		nations.add(nation);
	}
	
	public void render()
	{
		world.render();
		date.render();
	}
	
	public void updateOnDay()
	{
		world.updateOnDay();
		Main.hud.updateOnDay();
	}
	
	public void updateOnHour()
	{
		world.updateOnHour();
		Main.hud.updateOnHour();
	}
	
	private void updateSpeed()
	{
		if(Keyboard.getKeyPressed(Settings.keyFasterSpeed))
		{
			FRAMES_PER_HOUR--;
			if(FRAMES_PER_HOUR < 1)
				FRAMES_PER_HOUR = 1;
		}
		else if(Keyboard.getKeyPressed(Settings.keySlowerSpeed))
		{
			FRAMES_PER_HOUR++;
			if(FRAMES_PER_HOUR > 60)
				FRAMES_PER_HOUR = 60;
		}
	}
	
	public void update()
	{
		updateSpeed();
		List<Model> models = Renderer.provinces.stream()
									.map(Tile::getModel)
									.collect(Collectors.toList());
		if(Mouse.isLeftButtonReleased() && !HUD.menuOpen && !HUD.buttonClicked)
		{
			mouseBoxSelection.selectModel(models, Main.window, Mouse.getMousePosition(), 
			Main.camera);
		}
			
		if(Mouse.isRightButtonReleased() && selectedTile != null  && !HUD.menuOpen && !HUD.buttonClicked)
		{
			selectedTile.getModel().setSelected(false);
			selectedTile = null;
		}
		if(selectedTile != null && Keyboard.getKeyReleased(Settings.keyExit))
		{
			selectedTile.getModel().setSelected(false);
			selectedTile = null;
		}
		//cameraBoxSelection.selectModel(models, Main.camera);
		date.update();
		
		world.update();
		
		if(!date.getPause())
		{
			for(int i = 0; i < ais.size(); i++)
				ais.get(i).update();
		}
		if(hour != date.getHour())
		{
			updateOnHour();
			hour = date.getHour();
		}
		if(day != date.getDay())
		{
			updateOnDay();
			day = date.getDay();
		}
	}
	
	public Level(Nation nation, int worldWidth, int worldHeight)
	{
		nations = new ArrayList<>();
		ais = new ArrayList<>();
		mouseBoxSelection = new MouseBoxSelection();
		player = nation;
		nations.add(player);
		world = new World(worldWidth, worldHeight);
		selectedTile = null;
		date = new Calendar(0, 1, 0, 0);
		hour = date.getHour();
		day = date.getDay();
		ais.add(new AI(new Nation("Russia", "Russian", "Orthodox", new Vector3f(0.206f, 0.4f, 0.206f)), world.getTile(0, 0)));
	}
}
