package graphics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import main.Main;

public class TextItem
{
	private static final float ZPOS = 0.0f;
	private static final int VERTICES_PER_QUAD = 4;
	private String text;
	private Model model;
	private final TextFont fontTexture;
	
	public void update()
	{
		
	}
	
	public void render()
	{
		if(NanoVG.nvgFindFont(Main.vg, "Consolas") == -1)
		{
			String path = new File("D:/Downloads/Consolas.ttf").getAbsolutePath();
			NanoVG.nvgCreateFont(Main.vg, "Consolas", path);
		}
		NanoVG.nvgBeginPath(Main.vg);
		NanoVG.nvgFontSize(Main.vg, 20.0f);
		NanoVG.nvgFontFace(Main.vg, "Consolas");
		NanoVG.nvgTextAlign(Main.vg, NanoVG.NVG_ALIGN_LEFT);
		NVGColor color = NVGColor.create();
		float r = 100.0f, g = 100.0f, b = 100.0f, a = 255.0f;
		color.r(r / 255.0f);
		color.g(g / 255.0f);
		color.b(b / 255.0f);
		color.a(a / 255.0f);
		NanoVG.nvgFillColor(Main.vg, color);
		NanoVG.nvgFontBlur(Main.vg, 0);
		//float[] bound = new float[4];
		//NanoVG.nvgTextBounds(Main.vg, 50.0f, 50.0f, text, bound);
		//NanoVG.nvgRect(Main.vg, bound[0], bound[1], bound[2] - bound[0], bound[3] - bound[1]);
		//NanoVG.nvgFill(Main.vg);
		NanoVG.nvgText(Main.vg, model.getX(), model.getY(), text);
		//Renderer.huds.add(model);
	}
	
	public Model getModel()
	{
		return model;
	}
	
	public void setText(String text)
	{
		this.text = text;
		model.setMesh(buildMesh());
		Vector3f pos = new Vector3f(model.getRotation());
		Vector4f color = new Vector4f(model.getMesh().getColor());
		model.setPosition(pos);
		model.getMesh().setColor(color);
	}
	
	public String getText()
	{
		return text;
	}
	
	public TextItem(String text, TextFont fontTexture)
	{
		this.fontTexture = fontTexture;
		this.text = text;
		model = new Model(buildMesh());
	}
	
	private Mesh buildMesh()
	{
		List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        float[] normals  = new float[0];
        List<Integer> indices  = new ArrayList<>();
        char[] characters = text.toCharArray();
        int numChars = characters.length;

        float startx = 0.0f;
        for(int i=0; i<numChars; i++) {
            TextFont.CharInfo charInfo = fontTexture.getCharInfo(characters[i]);
            // Build a character tile composed by two triangles
      
            // Left Top vertex
            positions.add(startx); // x
            positions.add(0.0f); //y
            positions.add(ZPOS); //z
            textCoords.add((float)charInfo.getStartX() / (float)fontTexture.getWidth());
            textCoords.add(0.0f);
            indices.add(i*VERTICES_PER_QUAD);
                        
            // Left Bottom vertex
            positions.add(startx); // x
            positions.add((float)fontTexture.getHeight()); //y
            positions.add(ZPOS); //z
            textCoords.add((float)charInfo.getStartX() / (float)fontTexture.getWidth());
            textCoords.add(1.0f);
            indices.add(i*VERTICES_PER_QUAD + 1);

            // Right Bottom vertex
            positions.add(startx + charInfo.getWidth()); // x
            positions.add((float)fontTexture.getHeight()); //y
            positions.add(ZPOS); //z
            textCoords.add((float)(charInfo.getStartX() + charInfo.getWidth()) / (float)fontTexture.getWidth());
            textCoords.add(1.0f);
            indices.add(i*VERTICES_PER_QUAD + 2);

            // Right Top vertex
            positions.add(startx + charInfo.getWidth()); // x
            positions.add(0.0f); //y
            positions.add(ZPOS); //z
            textCoords.add((float)(charInfo.getStartX() + charInfo.getWidth()) / (float)fontTexture.getWidth());
            textCoords.add(0.0f);
            indices.add(i*VERTICES_PER_QUAD + 3);
            // Add indices por left top and bottom right vertices
            indices.add(i*VERTICES_PER_QUAD);
            indices.add(i*VERTICES_PER_QUAD + 2);
            
            startx += charInfo.getWidth();
        }
        
		float[] p = new float[positions.size()];
		float[] t = new float[textCoords.size()];
		int[] ind = new int[indices.size()];
		for(int i = 0; i < p.length; i++)
			p[i] = positions.get(i);
		for(int i = 0; i < t.length; i++)
			t[i] = textCoords.get(i);
		for(int i = 0; i < ind.length; i++)
			ind[i] = indices.get(i);
		
		Mesh mesh = new Mesh(p, t, normals, ind);
		mesh.setTexture((fontTexture.getTexture()));
		
		return mesh;
	}
}
