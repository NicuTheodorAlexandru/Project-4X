package graphics;

import java.io.Serializable;

import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import main.Main;

public class Text implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1914974323957980651L;
	private String text;
	private Vector4f color;
	private String fontName;
	private float fontSize;
	private float x;
	private float y;
	private float width;
	private float height;
	
	public float getY()
	{
		return y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public void changeY(float value)
	{
		y += value;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	private void getTextSize()
	{
		NanoVG.nvgFontSize(Main.vg, fontSize);
		NanoVG.nvgFontFace(Main.vg, fontName);
		NanoVG.nvgTextAlign(Main.vg, NanoVG.NVG_ALIGN_LEFT);
		
		float[] bounds = new float[4];
		NanoVG.nvgTextBounds(Main.vg, x, y, text, bounds);
		width = bounds[2] - bounds[0];
		height = bounds[3] - bounds[1];
	}
	
	public void setFontSize(float fontSize)
	{
		this.fontSize = fontSize;
		getTextSize();
	}
	
	public void setFont(String fontName)
	{
		this.fontName = fontName;
		getTextSize();
	}
	
	public void setColor(float red, float green, float blue, float alpha)
	{
		color.x = (red / 255.0f);
		color.y = (green / 255.0f);
		color.z = (blue / 255.0f);
		color.w = (alpha / 255.0f);
	}
	
	public void render()
	{
		NVGColor color = NVGColor.create();
		color.r(this.color.x);
		color.g(this.color.y);
		color.b(this.color.z);
		color.a(this.color.w);
		NanoVG.nvgBeginPath(Main.vg);
		NanoVG.nvgFontSize(Main.vg, fontSize);
		NanoVG.nvgFontFace(Main.vg, fontName);
		NanoVG.nvgTextAlign(Main.vg, NanoVG.NVG_ALIGN_LEFT);
		NanoVG.nvgFontBlur(Main.vg, 0);
		NanoVG.nvgFillColor(Main.vg, color);
		NanoVG.nvgText(Main.vg, x, y + fontSize, text);
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}	
	
	public Text(float x, float y, String text)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		color = new Vector4f();
		setColor(0, 0, 0, 255.0f);
		fontName = "Consolas";
		fontSize = 20.0f;
		getTextSize();
	}
}
