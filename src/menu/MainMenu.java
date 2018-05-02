package menu;

import org.joml.Vector3f;
import game.Level;
import game.Nation;
import gui.guiButton;
import gui.guiLoadGame;
import hud.HUD;
import input.Keyboard;
import main.Main;
import misc.Settings;

public class MainMenu 
{
	private guiButton startNewGame, exitGame, loadGame;
	private guiLoadGame gameLoader;
	
	public void render()
	{
		startNewGame.render();
		exitGame.render();
		loadGame.render();
		if(gameLoader != null)
			gameLoader.render();
	}
	
	public void update()
	{
		startNewGame.update();
		exitGame.update();
		loadGame.update();
		if(gameLoader != null)
			gameLoader.update();
		
		if(startNewGame.getActivated())
		{
			Main.level = new Level(new Nation("Prussia", "Prussian", "Protestant", 
					new Vector3f(0.0f, 0.192f, 0.325f)), 10, 10);
			Main.hud = new HUD();
		}
		else if(exitGame.getActivated())
		{
			Main.running = false;
		}
		else if(loadGame.getActivated() && gameLoader == null)
		{
			gameLoader = new guiLoadGame();
		}
		else if(gameLoader != null)
		{
			if(Keyboard.getKeyPressed(Settings.keyExit))
				gameLoader = null;
		}
	}
	
	public MainMenu()
	{
		startNewGame = new guiButton(600, 200, "Start new game");
		exitGame = new guiButton(600, 240, "Exit game");
		loadGame = new guiButton(600, 220, "Load game");
		gameLoader = null;
	}
}
