package main;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;

import misc.Defines;

public class Window
{
	private int width, height;
	private float widthRatio, heightRatio;
	private long windowID;
	private String title;
	private GLFWVidMode vidmode;
	
	public void cleanup()
	{
		GLFW.glfwTerminate();
	}
	
	private void center()
	{
		GLFW.glfwSetWindowPos(windowID, (vidmode.width() - width) / 2, 
				(vidmode.height() - height) / 2);
	}
	
	public void update()
	{
		GLFW.glfwPollEvents();
	}
	
	public void render()
	{
		GLFW.glfwSwapBuffers(windowID);
	}
	
	public void clear()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT 
				| GL11.GL_STENCIL_BUFFER_BIT);
	}
	
	public void setTitle(String title)
	{
		this.title = title;
		GLFW.glfwSetWindowTitle(windowID, title);
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		resize();
	}
	
	public void setWindowHeight(int height)
	{
		this.height = height;
		resize();
	}
	
	public void setWindowWidth(int width)
	{
		this.width = width;
		resize();
	}
	
	public float getHeightRatio()
	{
		return heightRatio;
	}
	
	public float getWidthRatio()
	{
		return widthRatio;
	}
	
	private void resize()
	{
		widthRatio = (float)(width / Defines.widthResolution);
		heightRatio = (float)(height / Defines.heightResolution);
		center();
	}
	
	public int getWindowHeight()
	{
		return height;
	}
	
	public int getWindowWidth()
	{
		return width;
	}
	
	public long getWindowID()
	{
		return windowID;
	}
	
	public Window(int width, int height, String title)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
		//GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		//GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		//GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		//GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		//GLFW.glfwWindowHint(GLFW.GLFW_FLOATING, GL11.GL_TRUE);

		windowID = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwMakeContextCurrent(windowID);
		center();
	}
}
