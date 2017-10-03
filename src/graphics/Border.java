package graphics;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Border
{	
	Mesh mesh;
	Model model;
	
	public Model getModel()
	{
		return model;
	}
	
	public void render()
	{
		model.render();
	}
	
	public Border(Vector3f pos1, Vector3f pos2, Vector4f color)
	{
		float[] positions = new float[]
				{
					-1.0f, 1.0f, -1.0f,
					-1.0f, 1.0f, 1.0f
				};
		float[] texCoords = new float[]
				{
						
				};
		float[] normals = new float[]
				{
						
				};
		int[] indices = new int[]
				{
						0, 1
				};
		Mesh mesh = new Mesh(positions, texCoords, normals, indices);
		mesh.setColor(color);
		model = new Model(mesh);
	}
}
