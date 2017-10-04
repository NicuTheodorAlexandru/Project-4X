package game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import graphics.HUD;
import main.Main;

public class LoadGame
{
	public static void loadGame(String filename)
	{
		try
		{
			filename = "src/saves/" + filename + "/";
			//load level
			Level level = null;
			FileInputStream f = new FileInputStream(filename + "level.save");
			ObjectInputStream o = new ObjectInputStream(f);
			try 
			{
				level = (Level)o.readObject();
			} catch (ClassNotFoundException e) 
			{
				System.err.println(e);
			}
			o.close();
			f.close();
			//load player
			Nation player = null;
			f = new FileInputStream(filename + "player.save");
			o = new ObjectInputStream(f);
			/*try 
			{
				player = (Nation)o.readObject();
			} catch (ClassNotFoundException e) 
			{
				System.err.println(e);
			}*/
			o.close();
			f.close();
			//load calendar
			Calendar date = null;
			f = new FileInputStream(filename + "calendar.save");
			o = new ObjectInputStream(f);
			/*try 
			{
				date = (Calendar)o.readObject();
			} catch (ClassNotFoundException e) 
			{
				System.err.println(e);
			}*/
			o.close();
			f.close();
			//load market
			Market market = null;
			f = new FileInputStream(filename + "market.save");
			o = new ObjectInputStream(f);
			/*try 
			{
				market = (Market)o.readObject();
			} catch (ClassNotFoundException e) 
			{
				System.err.println(e);
			}*/
			o.close();
			f.close();
			//load game
			//Level.date = date;
			//Level.player = player;
			//World.market = market;
			Main.level = level;
			Main.hud = new HUD();
			level.load();
		}
		catch(IOException err)
		{
			System.out.println(err);
		}
	}
}
