package gui;

import java.io.File;
import game.LoadGame;

public class guiLoadGame
{
	private guiButton load;
	private guiSavesList saves;
	
	private void loadAllSaves()
	{
		saves.empty();
		File f = new File("src/saves");
		File[] saves = f.listFiles();
		float x = 400;
		float yy = 200;
		float y = 20;
		
		for(int i = 0; i < saves.length; i++)
		{
			this.saves.addSave(new guiButton(x, yy + y * i, saves[i].getName()));
		}
	}
	
	public void update()
	{
		saves.update();
		load.update();
		if(load.getActivated())
		{
			if(saves.getSelectedSave() != null)
			{
				LoadGame.loadGame(saves.getSelectedSave().getText().getText());
			}
		}
	}
	
	public void render()
	{
		saves.render();
		load.render();
	}
	
	public void refresh()
	{
		loadAllSaves();
	}
	
	public guiLoadGame()
	{
		load = new guiButton(400, 176.5f, "Load");
		saves = new guiSavesList(400, 200, 200, 800);
		refresh();
	}
}
