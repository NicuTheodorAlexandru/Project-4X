package gui;

import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;
import java.nio.IntBuffer;
import org.lwjgl.nanovg.NVGPaint;
import main.Main;

public class guiSprite
{
	private int imageHandle;
	private int width;
	private int height;
	private float x;
	private float y;
	private float alpha;
	
	public void cleanup()
	{
		//NanoVG.nvgDeleteImage(Main.vg, imageHandle);
	}
	
	public void render()
	{
		//NanoVG.nvgSave(Main.vg);
		NVGPaint paint = NVGPaint.create();
		NanoVG.nvgImagePattern(Main.vg, x, y, width, height, 0.0f, imageHandle, alpha, paint);
		NanoVG.nvgBeginPath(Main.vg);
		NanoVG.nvgRect(Main.vg, x, y, width, height);
		NanoVG.nvgFillPaint(Main.vg, paint);
		NanoVG.nvgFill(Main.vg);
		//NanoVG.nvgRestore(Main.vg);
	}
	
	public void changeY(float value)
	{
		y += value;
	}
	
	public void changeX(float value)
	{
		x += value;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public guiSprite(int image)
	{
		this(0, 0, image);
	}
	
	public guiSprite(float x, float y, int image)
	{
		imageHandle = image;
		this.x = x;
		this.y = y;
		alpha = 1.0f;
		IntBuffer w = MemoryUtil.memAllocInt(1);
		IntBuffer h = MemoryUtil.memAllocInt(1);
		NanoVG.nvgImageSize(Main.vg, imageHandle, w, h);
		width = w.get(0);
		height = h.get(0);
		MemoryUtil.memFree(w);
		MemoryUtil.memFree(h);
	}
}
