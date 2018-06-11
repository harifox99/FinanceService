package org.bear.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataCompare 
{
	HashMap<String, Boolean> hashData = new HashMap<String, Boolean>();
	public void compare()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/capital20180413/Desktop/Book1.csv"));
			String readData;
			String[] bufferData = null;
			while((readData = reader.readLine()) != null)
			{
				bufferData = readData.split(",");		
			}
			hashData.put(bufferData[0], true);
			reader.close();
			reader = new BufferedReader(new FileReader("C:/Users/capital20180413/Desktop/Book2.csv"));
			while((readData = reader.readLine()) != null)
			{
				bufferData = readData.split(",");		
				String stockId = bufferData[0];
				if (hashData.get(stockId) != null)
				{
					System.out.println(stockId);
				}
			}			
			reader.close();
		}
		catch (IOException ex)
		{
			
		}
	}
	public static void main(String args[])
	{
		new DataCompare();
	}
}
