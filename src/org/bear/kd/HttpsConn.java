package org.bear.kd;

import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsConn 
{
	public HttpsConn()
	{
		try
		{
			// URL
			String url = "https://goodinfo.tw/StockInfo/StockList.asp?MARKET_CAT=%E6%99%BA%E6%85%A7%E9%81%B8%E8%82%A1&INDUSTRY_CAT=%E6%97%A5KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89%40%40%E6%97%A5KD%E7%9B%B8%E4%BA%92%E4%BA%A4%E5%8F%89%40%40KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89&SHEET=KD%E6%8C%87%E6%A8%99&SHEET2=%E6%97%A5%2F%E9%80%B1%2F%E6%9C%88&RPT_TIME=";
	        URL myURL = new URL(url);
	        // HttpsURLConnectio¡ASSLSocketFactory
	        HttpsURLConnection httpsConn = (HttpsURLConnection) myURL
	                .openConnection();
	        // 
	        InputStreamReader insr = new InputStreamReader(httpsConn
	                .getInputStream());
	        // 
	        int respInt = insr.read();
	        while (respInt != -1) {
	            System.out.print((char) respInt);
	            respInt = insr.read();
	        }
		}
		catch (Exception ex)
		{
			
		}
	}
	public static void main(String[] args) throws Exception
	{
		new HttpsConn();
	}
}
