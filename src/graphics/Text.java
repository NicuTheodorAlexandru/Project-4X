package graphics;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import main.Main;

public class Text
{
	private String text;
	private NVGColor color;
	private String fontName;
	private float fontSize;
	private float x;
	private float y;
	private float width;
	private float height;
	
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
		color.r(red / 255.0f);
		color.g(green / 255.0f);
		color.b(blue / 255.0f);
		color.a(alpha / 255.0f);
	}
	
	public void render()
	{
		NanoVG.nvgBeginPath(Main.vg);
		NanoVG.nvgFontSize(Main.vg, fontSize);
		NanoVG.nvgFontFace(Main.vg, fontName);
		NanoVG.nvgTextAlign(Main.vg, NanoVG.NVG_ALIGN_LEFT);
		NanoVG.nvgFontBlur(Main.vg, 0);
		NanoVG.nvgFillColor(Main.vg, color);
		NanoVG.nvgText(Main.vg, x, y, text);
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
		color = NVGColor.create();
		setColor(0, 0, 0, 255.0f);
		fontName = "Consolas";
		fontSize = 20.0f;
		getTextSize();
	}
}
