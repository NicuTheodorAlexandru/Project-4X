package misc;

import java.awt.Font;
import java.nio.ByteBuffer;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;
import graphics.Sprite;
import graphics.Texture;
import main.Main;

public class Assets
{
	//GUI sprites
	public static int imgBuild;
	//text defines
	public static String charset = "ISO-8859-1";
	public static Font fntArial = new Font("Consolas", Font.PLAIN, 20);
	//sprite defines
	public static float[] positions;
	public static int[] indices;
	public static float[] texCoords;
	public static float[] normals;
	//textures
	public static Texture texTerrainPlain;
	public static Texture texTerrainDesert;
	public static Texture texTerrainWater;
	//sprites
	public static Sprite sprTerrainPlain;
	public static Sprite sprTerrainDesert;
	public static Sprite sprTerrainWater;
	//models
	//meshes
	//levels and such
	
	public static void cleanup()
	{
		//textures
		texTerrainPlain.cleanup();
		texTerrainDesert.cleanup();
		texTerrainWater.cleanup();
		//sprites
		sprTerrainPlain.cleanup();
		sprTerrainDesert.cleanup();
		sprTerrainWater.cleanup();
		//
	}
	
	public static void initNano()
	{
		//fonts
		ByteBuffer buffer = null;
		buffer = Utils.ioResourceToByteBuffer("/fonts/Consolas.ttf", 450 * 1024);
		NanoVG.nvgCreateFontMem(Main.vg, "Consolas", buffer, 0);
		MemoryUtil.memFree(buffer);
		//images
		imgBuild = Utils.getNanoVGImage("/images/sprBuild.png", 16 * 1024);
	}
	
	public static void init()
	{
		positions = new float[]
				{
						-1.0f, -1.0f, 0.0f,
						1.0f, -1.0f, 0.0f,
						1.0f, 1.0f, 0.0f,
						-1.0f, 1.0f, 0.0f,
				};
		indices = new int[]
				{
						0, 1, 2, 
						2, 3, 0,
				};
		texCoords = new float[]
				{
					0.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					1.0f, 1.0f, 0.0f,
					0.0f, 1.0f, 0.0f,
				};
		normals = new float[]
				{
					0.0f, 0.0f, 1.0f,
					0.0f, 0.0f, 1.0f,
					0.0f, 0.0f, 1.0f,
					0.0f, 0.0f, 1.0f,
				};
		
		texTerrainPlain = new Texture("/images/sprTerrainPlain.png");
		texTerrainDesert = new Texture("/images/sprTerrainDesert.png");
		texTerrainWater = new Texture("/images/sprTerrainWater.png");
		
		sprTerrainPlain = new Sprite(texTerrainPlain);
		sprTerrainDesert = new Sprite(texTerrainDesert);
		sprTerrainWater = new Sprite(texTerrainWater);
	}
}
