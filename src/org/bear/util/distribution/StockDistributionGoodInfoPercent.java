package org.bear.util.distribution;
import java.io.*;
import java.net.URL;
import javax.net.ssl.*;
import org.bear.dao.StockDistributionDao;
import org.bear.datainput.ImportStockID;
import org.bear.parser.distribution.GoodInfoDistributionPercentParser;
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
	public void conn(String[] dateString, String[] week)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		StockDistributionDao dao = (StockDistributionDao)context.getBean("stockDistributionDao");	
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		List<String> stockList = new ArrayList<String>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/bear/Desktop/StockList.txt"));
			String readData;			
			while((readData = reader.readLine()) != null)
			{
				stockList.add(readData.trim());
			}
			reader.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		for (int j = 1583; j < wrapperList.size(); j++)
		{
			try
			{
				String STOCK_ID = wrapperList.get(j).getStockID();
				if (stockList.contains(STOCK_ID) == false)				
					continue;		
				else
					System.out.println("STOCK_ID: " + STOCK_ID + "; Key = " + j);
				String DISPLAY_CAT = "持有比例區間分級一覽(完整)";
				DISPLAY_CAT = URLEncoder.encode(DISPLAY_CAT, "UTF-8");
				StringBuffer content = new StringBuffer();
				String urlString = "https://goodinfo.tw/tw/data/EquityDistributionClassHis.asp";
				URL url = new URL(urlString);
				HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Referer", "https://goodinfo.tw/tw/EquityDistributionClassHis.asp?STOCK_ID=" + STOCK_ID);
				conn.setRequestProperty("cookie", "CLIENT%5FID=20260124095918476%5F114%2E35%2E37%2E71; _ga_0LP5MLQS7E=GS2.1.s1777804660$o3$g1$t1777804760$j56$l0$h0; _ga=GA1.1.1102654660.1769219964; cto_bundle=YUyFVV80cm5ZbFB4eVlVMHZtalA3SE9HdTk2a1pjQlBEbGRiYUhGbHJzMWlxejBreVJZYmhxdll5JTJCbmdqR3Y2T2tHVHhmVWlpSHZZMiUyQlNsdjlnWEtGV3lMb3EyRWNMaSUyRmxLQWM1JTJGdFV2NzhXdUtWVXJrUmpaZXpBTGNzTGZrWUpkYXdqMUw0Z0IlMkJyckRQM0NVTHlrUkpDZVdubGxKRktQb3B1JTJCODV1NDdiZFJZSjglM0Q; cto_bidid=jbT2sl9oM2xnenc2a1dUSWVqYkhIREJ1eUtMOGZZQXUzd1clMkZ4WVpobDFVaTU3JTJGZjBlbnBGV2RNc3ZzeEZoVHdQU00wa25qOUZTU0VuaU8yVVpyaXZwSFIlMkZ6Vzh6OGwzdWl6JTJCZWhESVVmMmZqOU9yYW5HNmY4VHNGOHA5UDlueEJCakhGZDJsT0h6dk9iRGd4V21VRkE4QjdrUSUzRCUzRA; _cc_id=ca110fe4f21cef7a20193cb896c61b22; __gads=ID=6000e596dad82ac9:T=1769219963:RT=1777804661:S=ALNI_MaAmdvpPTPB_qXLpbS72RjqT3uzVA; __gpi=UID=000011ea00ce5e51:T=1769219963:RT=1777804661:S=ALNI_MamjzYgUt8Ys7VG9jWJLrq94-c5sQ; __eoi=ID=9a9ded88d4abe1fa:T=1769219963:RT=1777804661:S=AA-AfjY4fmxfS07jkEXbIOdF9fa5; TW_STOCK_BROWSE_LIST=1101; cto_dna_bundle=jAVg6F80WFRLeCUyQm93dzk3aVJUczlqS0k5JTJCWTYwS2M1YmVzVEs5VnpvWkxEZTBtZHlLZ1FMYk1pdWRBam11TmVLVGp2MXQ2dWZuQzRheUc5c1N2JTJGQUlCZVJBUSUzRCUzRA; CLIENT_KEY=2.2%7C44125.5752251684%7C46347.7974473906%7C-480%7C46145.77727909722%7C46145.79715644676%7C95.41299962438643; SCREEN_WIDTH=1536; SCREEN_HEIGHT=864; IS_TOUCH_DEVICE=F; FCCDCF=%5Bnull%2Cnull%2Cnull%2Cnull%2Cnull%2Cnull%2C%5B%5B32%2C%22%5B%5C%22f819f4a8-8f15-4295-853c-a4d2d0eef823%5C%22%2C%5B1769219964%2C413000000%5D%5D%22%5D%5D%5D; _pubcid=5ef05bee-6129-4262-afd8-f555259ae0ea; panoramaId_expiry=1778409559075; panoramaId=0eae497132a0e084ae5977b8cc4f185ca02c940862f12da977ec345084c5cc0f; panoramaIdType=panoDevice; FCNEC=%5B%5B%22AKsRol-r10_uBWFh1ndaHYpY3en7PVvYTBNfETzwzuvoGXzLgGQ3-bEVFfmRaF-OoJgpukfl4sPBIwLVqc92uiIoGAZbGYgRKZYWkjW2JcpPrcB603wBdZ79dTSXg8Z3-HBNXyB5v-dP6-Iw1UN004dcwQpgjLOIrA%3D%3D%22%5D%5D");
				conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:146.0) Gecko/20100101 Firefox/146.0");
				conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
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
					content.append(string + "\n");
				}
				//System.out.println(content);
				GoodInfoDistributionPercentParser parser = new GoodInfoDistributionPercentParser();
				parser.parse(content.toString(), dateString, week, dao, STOCK_ID, true);
				Thread.sleep(10000);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	public static void main(String args[])
	{		
		String[] dateString = {"2026-04-02", "2026-04-10", "2026-04-17", "2026-04-24"};		
		String[] week = {"26W14", "26W15", "26W16", "26W17"};
		StockDistributionGoodInfoPercent distribution = new StockDistributionGoodInfoPercent();
		distribution.conn(dateString, week);
	}
}
