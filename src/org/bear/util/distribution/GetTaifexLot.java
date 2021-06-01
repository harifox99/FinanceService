package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.parser.TaifexLotParser;
import org.bear.util.HttpUtil;
/**
 * 期交所外資未平倉口數Http Post URL 
 * @author edward
 *
 */
public class GetTaifexLot 
{
	JuristicDailyReportDao dao;
	String date;
	String url;
	String commodityId;
	TaifexLotParser parser;
	int tableIndex;
	public JuristicDailyReportDao getDao() {
		return dao;
	}

	public void setDao(JuristicDailyReportDao dao) {
		this.dao = dao;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	
	public TaifexLotParser getParser() {
		return parser;
	}

	public void setParser(TaifexLotParser parser) {
		this.parser = parser;
	}
	
	public int getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(int tableIndex) {
		this.tableIndex = tableIndex;
	}

	public void getContent()
	{
		//TaifexLotParser parser = new TaifexLotParser();		
		//String[] dateArray = date.split("/");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("queryDate", date));
		paramList.add(new BasicNameValuePair("queryType", "1"));
		paramList.add(new BasicNameValuePair("doQuery", "1"));
		paramList.add(new BasicNameValuePair("commodityId", commodityId));	
		String responseString = HttpUtil.sendUrl(url, paramList, 1, "UTF-8");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(tableIndex);
	}
}
