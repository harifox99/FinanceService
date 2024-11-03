package org.bear.test;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.bear.parser.file.FileParser;
import java.io.*;
public class HttpsGetRequest 
{
	public HttpsGetRequest() {}
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
		/*
		URL serverUrl = new URL("https://www.taifex.com.tw/cht/3/futContractsDate?queryType=1&goDay=&doQuery=1&dateaddcnt=&queryDate=2024%2F09%2F13&commodityId=");
	    HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");
	    conn.setInstanceFollowRedirects(false);
        conn.connect();
	    String result = getReturn(conn);
	    System.out.println(result);*/
	    String url = "https://mops.twse.com.tw/server-java/t105sb02";
		HttpsGetRequest request = new HttpsGetRequest();
		request.httpPost(url);
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
	/**
	 * HTTP Post with Downloading File
	 * @param url
	 */
	public void httpPost(String url)
	{
		URL serverUrl;
		BufferedReader htmlContent = null;
		try 
		{
			serverUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();
			urlConnection.setDoOutput(true);
	        urlConnection.setRequestMethod("POST");
	        // Writing the post data to the HTTP request body
	        BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
	        httpRequestBodyWriter.write("firstin=true&step=10&filename=t51sb01_20241103_23491264.csv");
	        httpRequestBodyWriter.close();
	        htmlContent = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "Big5"));
	        FileParser fileParser = new FileParser();
	        List<String> listData = fileParser.getResponse(htmlContent);
	        for (int i = 0; i < listData.size(); i++)
	        	System.out.println(listData.get(i));
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
