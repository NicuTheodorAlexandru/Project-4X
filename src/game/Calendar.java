package game;

import graphics.HUD;
import graphics.Text;
import input.Keyboard;
import main.Main;
import misc.Settings;

public class Calendar
{
	private boolean pause;
	private String[] months;
	private int hour;
	private int day;
	private int month;
	private int year;
	private Text date;
	private String time;
	private long lastFrame;
	
	public boolean getPause()
	{
		return pause;
	}
	
	public void unpause()
	{
		pause = false;
	}
	
	public void pause()
	{
		pause = true;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public int getHour()
	{
		return hour;
	}
	
	private void updateYear()
	{
		if(month >= 12)
		{
			month = 0;
			year++;
		}
		time += "-" + months[month] + "-" + year;
	}
	
	private void updateMonth()
	{
		int limit = -1;
		//January, March, May, July, August, October, December
		if(month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 
				|| month == 11)
			limit = 31;
		//February
		if(month == 1)
		{
			if(year % 4 == 0)
				limit = 29;
			else
				limit = 28;
		}
		//April, June, September, November
		if(month == 3 || month == 5 || month == 8 || month == 10)
			limit = 30;
		
		if(day > limit)
		{
			day = 1;
			month++;
		}
		if(day < 10)
			time += "0";
		time += day;
	}
	
	private void updateDay()
	{
		if(hour >= 25)
		{
			hour = 0;
			day++;
		}
		if(hour < 10)
			time += "0";
		time += hour + ":00" + "  ";
	}
	
	private void updateDate()
	{
		updateDay();
		updateMonth();
		updateYear();
	}
	
	public void update()
	{
		if(Keyboard.getKeyReleased(Settings.keyPauseGame))
			if(!HUD.menuOpen)
				pause = !pause;
		if(pause)
		{
			lastFrame++;
			return;
		}
		if(lastFrame + Level.FRAMES_PER_HOUR > Main.frame)
			return;
		lastFrame = Main.frame;
		hour += Level.HOURS_PER_FRAME;
		
		time = "";
		updateDate();
	}
	
	public void render()
	{
		date.setText(time);
		date.render();
	}
	
	public Calendar(int hour, int day, int month, int year)
	{
		pause = true;
		lastFrame = Main.frame;
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.year = year;
		months = new String[]
			{
				"January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December"
			};
		time = "";
		updateDate();
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.year = year;
		date = new Text(Main.window.getWindowWidth() - 250.0f, 0.0f, time);
	}
}
