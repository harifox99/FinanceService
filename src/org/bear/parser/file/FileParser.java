package org.bear.parser.file;

import java.io.*;
/**
 * 可直接擷取下載的檔案資料
 * @author edward
 *
 */
public class FileParser 
{
	public void getResponse(BufferedReader reader)
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
	
}
