package org.bear.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;

public class HttpsGetRequest 
{
	public HttpsGetRequest(String urlString)
	{
		try
		{
			URL serverUrl = new URL(urlString);
		    HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
		    conn.setRequestMethod("GET");
		    conn.setRequestProperty("Content-type", "application/json");
		    conn.setInstanceFollowRedirects(false);
	        conn.connect();
		    String result = getReturn(conn);
		    System.out.println(result);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception 
	{
		URL serverUrl = new URL("https://www.taifex.com.tw/cht/3/futContractsDate?queryType=1&goDay=&doQuery=1&dateaddcnt=&queryDate=2024%2F09%2F13&commodityId=");
	    HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");
	    conn.setInstanceFollowRedirects(false);
        conn.connect();
	    String result = getReturn(conn);
	    System.out.println(result);
	}
	public static String getReturn(HttpURLConnection connection) throws Exception
	{
        StringBuffer buffer = new StringBuffer();
		InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = 
        new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    
        String str = null;
        while ((str = bufferedReader.readLine()) != null) 
        {
            buffer.append(str);
        }
        String result = buffer.toString();
        return result;
    }
}
