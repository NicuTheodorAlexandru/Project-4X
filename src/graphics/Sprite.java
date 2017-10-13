package graphics;

public class Sprite
{
	private float base = 1.0f;
	private float baseWidth = 32.0f;
	private float baseHeight = 32.0f;
	private Model model;
	private Texture texture;
	
	public void cleanup()
	{
		model.getMesh().cleanup();
	}
	
	public void createSprite()
	{
		float w = texture.getWidth();
		float h = texture.getHeight();
		w /= baseWidth;
		h /= baseHeight;
		
		float[] positions = 
			{
					-w, -h, 0.0f,
		            w, -h, 0.0f, 
		            w, h, 0.0f, 
		            -w, h, 0.0f,
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
		model = new Model(new Mesh(positions, texCoords, normals, indices));
		model.rotateX(180);
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
		createSprite();
	}
}
