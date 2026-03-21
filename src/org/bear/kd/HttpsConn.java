package org.bear.kd;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

public class HttpsConn
{	
	public static void main(String[] args)
	{
		//String https_url = "https://goodinfo.tw/StockInfo/StockList.asp?MARKET_CAT=%E6%99%BA%E6%85%A7%E9%81%B8%E8%82%A1&INDUSTRY_CAT=%E6%97%A5KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89%40%40%E6%97%A5KD%E7%9B%B8%E4%BA%92%E4%BA%A4%E5%8F%89%40%40KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89&SHEET=KD%E6%8C%87%E6%A8%99&SHEET2=%E6%97%A5%2F%E9%80%B1%2F%E6%9C%88&RPT_TIME=";
		String https_url = "https://goodinfo.tw/tw/EquityDistributionClassHis.asp?STOCK_ID=2337&CHT_CAT=WEEK";
        new HttpsConn().testIt(https_url);
	}
	private void testIt(String https_url)
	{
		URL url;
		try 
		{
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			url = new URL(https_url);
		   	HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		   	con.setRequestProperty("cookie", "_ga=GA1.2.795921985.1520179488; __gads=ID=944f3deb3d244b7c:T=1520179488:S=ALNI_Ma-xj15q7lM5grfZKetSJM6376OkA; CLIENT%5FID=20180414160428687%5F220%2E135%2E163%2E10; SCREEN_SIZE=WIDTH=1366&HEIGHT=768; _gid=GA1.2.25478762.1525869105; _gat=1");
			con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
			con.setSSLSocketFactory(sslsocketfactory);
		    //dumpl all cert info
		   	print_https_cert(con);
		    //dump all the content
		   	print_content(con);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
        }
    }
	
    private void print_https_cert(HttpsURLConnection con)
    { 
    	if (con != null)
    	{
    		try 
    		{
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");		
				Certificate[] certs = con.getServerCertificates();
				for(Certificate cert : certs)
				{
				    System.out.println("Cert Type : " + cert.getType());
				    System.out.println("Cert Hash Code : " + cert.hashCode());
				    System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
				    System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
				    System.out.println("\n");
				}
			} 
    		catch (SSLPeerUnverifiedException e) 
    		{
				e.printStackTrace();
			} 
    		catch (IOException e)
    		{
				e.printStackTrace();
			}
    	}
    }
	
    private void print_content(HttpsURLConnection con)
    {
    	if(con!=null)
    	{	
    		try 
    		{
			    System.out.println("****** Content of the URL ********");			
			    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			    String input;	
			    while ((input = br.readLine()) != null)
			    {
			        System.out.println(input);
			    }
			    br.close();
    		} 
    		catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
        }
    }
}
