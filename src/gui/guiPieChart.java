package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import main.Main;
import misc.Assets;

public class guiPieChart
{
	private float x, y;
	private float radius;
	private HashMap<String, Float> values;
	private HashMap<String, Float> percentages;
	
	public void render()
	{
		calculatePercentages();
		float angle = 0.0f;
		Iterator<Map.Entry<String, Float>> iter = percentages.entrySet().iterator();
		while(iter.hasNext())
		{
		    Map.Entry<String, Float> entry = iter.next();
		    NVGColor color = NVGColor.create();
		    if(entry.getKey().equals("Unemployed"))
		    	color = Assets.cUnemployed;
		    else if(entry.getKey().equals("Soldier"))
		    	color = Assets.cSoldier;
		    else if(entry.getKey().equals("Farmer"))
		    	color = Assets.cFarmer;
		    else if(entry.getKey().equals("Woodcutter"))
		    	color = Assets.cWoodcutter;
		    float x1 = x + (float)Math.sin(angle) * radius;
		    float y1 = y - (float)Math.cos(angle) * radius;
		    float a0 = (float)Math.toRadians(angle);
		    angle += 360 * entry.getValue();
		    float x2 = x + (float)Math.sin(angle) * radius;
		    float y2 = y - (float)Math.cos(angle) * radius;
		    float a1 = (float)Math.toRadians(angle);
		    float r = (float)Math.toRadians(radius);
		    float t = 10.0f;
		    NanoVG.nvgBeginPath(Main.vg);
		    NanoVG.nvgMoveTo(Main.vg, x, y);
		    //NanoVG.nvgLineTo(Main.vg, x1, y1);
		    //NanoVG.nvgArcTo(Main.vg, xx, yy, xx + 5.0f, yy + 5.0f, 1.0f);
		    NanoVG.nvgArc(Main.vg, x, y, radius, a0, a1, NanoVG.NVG_CW);
		    //NanoVG.nvgArc(Main.vg, x, y, 10.0f, a0, a1, NanoVG.NVG_CCW);
		    //NanoVG.nvgMoveTo(Main.vg, x2, y2);
		    //System.out.println(x2 + ";" + y2);
		    //NanoVG.nvgLineTo(Main.vg, x, y);
		    NanoVG.nvgFillColor(Main.vg, color);
		    NanoVG.nvgFill(Main.vg);
		}
	}
	
	private void calculatePercentages()
	{
		float total = 0;
		Iterator<Map.Entry<String, Float>> iter = values.entrySet().iterator();
		while(iter.hasNext())
		{
		    Map.Entry<String, Float> entry = iter.next();
		    total += entry.getValue();
		}
		iter = values.entrySet().iterator();
		while(iter.hasNext())
		{
		    Map.Entry<String, Float> entry = iter.next();
		    float p = entry.getValue() / total;
		    percentages.put(entry.getKey(), p);
		}
	}
	
	public void resetValues()
	{
		values.clear();
	}
	
	public void addValue(String name, float value)
	{
		if(values.containsKey(name))
			values.put(name, values.get(name) + value);
		else
			setValue(name, value);
	}
	
	public void setValue(String name, float value)
	{
		values.put(name, value);
	}
	
	public void setRadius(float radius)
	{
		this.radius = radius;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getX()
	{
		return x;
	}
	
	public guiPieChart()
	{
		values = new HashMap<>();
		percentages = new HashMap<>();
		x = y = 0.0f;
	}
}
