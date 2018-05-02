package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.joml.Vector2f;
import org.joml.Vector3f;
import graphics.Model;
import graphics.Renderer;
import input.Keyboard;
import input.Mouse;
import main.Main;
import misc.Defines;
import misc.MouseBoxSelection;
import misc.Settings;
import misc.Utils;

public class Level implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1115634716574780107L;
	private World world;
	public List<Nation> nations;
	private List<AI> ais;
	public Nation player;
	public static Calendar date;
	private MouseBoxSelection mouseBoxSelection;
	public static Tile selectedTile;
	public Army selectedArmy;
	public static int FRAMES_PER_HOUR = 1;
	public static int HOURS_PER_FRAME = 1;
	public static int speed = 1;
	private Vector3f mouseWorldPos;
	private int hour, day;
	private boolean init;
	
	public Vector3f getMouseWorldPos()
	{
		return mouseWorldPos;
	}
	
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
		for(Nation n: nations)
			n.render();
		date.render();
	}
	
	public void updateOnDay()
	{
		world.updateOnDay();
		for(Nation n: nations)
			n.updateOnDay();
		Main.hud.updateOnDay();
	}
	
	public void updateOnHour()
	{
		world.updateOnHour();
		for(Nation n: nations)
			if(n.getCapital() != null)
				n.updateOnHour();
		Main.hud.updateOnHour();
		for(int i = 0; i < ais.size(); i++)
			ais.get(i).updateOnHour();
	}
	
	private void updateSpeed()
	{
		if(Keyboard.getKeyPressed(Settings.keyFasterSpeed))
		{
			speed--;
			if(speed < 1)
				speed = 1;
		}
		else if(Keyboard.getKeyPressed(Settings.keySlowerSpeed))
		{
			speed++;
			if(speed > 5)
				speed = 5;
		}
	}
	
	public static void deselectTile()
	{
		if(selectedTile == null)
			return;
		selectedTile.getModel().setSelected(false);
		selectedTile = null;
	}
	
	public static void selectTile(Tile tile)
	{
		if(selectedTile != null)
			deselectTile();
		selectedTile = tile;
	}
	
	public void update()
	{
		if(!init)
		{
			this.init();
		}
		updateSpeed();
		List<Model> models = new ArrayList<>();
		models = Renderer.provinces.stream()
									.map(Tile::getModel)
									.collect(Collectors.toList());
		for(int i = 0; i < nations.size(); i++)
			for(int j = 0; j < nations.get(i).getArmies().size(); j++)
				models.add(nations.get(i).getArmies().get(j).getSprite().getModel());
		if(Mouse.isLeftButtonReleased() && !Main.hud.menuOpen && !Main.hud.buttonClicked)
		{
			mouseBoxSelection.selectModel(models, Main.window, Mouse.getMousePosition(), 
			Main.camera);
		}
		if(selectedArmy != null && selectedArmy.getOwner() == player && Mouse.isRightButtonReleased())
		{
			Vector2f targetPos = MouseBoxSelection.getMouseWorldPos(0, 0, 0, world.getWidth() * Defines.tileWidth, world.getHeight() * Defines.tileHeight
					, -1.0f);
			Vector3f target = new Vector3f(targetPos.x, targetPos.y, -1.0f);
			selectedArmy.moveTo(target);
		}
		if(Mouse.isRightButtonReleased() && selectedTile != null  && !Main.hud.menuOpen && !Main.hud.buttonClicked)
		{
			deselectTile();
		}
		if(selectedTile != null && Keyboard.getKeyReleased(Settings.keyExit))
		{
			deselectTile();
		}
		if(Mouse.isLeftButtonReleased() && selectedArmy != null && !Main.hud.menuOpen && !Main.hud.buttonClicked)
		{
			selectedArmy.getSprite().getModel().setSelected(false);
			selectedArmy = null;
		}
		if(Keyboard.getKeyReleased(Settings.keyExit) && selectedArmy != null)
		{
			selectedArmy.getSprite().getModel().setSelected(false);
			selectedArmy = null;
		}
		//cameraBoxSelection.selectModel(models, Main.camera);
		date.update();
		
		world.update();
		
		/*if(!date.getPause())
		{
			for(int i = 0; i < ais.size(); i++)
				ais.get(i).update();
		}*/
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
	
	private void addNations()
	{
		nations.add(player);
		String[] nations = Utils.getAllFilenames("src/nations");
		for(String nation: nations)
		{
			ais.add(new AI(Utils.getNationFromFile(nation)));
		}
	}
	
	private void init()
	{
		init = true;
		addNations();
	}
	
	public Level(Nation nation, int worldWidth, int worldHeight)
	{
		nations = new ArrayList<>();
		ais = new ArrayList<>();
		mouseBoxSelection = new MouseBoxSelection();
		player = nation;
		world = new World(worldWidth, worldHeight);
		selectedTile = null;
		date = new Calendar(0, 1, 0, 0);
		hour = date.getHour();
		day = date.getDay();
	}
}
