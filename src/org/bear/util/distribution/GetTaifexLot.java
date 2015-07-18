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

	public void getContent()
	{
		TaifexLotParser parser = new TaifexLotParser();		
		String[] dateArray = date.split("/");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("datestart", date));
		paramList.add(new BasicNameValuePair("DATA_DATE_Y", dateArray[0]));
		paramList.add(new BasicNameValuePair("DATA_DATE_M", dateArray[1]));
		paramList.add(new BasicNameValuePair("DATA_DATE_D", dateArray[2]));
		paramList.add(new BasicNameValuePair("syear", dateArray[0]));
		paramList.add(new BasicNameValuePair("smonth", dateArray[1]));
		paramList.add(new BasicNameValuePair("sday", dateArray[2]));		 	
		paramList.add(new BasicNameValuePair("COMMODITY_ID", ""));	
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(1);	
	}
}
