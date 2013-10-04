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
		parser.setDateString(startYear + startMonth);
		String url = "http://www.tdcc.com.tw/smWeb/QryStock.jsp";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("SCA_DATE", startYear + startMonth));
		paramList.add(new BasicNameValuePair("SqlMethod", "StockNo"));
		paramList.add(new BasicNameValuePair("StockNo", stockID));
		paramList.add(new BasicNameValuePair("sub", "¬d¸ß"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.parse(7);	
	}
}
