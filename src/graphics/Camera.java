package graphics;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import input.Keyboard;
import input.Mouse;
import misc.Settings;

public class Camera
{
	private final Vector3f position;
	private final Vector3f rotation;
	
	private void zoom()
	{
		Vector3f z = new Vector3f(0, 0, 0);
		z.z -= Mouse.getScrollY();
		movePosition(z);
	}
	
	public void update()
	{
		zoom();
		Vector3f cameraInc = new Vector3f(0, 0, 0);
		
        if (Keyboard.getKey(GLFW.GLFW_KEY_A)) 
        {
            cameraInc.x = -Settings.cameraSpeed;
        } else if (Keyboard.getKey(GLFW.GLFW_KEY_D)) 
        {
            cameraInc.x = Settings.cameraSpeed;
        }
        if (Keyboard.getKey(GLFW.GLFW_KEY_S)) {
            cameraInc.y = -Settings.cameraSpeed;
        } else if (Keyboard.getKey(GLFW.GLFW_KEY_W)) 
        {
            cameraInc.y = Settings.cameraSpeed;
        }
        
        movePosition(cameraInc);
        //moveRotation();
	}
	
	public void movePosition(Vector3f movePosition)
	{
		if ( movePosition.z != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * movePosition.z;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * movePosition.z;
        }
        if ( movePosition.x != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * movePosition.x;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * movePosition.x;
        }
        if(position.z >= 0)
        	position.z = -0.5f;
        position.y += movePosition.y;
	}
	
	private float simplifyRotation(float value)
	{
		while(value >= 360.f)
		{
			value -= 360.f;
		}
		while(value <= -360.f)
		{
			value += 360.f;
		}
		
		return value;
	}
	
	public void moveRotation(Vector3f moveRotation)
	{
		rotation.x = simplifyRotation(rotation.x + moveRotation.x);
		rotation.y = simplifyRotation(rotation.y + moveRotation.y);
		rotation.z = simplifyRotation(rotation.z + moveRotation.z);
	}
	
	public void setRotation(Vector3f rotation)
	{
		this.rotation.x = simplifyRotation(rotation.x);
		this.rotation.y = simplifyRotation(rotation.y);
		this.rotation.z = simplifyRotation(rotation.z);
	}
	
	public void setPosition(Vector3f position)
	{
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
	}
	
	public Vector3f getRotation()
	{
		return rotation;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public Camera()
	{
		position = new Vector3f(0, 0, -4.0f);
		rotation = new Vector3f(0, 180, 0);
	}
}
