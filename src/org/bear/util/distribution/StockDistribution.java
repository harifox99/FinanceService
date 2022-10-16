package org.bear.util.distribution;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import org.bear.dao.StockDistributionDao;
import org.bear.exception.TdccException;
import org.bear.parser.distribution.StockDistributionParser;
import javax.net.ssl.*;
public class StockDistribution 
{
	StockDistributionDao dao;
	/**
	 * 集保股權因為是週資料，所以有時當月的資料要去下個月去抓 (7月的資料要去8月抓)，所以由此參數，來決定月資料要不要減1
	 * 抓到8月的資料 (但其實是7月的資料後)，減1後儲存
	 * isCurrentMonth = false, 轉換月份
	 * isCurrentMonth = true,  不轉換月份
	 */
	boolean isCurrentMonth;
	public boolean isCurrentMonth() {
		return isCurrentMonth;
	}
	public void setCurrentMonth(boolean isCurrentMonth) {
		this.isCurrentMonth = isCurrentMonth;
	}
	public StockDistributionDao getDao() {
		return dao;
	}
	public void setDao(StockDistributionDao dao) {
		this.dao = dao;
	}
	public void getContent(String stockID, String startYear, String startMonth, String endYear, String endMonth, String token) throws TdccException
	{
		try
		{
			StringBuffer content = new StringBuffer();
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			StockDistributionParser parser = new StockDistributionParser();
			parser.setDao(dao);
			parser.setStockID(stockID);
			parser.setCurrentMonth(isCurrentMonth);
			parser.setDateString(startYear + startMonth);
			URL url = new URL("https://www.tdcc.com.tw/portal/zh/smWeb/qryStock?");
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);        
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
			conn.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Content-Length", "196");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("cookie", "JSESSIONID=aOes-vzfWrBZKeNd2lsBNmX; _ga=GA1.3.730827645.1665751778; _fbp=fb.2.1665751778503.984260935; JSESSIONID=00009H3jWMAUoPKm0K-jsNh1hmx:19tmde4ae; _gid=GA1.3.1348963233.1665895596");
			conn.setRequestProperty("Host", "www.tdcc.com.tw");
			conn.setRequestProperty("Origin", "https://www.tdcc.com.tw");
			conn.setRequestProperty("Referer", "https://www.tdcc.com.tw/portal/zh/smWeb/qryStock");
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0");
			
			
			conn.setSSLSocketFactory(sslsocketfactory);
			DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
			String SYNCHRONIZER_URI_ENCODE = URLEncoder.encode("/portal/zh/smWeb/qryStock", "UTF-8");
			String postString = "SYNCHRONIZER_TOKEN=" + token + "&SYNCHRONIZER_URI=" + SYNCHRONIZER_URI_ENCODE + "&method=submit&firDate=20221014&scaDate=20221014&sqlMethod=StockNo&stockNo=1101&stockName=";
			System.out.println(postString);
			///////////////////////////////////
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
			System.out.println(content.toString());
			parser.parse(7);	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
