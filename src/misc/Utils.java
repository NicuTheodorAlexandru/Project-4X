package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Utils
{	
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
			// TODO Auto-generated catch block
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
