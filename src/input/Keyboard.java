package input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Keyboard
{
	private long window;
	private static boolean[] keys = new boolean[GLFW_KEY_LAST + 1];
	private static boolean[] keyPressed = new boolean[GLFW_KEY_LAST + 1];
	private static boolean[] keyReleased = new boolean[GLFW_KEY_LAST + 1];
	//private static boolean[] just = new boolean[GLFW_KEY_LAST + 1];
	
	public static boolean getKeyReleased(int key)
	{
		return keyReleased[key];
	}
	
	public static boolean getKeyPressed(int key)
	{
		return keyPressed[key];
	}
	
	public static boolean getKey(int key)
	{
		return keys[key];
	}
	
	private void create()
	{
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> 
		{
			if(action == GLFW_PRESS && key >= 0)
	    	{
	    		keyPressed[key] = true;
	    		keys[key] = true;
	    	}
	    	if(action == GLFW_REPEAT && key >= 0)
	    	{
	    		keys[key] = true;
	    	}
	    	if(action == GLFW_RELEASE && key >= 0)
	    	{
	    		keys[key] = false;
	    		keyReleased[key] = true;
	    	}
	    });
	}
	
	public void update()
	{
		for(int i = 0; i < keyReleased.length; i++)
		{
			keyPressed[i] = false;
			keyReleased[i] = false;
		}
	}

	public Keyboard(long window)
	{
		this.window = window;
		create();
	}
}
