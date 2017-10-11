package graphics;

import misc.Defines;
import misc.OBJLoader;

public class Sprite
{
	private float base = 1.0f;
	private Model model;
	private Texture texture;
	
	public void cleanup()
	{
		model.getMesh().cleanup();
	}
	
	public void createSprite()
	{
		float[] positions = 
			{
					-base, -base, 0.0f,
		            base, -base, 0.0f, 
		            base, base, 0.0f, 
		            -base, base, 0.0f,
			};
		float[] texCoords = 
			{
					0.0f, 0.0f, 0.0f,
					base, 0.0f, 0.0f,
					base, base, 0.0f,
					0.0f, base, 0.0f,
			};
		float[] normals = 
			{
				0.0f, 0.0f, base,
				0.0f, 0.0f, base,
				0.0f, 0.0f, base,
				0.0f, 0.0f, base,
			};
		int[] indices = 
			{
				0, 1, 2,
				0, 2, 3,
			};
		if(Defines.tileWidth <= 0)
		{
			Defines.tileWidth = base * 2;
			Defines.tileHeight = base * 2;
		}
		model = new Model(new Mesh(positions, texCoords, normals, indices));
		model.getMesh().setTexture(texture);
	}
	
	public Model getModel()
	{
		return model;
	}
	
	public void render()
	{
		model.render();
	}
	
	public Sprite(Texture tex)
	{
		texture = tex;
		model = new Model(OBJLoader.loadMesh("/models/square.obj"));
		model.getMesh().setTexture(texture);
		//createSprite();
	}
}
