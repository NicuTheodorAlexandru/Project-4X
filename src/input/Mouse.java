package input;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import main.Main;
import main.Window;

public class Mouse
{
	private static Vector2d previousPos;
	private static Vector2d currentPos;
	private static Vector2d position;
	private static Vector2f displVec;
	private boolean inWindow = true;
	private static boolean leftMousePressed = false;
	private static boolean rightMousePressed = false;
	private static boolean leftMouseReleased = false;
	private static boolean rightMouseReleased = false;
	private static boolean leftMouse = false;
	private static boolean rightMouse = false;
	private Window window;
	
	public static Vector3f getMouseWorldPosition()
	{
		
		Vector3f pos = new Vector3f();
		
		float x = (float)(2 * position.x / Main.window.getWindowWidth() - 1) * (-1);
		float y = (float)(1 - 2 * position.y / Main.window.getWindowHeight());
		
		pos.x = x;
		pos.y = y;
		
		return pos;
	}
	
	public static boolean rightMouse()
	{
		return rightMouse;
	}
	
	public static boolean leftMouse()
	{
		return leftMouse;
	}
	
	public static boolean isRightButtonReleased()
	{
		return rightMouseReleased;
	}
	
	public static boolean isLeftButtonReleased()
	{
		return leftMouseReleased;
	}
	
	public static boolean isRightButtonPressed()
	{
		return rightMousePressed;
	}
	
	public static boolean isLeftButtonPressed()
	{
		return leftMousePressed;
	}
	
	public static Vector2d getMousePosition()
	{
		return currentPos;
	}
	
	public void input() {
		position.x = currentPos.x;
		position.y = currentPos.y;
        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }
	
	public static Vector2f getDisplayVec()
	{
		return displVec;
	}
	
	public void init()
	{
		 glfwSetCursorPosCallback(window.getWindowID(), (windowHandle, xpos, ypos) -> 
		 {
	         currentPos.x = xpos;
	         currentPos.y = ypos;
	     });
	     glfwSetCursorEnterCallback(window.getWindowID(), (windowHandle, entered) -> 
	     {
	    	 inWindow = entered;
	     });
	     glfwSetMouseButtonCallback(window.getWindowID(), (windowHandle, button, action, mode) -> 
	     {
	       	leftMousePressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
	        rightMousePressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
	        leftMouseReleased = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE;
	        rightMouseReleased = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_RELEASE;
	        if(button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS)
	        {
	        	if(leftMouse == false)
	        	{
	        		leftMousePressed = true;
	        	}
	        	leftMouse = true;
	        }
	        if(button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE)
	        {
	        	if(leftMouse)
	        		leftMouseReleased = true;
	        	leftMouse = false;
	        }
	        if(button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS)
	        {
	        	if(rightMouse == false)
	        	{
	        		rightMousePressed = true;
	        	}
	        	rightMouse = true;
	        }
	        if(button == GLFW_MOUSE_BUTTON_2 && action == GLFW_RELEASE)
	        {
	        	if(rightMouse)
	        		rightMouseReleased = true;
	        	rightMouse = false;
	        }
	     });
	}
	
	public static void update()
	{
		leftMousePressed = false;
		rightMousePressed = false;
		leftMouseReleased = false;
		rightMouseReleased = false;
	}
	
	public Mouse(Window window)
	{
		this.window = window;
		previousPos = new Vector2d(-1, -1);
		currentPos = new Vector2d(0, 0);
		displVec = new Vector2f();
		position = new Vector2d(0, 0);
		init();
	}
}
