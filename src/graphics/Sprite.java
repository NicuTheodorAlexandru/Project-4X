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
		float widthRatio = texture.getWidth() / 32.0f;
		float heightRatio = texture.getHeight() / 32.0f;
		float[] vertices = 
			{
					-base * widthRatio, -base * heightRatio, 0f,
		            base * widthRatio, -base * heightRatio, 0f, 
		            base * widthRatio, base * heightRatio, 0f, 
		            -base * widthRatio, base * heightRatio, 0f,
			};
		if(Defines.tileWidth <= 0)
		{
			Defines.tileWidth = base * widthRatio * 2;
			Defines.tileHeight = base * heightRatio * 2;
		}
		model = new Model(OBJLoader.loadSpriteMesh("/models/square.obj", vertices));
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
		model = new Model(OBJLoader.loadMesh("/models/cube.obj"));
		model.getMesh().setTexture(texture);
		//createSprite();
	}
}
