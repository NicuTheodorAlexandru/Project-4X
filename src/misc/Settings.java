package misc;

import org.lwjgl.glfw.GLFW;

public class Settings
{
	//keys
	public static int keyMoveCameraUp = GLFW.GLFW_KEY_W;
	public static int keyMoveCameraDown = GLFW.GLFW_KEY_S;
	public static int keyMoveCameraLeft = GLFW.GLFW_KEY_A;
	public static int keyMoveCameraRight = GLFW.GLFW_KEY_D;
	public static int keyCameraZoomIn = GLFW.GLFW_KEY_KP_ADD;
	public static int keyCameraZoomOut = GLFW.GLFW_KEY_KP_SUBTRACT;
	public static int keyExit = GLFW.GLFW_KEY_ESCAPE;
	public static int keyFasterSpeed = GLFW.GLFW_KEY_KP_ADD;
	public static int keySlowerSpeed = GLFW.GLFW_KEY_KP_SUBTRACT;
	public static int keyPauseGame = GLFW.GLFW_KEY_SPACE;
	//settings
	public static float mouseSensitivity = 0.2f;
	public static float cameraSpeed = 0.01f;
}
