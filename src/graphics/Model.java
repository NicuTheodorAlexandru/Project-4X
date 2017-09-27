package graphics;

import org.joml.Vector3f;

public class Model
{
	private Mesh mesh;
	private final Vector3f position;
	private final Vector3f rotation;
	private float scale;
	private boolean selected;
	
	public void setMesh(Mesh mesh)
	{
		this.mesh.cleanup();
		this.mesh = mesh;
	}
	
	public void setSelected(boolean value)
	{
		selected = value;
	}
	
	public boolean getSelected()
	{
		return selected;
	}
	
	public Mesh getMesh()
	{
		return mesh;
	}
	
	public void rotateZ(float value)
	{
		rotation.z += value;
	}
	
	public void rotateY(float value)
	{
		rotation.y += value;
	}
	
	public void rotateX(float value)
	{
		rotation.x += value;
	}
	
	public void setRotation(Vector3f rotation)
	{
		this.rotation.x = rotation.x;
		this.rotation.y = rotation.y;
		this.rotation.z = rotation.z;
	}
	
	public void setScale(float value)
	{
		scale = value;
	}
	
	public float getScale()
	{
		return scale;
	}
	
	public Vector3f getRotation()
	{
		return rotation;
	}
	
	public void movePosition(Vector3f pos)
	{
		position.x += pos.x;
		position.y += pos.y;
		position.z += pos.z;
	}
	
	public void moveZ(float value)
	{
		position.z += value;
	}
	
	public void moveY(float value)
	{
		position.y += value;
	}
	
	public void moveX(float value)
	{
		position.x += value;
	}
	
	public void setPosition(Vector3f pos)
	{
		position.x = pos.x;
		position.y = pos.y;
		position.z = pos.z;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public void setZ(float value)
	{
		position.z = value;
	}
	
	public float getZ()
	{
		return position.z;
	}
	
	public void setY(float value)
	{
		position.y = value;
	}
	
	public float getY()
	{
		return position.y;
	}
	
	public void setX(float value)
	{
		position.x = value;
	}
	
	public float getX()
	{
		return position.x;
	}
	
	public void render()
	{
		
	}
	
	public Model(Mesh mesh)
	{
		selected = false;
		this.mesh = mesh;
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = 1.0f;
	}
}
