package graphics;

import org.joml.Vector3f;

import game.AABB;

public class Model
{
	private Mesh mesh;
	private final Vector3f position;
	private final Vector3f rotation;
	private float size;
	private Vector3f scale;
	private boolean selected;
	private AABB aabb;
	
	public boolean getCollision(Model other)
	{
		return aabb.collision(other.getCollisionBox());
	}
	
	public AABB getCollisionBox()
	{
		return aabb;
	}
	
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
	
	public float getSize()
	{
		return size;
	}
	
	public void setScale(float value)
	{
		size = value;
	}
	
	public Vector3f getScale()
	{
		return new Vector3f(scale.x * size, scale.y * size, scale.z * size);
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
		aabb.update(position.x, position.y);
	}
	
	public void moveZ(float value)
	{
		movePosition(new Vector3f(0, 0, value));
	}
	
	public void moveY(float value)
	{
		movePosition(new Vector3f(0, value, 0));
	}
	
	public void moveX(float value)
	{
		movePosition(new Vector3f(value, 0, 0));
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
		size = 1.0f;
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = new Vector3f(mesh.getLength());
		aabb = new AABB(position.x, position.z, mesh.getLength().x, mesh.getLength().z);
	}
}
