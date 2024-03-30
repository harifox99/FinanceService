package org.bear.kd;

import java.io.*;
import java.net.URL;
import javax.net.ssl.*;

import org.bear.parser.GoodInfoParser;
/**
 * GoodInfo KD Parser
 * @author capital20180413
 *
 */
public class GoodInfoRequest 
{
	GoodInfoParser parser = new GoodInfoParser();
	public void conn(boolean isDay, String dateString)
	{
		try
		{
			StringBuffer content = new StringBuffer();
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			URL url;
			if (isDay)
				url = new URL("https://goodinfo.tw/StockInfo/StockList.asp?MARKET_CAT=%E6%99%BA%E6%85%A7%E9%81%B8%E8%82%A1&INDUSTRY_CAT=%E6%97%A5KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89%40%40%E6%97%A5KD%E7%9B%B8%E4%BA%92%E4%BA%A4%E5%8F%89%40%40KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89&SHEET=KD%E6%8C%87%E6%A8%99&SHEET2=%E6%97%A5%2F%E9%80%B1%2F%E6%9C%88&RPT_TIME=");
			else
				url = new URL("https://goodinfo.tw/StockInfo/StockList.asp?MARKET_CAT=%E6%99%BA%E6%85%A7%E9%81%B8%E8%82%A1&INDUSTRY_CAT=%E9%80%B1KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89%40%40%E9%80%B1KD%E7%9B%B8%E4%BA%92%E4%BA%A4%E5%8F%89%40%40KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89&SHEET=KD%E6%8C%87%E6%A8%99&SHEET2=%E6%97%A5%2F%E9%80%B1%2F%E6%9C%88&RPT_TIME=");
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestProperty("cookie", "_ga=GA1.2.795921985.1520179488; __gads=ID=944f3deb3d244b7c:T=1520179488:S=ALNI_Ma-xj15q7lM5grfZKetSJM6376OkA; CLIENT%5FID=20180414160428687%5F220%2E135%2E163%2E10; SCREEN_SIZE=WIDTH=1366&HEIGHT=768; _gid=GA1.2.25478762.1525869105; _gat=1");
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
			conn.setSSLSocketFactory(sslsocketfactory);
			InputStream inputstream = conn.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "UTF-8");
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String string = null;
			while ((string = bufferedreader.readLine()) != null)
			{
			    //System.out.println("Received " + string);
				content.append(string + "\n");
			}
			parser.parse(content.toString(), dateString, isDay);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception
    {
		GoodInfoRequest request = new GoodInfoRequest();
		request.conn(true, "2024/03/01");
		request.conn(false, "2024/03/01");
    }
}
