package org.bear.util.distribution;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.StockDistributionDao;
import org.bear.exception.TdccException;
import org.bear.parser.distribution.StockDistributionParser;

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
			StockDistributionParser parser = new StockDistributionParser();
			parser.setDao(dao);
			parser.setStockID(stockID);
			parser.setCurrentMonth(isCurrentMonth);
			parser.setDateString(startYear + startMonth);
			String url = "https://www.tdcc.com.tw/portal/zh/smWeb/qryStock";
			String subUrl = "/portal/zh/smWeb/qryStock";
			//String SYNCHRONIZER_URI_ENCODE = URLEncoder.encode(subUrl, "UTF-8");
			/******************************************************/
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			/******************************************************/
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();		
			paramList.add(new BasicNameValuePair("SYNCHRONIZER_TOKEN", token));			
			paramList.add(new BasicNameValuePair("SYNCHRONIZER_URI", subUrl));
			paramList.add(new BasicNameValuePair("method", "submit"));		
			paramList.add(new BasicNameValuePair("firDate", "20230414"));
			paramList.add(new BasicNameValuePair("scaDate", "20230414"));
			paramList.add(new BasicNameValuePair("sqlMethod", "StockNo"));
			paramList.add(new BasicNameValuePair("stockNo", "1101"));
			paramList.add(new BasicNameValuePair("stockName", ""));
			httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
			/******************************************************/
			httpPost.setHeader("Cookie", "JSESSIONID=yOuz_3wI3cWnn3R9h91vD2L; JSESSIONID=0000a-y9GiUSckaVngdUhVPLtW-:19tmde4ae");
			//httpPost.setHeader("Cookie", "JSESSIONID=gvMgA7hxb3LPe6siUpQDiNa; _ga=GA1.3.981804023.1667022288; _fbp=fb.2.1667022288683.1803316909; JSESSIONID=00004SFSqYiv12OuzCnUN303704:19tmde622; _gid=GA1.3.473102104.1672639373; _gat=1");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			//httpPost.setHeader("Content-Length", "196");
			httpPost.setHeader("Host", "www.tdcc.com.tw");
			//httpPost.setHeader("User-Agent", "PMozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
			httpPost.setHeader("Accept", "*/*");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
			httpPost.setHeader("Connection", "keep-alive");
			//httpPost.setHeader("Referer", "https://www.tdcc.com.tw/portal/zh/smWeb/qryStock");
			/******************************************************/
			CloseableHttpResponse response = client.execute(httpPost);		
			String responseString = new BasicResponseHandler().handleResponse(response);
			//HttpResponse response = client.execute(httpPost);
			//HttpEntity entity = response.getEntity();
			//String responseString = EntityUtils.toString(entity, "UTF-8");
		    client.close();
			System.out.println(responseString);
			parser.setResponseString(responseString);
			parser.parse(7);	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
