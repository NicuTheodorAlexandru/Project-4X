package graphics;

import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import game.Tile;
import main.Main;
import misc.Utils;

public class Renderer
{
	public static final float FOV = (float)(Math.toRadians(60.0f));
	public static final float Z_NEAR = 0.01f;
	public static final float Z_FAR = 1000.0f;
	public static Transformation transformation;
	private ShaderProgram shaderProgram;
	private ShaderProgram shaderHUD;
	private ShaderProgram shaderProvince;
	public static List<Tile> provinces;
	public static List<Model> models;
	public static List<Model> huds;
	public static Matrix4f projectionMatrix;
	public static Matrix4f viewMatrix;
	
	private void renderProvinces()
	{
		shaderProvince.bind();
		
		shaderProvince.setUniform("projectionMatrix", projectionMatrix);
		
		shaderProvince.setUniform("textureSampler", 0);
		
		for(Tile tile: provinces)
		{
			Model model = tile.getModel();
			Mesh mesh = model.getMesh();
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(model, viewMatrix);
			shaderProvince.setUniform("modelViewMatrix", modelViewMatrix);
			if(tile.getOwner() != null)
			{
				shaderProvince.setUniform("color", tile.getOwner().getColor());
				shaderProvince.setUniform("owned", 1);
			}
			else
			{
				shaderProvince.setUniform("color", mesh.getColor());
				shaderProvince.setUniform("owned", 0);
			}
			int value = 0;
			if(tile.getModel().getSelected() == true)
			{
				value = 1;
			}
			shaderProvince.setUniform("selected", value);
			
			mesh.render();
		}
		
		shaderProvince.unbind();
	}
	
	private void renderHUD()
	{
		shaderHUD.bind();
		
		//shaderHUD.setUniform("textureSampler", 0);
		Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, 
				Main.window.getWindowWidth(), Main.window.getWindowHeight(), 0);
		
		for(Model model: huds)
		{
			Mesh mesh = model.getMesh();
			int hasTexture = 0;
			if(mesh.getTexture() != null)
				hasTexture = 1;
			
			Matrix4f projModelMatrix = transformation.buildOrtoProjModelMatrix(model, ortho);
			shaderHUD.setUniform("colour", mesh.getColor());
			shaderHUD.setUniform("hasTexture", hasTexture);
			shaderHUD.setUniform("projModelMatrix", projModelMatrix);
			mesh.render();
		}
		
		shaderHUD.unbind();
	}
	
	private void renderModels()
	{
		shaderProgram.bind();

		shaderProgram.setUniform("projectionMatrix", projectionMatrix);
		
		shaderProgram.setUniform("textureSampler", 0);
		
		for(Model model: models)
		{
			Mesh mesh = model.getMesh();
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(model, viewMatrix);
			int hasTexture = 0;
			if(mesh.getTexture() != null)
				hasTexture = 1;
			
			shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
			shaderProgram.setUniform("color", mesh.getColor());
			shaderProgram.setUniform("hasTexture", hasTexture);
			
			mesh.render();
		}
		
		shaderProgram.unbind();
	}
	
	private void initShaderProvince()
	{
		shaderProvince = new ShaderProgram();
		
		shaderProvince.createVertexShader(Utils.loadFileAsText("/shaders/province.vs"));
		shaderProvince.createFragmentShader(Utils.loadFileAsText("/shaders/province.fs"));
		shaderProvince.link();
		
		shaderProvince.createUniform("color");
		shaderProvince.createUniform("owned");
		shaderProvince.createUniform("textureSampler");
		shaderProvince.createUniform("projectionMatrix");
		shaderProvince.createUniform("modelViewMatrix");
		shaderProvince.createUniform("selected");
	}
	
	private void initShaderHUD()
	{
		shaderHUD = new ShaderProgram();
		
		shaderHUD.createVertexShader(Utils.loadFileAsText("/shaders/hud.vs"));
		shaderHUD.createFragmentShader(Utils.loadFileAsText("/shaders/hud.fs"));
		shaderHUD.link();
		
		shaderHUD.createUniform("projModelMatrix");
		//shaderHUD.createUniform("textureSampler");
		shaderHUD.createUniform("colour");
		shaderHUD.createUniform("hasTexture");
	}
	
	private void initShaderProgram()
	{
		shaderProgram = new ShaderProgram();
		
		shaderProgram.createVertexShader(Utils.loadFileAsText("/shaders/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadFileAsText("/shaders/fragment.fs"));
		shaderProgram.link();
		
		shaderProgram.createUniform("textureSampler");
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("modelViewMatrix");
		shaderProgram.createUniform("color");
		shaderProgram.createUniform("hasTexture");
	}
	
	public void cleanup()
	{
		shaderProgram.cleanup();
		shaderHUD.cleanup();
		shaderProvince.cleanup();
	}
	
	public void render()
	{
		GL11.glViewport(0, 0, Main.window.getWindowWidth(), Main.window.getWindowHeight());
		
		projectionMatrix = new Matrix4f(transformation.getProjectionMatrix(FOV, Main.window.getWindowWidth(), 
				Main.window.getWindowHeight(), Z_NEAR, Z_FAR));
		viewMatrix = transformation.getViewMatrix(Main.camera, viewMatrix);
		renderProvinces();
		renderModels();
		renderHUD();
	}
	
	public void clear()
	{
		models.clear();
		huds.clear();
		provinces.clear();
	}
	
	public void init()
	{
		initShaderProgram();
		initShaderHUD();
		initShaderProvince();
	}
	
	public Renderer()
	{
		Renderer.transformation = new Transformation();
		models = new ArrayList<>();
		huds = new ArrayList<>();
		provinces = new ArrayList<>();
		projectionMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}
}
