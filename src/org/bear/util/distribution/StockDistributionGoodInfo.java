package org.bear.util.distribution;
import java.io.*;
import java.net.URL;
import javax.net.ssl.*;
import org.bear.dao.StockDistributionDao;
import org.bear.datainput.ImportStockID;
import org.bear.parser.distribution.GoodInfoDistributionParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * GoodInfo的股權分配表
 * @author bear
 *
 */
public class StockDistributionGoodInfo extends ImportStockID
{
	public void conn(String dateString, String week)
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
		for (int j = 0; j < wrapperList.size(); j++)
		{
			try
			{
				String STOCK_ID = wrapperList.get(j).getStockID();
				if (stockList.contains(STOCK_ID) == false)				
					continue;		
				else
					System.out.println("STOCK_ID: " + STOCK_ID);
				String DISPLAY_CAT = "持有張數區間分級一覽(完整)";
				DISPLAY_CAT = URLEncoder.encode(DISPLAY_CAT, "UTF-8");
				StringBuffer content = new StringBuffer();
				String urlString = "https://goodinfo.tw/tw/data/EquityDistributionClassHis.asp";
				URL url = new URL(urlString);
				HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Referer", "https://goodinfo.tw/tw/EquityDistributionClassHis.asp?STOCK_ID=1101&CHT_CAT=WEEK");
				conn.setRequestProperty("cookie", "__qca=I0-1823878349-1767517485950; CLIENT%5FID=20260104164959683%5F36%2E224%2E189%2E134; CLIENT_KEY=2.1%7C45016.6112352694%7C46127.7223463805%7C-480%7C20457.71230355324%7C20457.719879201388; SCREEN_WIDTH=1280; SCREEN_HEIGHT=720; IS_TOUCH_DEVICE=F; _ga_0LP5MLQS7E=GS2.1.s1767517256$o1$g1$t1767518197$j60$l0$h0; _ga=GA1.1.1447416186.1767517256; _cc_id=7ad3f3222483775f75f19f55b45ab52b; panoramaId_expiry=1768122055208; panoramaId=f61840aaf4179b34d65a2513499c4945a702b9bda85ead4c9887034181bfc0f8; panoramaIdType=panoIndiv; cto_bundle=OWJlHV92REtMNnd4RnVESklxOTJWbFNNTkxOd05pUjNUTmxZZkslMkJMUiUyRkhKbU9IdzVOZTFWYVM1RVRJaUdMOTBOR0twUFZTJTJGTHpiY2ZZdFZjS2lSaHptc3lOYWdyNE1UNmZpZU9PVzFMbTg5S2kzJTJGaVR2a1k4QmN6emVHdHN4MHdhWHB5eCUyRmloM2JUdFIlMkZ3YVRHVkZsZFYlMkZUdHp3eE02MFozc2olMkZSdUNvalhqQXdNJTNE; cto_bidid=ADiZw18yOGdUWktwRVAlMkJQMTlwbVRoM28yV0RFS0pIclZFajR3S1NnUWlnQTVBUmk4N0hIeDJqWVFheENBWGN1UnBSczE2UzFwa0p0YUZNbE4yV3JoVHVxUmhHOUltNnA2U2EzQmRtJTJCMEpEWkd5RjREaGlWVm9KNXAlMkJOR3R0NlklMkZTQ2wwZWk4RERGcnlOWDZEckJMdU5UajlwZyUzRCUzRA; __gads=ID=579360ad66bfabbc:T=1767517255:RT=1767517702:S=ALNI_MaR-DcdFoFB_2vjKGGHbtkliBCl9w; __gpi=UID=000011dc0465081c:T=1767517255:RT=1767517702:S=ALNI_MbxvR-6GCCeWtZA4aqG9TD_-3M3Kw; __eoi=ID=03aca2eadac08f02:T=1767517255:RT=1767517702:S=AA-AfjbeBUZUg2u9jzKACncyZIUw; cto_dna_bundle=EUrG9V92REtMNnd4RnVESklxOTJWbFNNTkxPcURYbVFlSEgyV1dad1FUM2F4ZmlZN0lNdURZT202SG1ZVDdHbjh5WXdQdGhVUmZhSGkzeE40VzVGZks4ajVtQSUzRCUzRA; FCCDCF=%5Bnull%2Cnull%2Cnull%2Cnull%2Cnull%2Cnull%2C%5B%5B32%2C%22%5B%5C%22e100d0c7-8e1e-4826-9b35-1f288a174de0%5C%22%2C%5B1767517257%2C37000000%5D%5D%22%5D%5D%5D; FCNEC=%5B%5B%22AKsRol_n8wn1jr8raKNEeCAy7TUtAVm-mg8o_9RgsdeuJHHa9v61Y_1jVgCI1KptjpKO8ff8H_DPFP2kl-iswEXxZmp3zH8XTCkJbN6t97ngx1HxSsjUEOf4Jz8dVztnN1zLdHZzozvCJt44cep5JQoqMk8FRNaG6w%3D%3D%22%5D%5D");
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
				    //System.out.println("Received " + string);
					content.append(string + "\n");
				}
				GoodInfoDistributionParser parser = new GoodInfoDistributionParser();
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
		String[] dateString = {"202511"};		
		String[] week = {"25W48"};
		for (int i = 0; i < dateString.length; i++)
		{
			StockDistributionGoodInfo distribution = new StockDistributionGoodInfo();
			distribution.conn(dateString[i], week[i]);
		}
	}
}
