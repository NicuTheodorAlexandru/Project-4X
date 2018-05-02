package misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NanoVG;
import game.Nation;
import main.Main;

public class Utils
{	
	public static String[] getAllFilenames(String path)
	{
		File folder = new File(path);
		String[] filenames;
		int length = folder.listFiles().length;
		filenames = new String[length];
		int index = 0;
		for(File file: folder.listFiles())
		{
			filenames[index] = file.getName();
			index++;
		}
		return filenames;
	}
	
	public static Nation getNationFromFile(String filename)
	{
		filename = "src/nations/" + filename;
		Nation n = null;
		String name, adjective, religion;
		Vector3f color = new Vector3f();
		Scanner s = null;
		name = adjective = religion = "";
		try
		{
			s = new Scanner(new FileReader(filename));
			name = s.nextLine();
			adjective = s.nextLine();
			religion = s.nextLine();
			color.x = s.nextFloat();
			color.y = s.nextFloat();
			color.z = s.nextFloat();
			s.close();
		} catch (FileNotFoundException e)
		{
			System.err.println(e);
		}
		if(!name.equals("") && !adjective.equals("") && !religion.equals(""))
			n = new Nation(name, adjective, religion, color);
		return n;
	}
	
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) 
	{
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
	
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize)
	{
		//if(resource.equals("src/sounds/sndDiplomacy"))
			//System.out.println("!");
        ByteBuffer buffer = null;
        try
        {
        	Path path = Paths.get(resource);
            if(Files.isReadable(path)) 
            {
                try(SeekableByteChannel fc = Files.newByteChannel(path)) 
                {
                    buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                    while (fc.read(buffer) != -1);
                }
            } 
            else 
            {
                try 
                (
                    InputStream source = Utils.class.getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
                )
                {
                    buffer = BufferUtils.createByteBuffer(bufferSize);
                    while(true) 
                    {
                        int bytes = rbc.read(buffer);
                        if (bytes == -1) 
                            break;
                        if (buffer.remaining() == 0) 
                            buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
                catch(IOException err)
                {
                	System.err.println(err);
                }
            }
        }catch(IOException err)
        {
        	System.err.println(err);
        }
        buffer.flip();
        return buffer;
    }
	
	public static int getNanoVGImage(String fileName, int bufferSize)
	{
		ByteBuffer buffer = Utils.ioResourceToByteBuffer(fileName, bufferSize);
		int image = NanoVG.nvgCreateImageMem(Main.vg, NanoVG.NVG_IMAGE_GENERATE_MIPMAPS, buffer);
		return image;
	}
	
	public static List<String> readAllLines(String fileName) 
	{
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e)
		{
			System.err.println(e);
		}
        return list;
    }
	
	public static String loadFileAsText(String path)
	{
		String text = "";
		InputStream in = null;
		in = Utils.class.getResourceAsStream(path);
		InputStreamReader inr = null;
		try
		{
			inr = new InputStreamReader(in, "UTF-8");
		} catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			System.err.println(e1);
		}
		BufferedReader br = new BufferedReader(inr);
		String line;
		
		try
		{
			while((line = br.readLine()) != null)
			{
				text += line;
				text += "\r\n";
			}
		} catch (IOException e)
		{
			System.err.println(e);
		}
		
		try
		{
			br.close();
			in.close();
		} catch (IOException e)
		{
			System.err.println(e);
		}
		
		return text;
	}
}
