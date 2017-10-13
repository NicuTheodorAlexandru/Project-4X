package menu;

import org.joml.Vector3f;

import game.Level;
import game.LoadGame;
import game.Nation;
import graphics.HUD;
import gui.guiButton;
import main.Main;

public class MainMenu 
{
	private guiButton startNewGame, exitGame, loadGame;
	
	public void render()
	{
		startNewGame.render();
		exitGame.render();
		loadGame.render();
	}
	
	public void update()
	{
		startNewGame.update();
		exitGame.update();
		loadGame.update();
		
		if(startNewGame.getActivated())
		{
			Level level = new Level(new Nation("Prussia", "Prussian", "Protestant", 
					new Vector3f(0.0f, 0.192f, 0.325f)), 1, 1);
			Main.level = level;
			Main.hud = new HUD();
		}
		else if(exitGame.getActivated())
		{
			Main.running = false;
		}
		else if(loadGame.getActivated())
		{
			LoadGame.loadGame("save");
			
		}
	}
	
	public MainMenu()
	{
		startNewGame = new guiButton(600, 200, "Start new game");
		exitGame = new guiButton(600, 240, "Exit game");
		loadGame = new guiButton(600, 220, "Load game");
	}
}
