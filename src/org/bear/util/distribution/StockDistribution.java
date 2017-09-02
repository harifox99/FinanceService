package org.bear.util.distribution;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.StockDistributionDao;
import org.bear.parser.distribution.StockDistributionParser;
import org.bear.util.HttpUtil;

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
	public void getContent(String stockID, String startYear, String startMonth, String endYear, String endMonth)
	{
		StockDistributionParser parser = new StockDistributionParser();
		parser.setDao(dao);
		parser.setStockID(stockID);
		parser.setCurrentMonth(isCurrentMonth);
		parser.setDateString(startYear + startMonth);
		String url = "http://www.tdcc.com.tw/smWeb/QryStock.jsp";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("SCA_DATE", startYear + startMonth));
		paramList.add(new BasicNameValuePair("SqlMethod", "StockNo"));
		paramList.add(new BasicNameValuePair("StockNo", stockID));
		paramList.add(new BasicNameValuePair("sub", "查詢"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.parse(7);	
	}
}
