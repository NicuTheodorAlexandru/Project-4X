package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import main.Main;

public class SaveGame
{
	public static void saveGame(String filename)
	{
		try 
		{
			File file = new File("src/saves/" + filename);
			if(file.exists())
			{
				file.delete();
				file.mkdir();
			}
			else
			{
				file.mkdir();
			}
			File ff;
			ff = new File("src/saves/" + filename + "/level.save");
			ff.createNewFile();
			ff = new File("src/saves/" + filename + "/player.save");
			ff.createNewFile();
			ff = new File("src/saves/" + filename + "/calendar.save");
			ff.createNewFile();
			ff = new File("src/saves/" + filename + "/market.save");
			ff.createNewFile();
			//save level
			FileOutputStream f = new FileOutputStream("src/saves/" + filename + "/" + "level.save");
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(Main.level);
			o.flush();
			o.close();
			f.close();
			//save player
			f = new FileOutputStream("src/saves/" + filename + "/" + "player.save");
			o = new ObjectOutputStream(f);
			o.writeObject(Main.level.player);
			o.flush();
			o.close();
			f.close();
			//save calendar
			f = new FileOutputStream("src/saves/" + filename + "/" + "calendar.save");
			o = new ObjectOutputStream(f);
			o.writeObject(Main.level.date);
			o.flush();
			o.close();
			f.close();
			//save market
			f = new FileOutputStream("src/saves/" + filename + "/" + "market.save");
			o = new ObjectOutputStream(f);
			o.writeObject(World.market);
			o.flush();
			o.close();
			f.close();
		} catch (IOException e) 
		{
			System.out.println(e);
		}
	}
}
