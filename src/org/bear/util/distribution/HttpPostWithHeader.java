package org.bear.util.distribution;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.parser.TaifexLotParser;

public class HttpPostWithHeader 
{
	public void getContent(String url, String date, JuristicDailyReportDao dao, TaifexLotParser parser)
	{
		try
		{
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			StringBuffer content = new StringBuffer();	
			System.out.println(url);
			URL urlConn = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection)urlConn.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true); 
			conn.setRequestProperty("Host", "www.taifex.com.tw");
			conn.setSSLSocketFactory(sslsocketfactory);
			DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
			dos.flush();
			dos.close();
			InputStream inputstream = conn.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "UTF-8");
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String string = null;
			while ((string = bufferedreader.readLine()) != null)
			{
			    //System.out.println(string);
				content.append(string + "\n");
			}
			parser.setDao(dao);
			parser.setDate(date);
			parser.parse(content.toString());
		}
		catch (IOException ex)
		{
			ex.printStackTrace();		
		}
	}
}
