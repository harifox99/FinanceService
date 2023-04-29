package org.bear.util.distribution;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import org.bear.dao.StockDistributionDao;
import org.bear.exception.TdccException;
import org.bear.parser.distribution.StockDistributionParser;
import javax.net.ssl.*;
public class StockDistributionGoodInfo 
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
		token = "450fbf82-e157-41af-aba2-657f193aa279";
		try
		{
			StockDistributionParser parser = new StockDistributionParser();
			parser.setDao(dao);
			parser.setStockID(stockID);
			parser.setCurrentMonth(isCurrentMonth);
			parser.setDateString(startYear + startMonth);
			String urlString = "https://www.tdcc.com.tw/portal/zh/smWeb/qryStock";
			String subUrl = "/portal/zh/smWeb/qryStock";
			//String SYNCHRONIZER_URI_ENCODE = URLEncoder.encode(subUrl, "UTF-8");
			/******************************************************/
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			String SYNCHRONIZER_TOKEN = URLEncoder.encode(token, "UTF-8");
			String SYNCHRONIZER_URI = URLEncoder.encode(subUrl, "UTF-8");
			String method = "submit";
			String firDate = "20230414";
			String scaDate = "20230414";
			String sqlMethod = "StockNo";
			String stockNo = "1101";
			/******************************************************/
			URL url = new URL(urlString);
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true); 
			conn.setRequestProperty("Cookie", "JSESSIONID=yOuz_3wI3cWnn3R9h91vD2L; JSESSIONID=0000a-y9GiUSckaVngdUhVPLtW-:19tmde4ae");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", "196");
			conn.setRequestProperty("Host", "www.tdcc.com.tw");
			//httpPost.setHeader("User-Agent", "PMozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setSSLSocketFactory(sslsocketfactory);
			/******************************************************/
			DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
			String postString = "SYNCHRONIZER_TOKEN=" + SYNCHRONIZER_TOKEN + "&SYNCHRONIZER_URI=" + SYNCHRONIZER_URI + "&method=" + method +
					"&firDate=" + firDate + "&scaDate=" + scaDate + "&sqlMethod=" + sqlMethod + "&stockNo=" + stockNo;
			System.out.println(postString);
			dos.writeBytes(postString);
			dos.flush();
			dos.close();
			InputStream inputstream = conn.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "UTF-8");
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String string = null;
			StringBuffer content = new StringBuffer();
			while ((string = bufferedreader.readLine()) != null)
			{
			    System.out.println("Received " + string);
				content.append(string + "\n");
			}
			parser.parse(7);	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
