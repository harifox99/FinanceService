package org.bear.parser.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * 可直接擷取下載檔案的資料
 * @author edward
 *
 */
public class FileParser 
{
	public void printResponse(BufferedReader reader)
	{
		try 
		{    
		    // Get the response
		    String line;
		    while ((line = reader.readLine()) != null) 
		    {
		        System.out.println(line);
		    }
		    reader.close();
		} 
		catch (Exception ignored) 
		{}
	}
	public List<String> getResponse(BufferedReader reader)
	{
		List<String> listData = new ArrayList<String>();
		try 
		{    
		    // Get the response
		    String line;
		    while ((line = reader.readLine()) != null) 
		    {
		    	listData.add(line);
		    }
		    reader.close();
		} 
		catch (Exception ignored) 
		{}
		return listData;
	}
}
