package org.bear.util.distribution;
import java.io.*;
import java.net.URL;
import javax.net.ssl.*;
import org.bear.dao.StockDistributionDao;
import org.bear.datainput.ImportStockID;
import org.bear.parser.GoodInfoDistributionPercentParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * GoodInfo的股權分配表(持股比率)
 * @author bear
 *
 */
public class StockDistributionGoodInfoPercent extends ImportStockID
{
	public void conn(String dateString, String week)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		StockDistributionDao dao = (StockDistributionDao)context.getBean("stockDistributionDao");	
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		List<String> stockList = new ArrayList<String>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/bear/Desktop/StockListBack.txt"));
			String readData;			
			while((readData = reader.readLine()) != null)
			{
				stockList.add(readData);
			}
			reader.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		for (int j = 0; j < wrapperList.size(); j++)
		{
			try
			{
				String STOCK_ID = wrapperList.get(j).getStockID();
				if (stockList.contains(STOCK_ID) == false)				
					continue;		
				else
					System.out.println("STOCK_ID: " + STOCK_ID);
				String DISPLAY_CAT = "持有比例區間分級一覽(完整)";
				DISPLAY_CAT = URLEncoder.encode(DISPLAY_CAT, "UTF-8");
				StringBuffer content = new StringBuffer();
				String urlString = "https://goodinfo.tw/tw/EquityDistributionClassHis.asp";
				URL url = new URL(urlString);
				HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Referer", "https://goodinfo.tw/tw/EquityDistributionClassHis.asp?STOCK_ID=1101&CHT_CAT=WEEK");
				conn.setRequestProperty("cookie", "_ga=GA1.2.795921985.1520179488; __gads=ID=944f3deb3d244b7c:T=1520179488:S=ALNI_Ma-xj15q7lM5grfZKetSJM6376OkA; CLIENT%5FID=20180414160428687%5F220%2E135%2E163%2E10; SCREEN_SIZE=WIDTH=1366&HEIGHT=768; _gid=GA1.2.25478762.1525869105; _gat=1");
				conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
				conn.setSSLSocketFactory(sslsocketfactory);
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				String postString = "STOCK_ID=" + STOCK_ID + "&CHT_CAT=WEEK&STEP=DATA&DISPLAY_CAT=" + DISPLAY_CAT;
				System.out.println(postString);
				dos.writeBytes(postString);
				dos.flush();
				dos.close();
				InputStream inputstream = conn.getInputStream();
				InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "UTF-8");
				BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
				String string = null;
				while ((string = bufferedreader.readLine()) != null)
				{
				    System.out.println("Received " + string);
					content.append(string + "\n");
				}
				GoodInfoDistributionPercentParser parser = new GoodInfoDistributionPercentParser();
				parser.parse(content.toString(), dateString, week, dao, STOCK_ID, true);
				Thread.sleep(3000);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	public static void main(String args[])
	{		
		String[] dateString = {"2023-01-01"};		
		String[] week = {"23W05"};
		for (int i = 0; i < dateString.length; i++)
		{
			StockDistributionGoodInfoPercent distribution = new StockDistributionGoodInfoPercent();
			distribution.conn(dateString[i], week[i]);
		}
	}
}
