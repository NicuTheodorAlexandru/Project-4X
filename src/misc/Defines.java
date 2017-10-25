package misc;

public class Defines
{
	public static int FRAMES_PER_SECOND;
	public static String title;
	public static float tileWidth;
	public static float tileHeight;
	public static int widthResolution;
	public static int heightResolution;
	public static String[] resourceTypes;
	public static String[] terrainTypes;
	
	public static void init()
	{
		resourceTypes = new String[]
				{
					"Food", "Wood"
				};
		
		terrainTypes = new String[]
				{
					"Plain", "Desert", "Water"	
				};
		
		tileWidth = tileHeight = 0.5f;
		widthResolution = 1280;
		heightResolution = 720;
		title = "4X Project";
		FRAMES_PER_SECOND = 60;
	}
}
