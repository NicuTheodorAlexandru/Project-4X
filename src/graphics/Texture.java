package graphics;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture
{
	private int textureID = -1;
	private int width, height;
	
	public void cleanup()
	{
		GL11.glDeleteTextures(textureID);
	}
	
	public int getTextureID()
	{
		return textureID;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}

	public Texture(InputStream is)
	{
		try
		{
			PNGDecoder decoder = new PNGDecoder(is);
			width = decoder.getWidth();
			height = decoder.getHeight();
			ByteBuffer buffer = ByteBuffer.allocateDirect
				(4 * decoder.getWidth() * decoder.getHeight());
			textureID = GL11.glGenTextures();
			
			decoder.decode(buffer, 4 * decoder.getWidth(), Format.RGBA);
			buffer.flip();
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			
			GL11.glTexImage2D
				(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height,
						0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		}catch(IOException e)
		{
			System.err.println(e);
		}
	}
	
	public Texture(String filename)
	{
		this(Texture.class.getResourceAsStream(filename));
	}
}
