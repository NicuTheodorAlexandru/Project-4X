package misc;

import java.awt.Font;
import java.nio.ByteBuffer;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import audio.SoundBuffer;
import audio.SoundSource;
import graphics.Sprite;
import graphics.Texture;
import main.Main;

public class Assets
{
	//GUI sprites
	public static int imgBuild;
	public static int imgRecruit;
	public static int imgDiplomacy;
	public static int[] imgSpeed;
	//text defines
	public static String charset = "ISO-8859-1";
	public static Font fntArial = new Font("Consolas", Font.PLAIN, 20);
	//font bytebuffers
	public static ByteBuffer fntConsolas;
	//pie chart colors
	//jobs
	public static NVGColor cUnemployed;
	public static NVGColor cSoldier;
	public static NVGColor cFarmer;
	public static NVGColor cWoodcutter;
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
	}
	
	public static void initSounds()
	{
		SoundSource source = null;
		SoundBuffer buffer = null;
		buffer = new SoundBuffer("/sounds/sndDiplomacy.ogg");
		Main.soundManager.addSoundBuffer(buffer);
		source = new SoundSource(false, false);
		source.setBuffer(buffer.getBufferID());
		Main.soundManager.addSoundSource("Diplomacy", source);
	}
	
	public static void initNano()
	{
		//fonts
		fntConsolas = Utils.ioResourceToByteBuffer("/fonts/Consolas.ttf", 450 * 1024);
		NanoVG.nvgCreateFontMem(Main.vg, "Consolas", fntConsolas, 0);
		//MemoryUtil.memFree(buffer);
		//images
		imgBuild = Utils.getNanoVGImage("/images/sprBuild.png", 32 * 1024);
		imgDiplomacy = Utils.getNanoVGImage("/images/sprDiplomacy.png", 64 * 1024);
		imgRecruit = Utils.getNanoVGImage("/images/sprRecruit.png", 32 * 1024);
		imgSpeed = new int[6];
		imgSpeed[0] = Utils.getNanoVGImage("/images/sprSpeed0.png", 16 * 1024);
		imgSpeed[1] = Utils.getNanoVGImage("/images/sprSpeed1.png", 16 * 1024);
		imgSpeed[2] = Utils.getNanoVGImage("/images/sprSpeed2.png", 16 * 1024);
		imgSpeed[3] = Utils.getNanoVGImage("/images/sprSpeed3.png", 16 * 1024);
		imgSpeed[4] = Utils.getNanoVGImage("/images/sprSpeed4.png", 16 * 1024);
		imgSpeed[5] = Utils.getNanoVGImage("/images/sprSpeed5.png", 18 * 1024);
		//colors
		cUnemployed = NVGColor.create();
		cUnemployed.a(1.0f);
		cUnemployed.r(1.0f);
		cSoldier = NVGColor.create();
		cSoldier.a(1.0f);
		cSoldier.b(1.0f);
		cFarmer = NVGColor.create();
		cFarmer.a(1.0f);
		cFarmer.g(1.0f);
		cWoodcutter = NVGColor.create();
		cWoodcutter.a(1.0f);
		cWoodcutter.r(1.0f);
		cWoodcutter.g(1.0f);
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
