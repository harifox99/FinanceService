package org.bear.util;
import java.io.*;
import java.net.*;
/**
 * @author edward
 * 證交所專用URL Connection
 */
public class GetURLContent 
{	
	String urlString;	
	public GetURLContent(String urlString, String yearseason, String co_id)
	{
		this.urlString = urlString;
		this.urlString += "yearseason=" + yearseason + "&co_id=" + co_id;
	}
	public GetURLContent(String urlString)
	{
		this.urlString = urlString;
	}
	public BufferedReader getContent()
	{
		boolean isConnect = false;
		boolean isFirst = true;
		URL url;
		URLConnection urlConnection;
		BufferedReader htmlContent = null;
		while (isConnect == false)
		{
			try
			{
				url = new URL(urlString);
				urlConnection = url.openConnection();
				htmlContent = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				isConnect = true;
				if (isFirst == false)
					Thread.sleep(1000 * 30);
				/*
				while ((thisLine = htmlContent.readLine()) != null)
				{
					content = content + thisLine + "\n";
				}*/
			}
			catch (SocketException ex)
			{
				isConnect = false;
				isFirst = false;
				ex.printStackTrace();
				System.out.println("Socket Exception!");
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}			
		}
		return htmlContent;
	}
}
