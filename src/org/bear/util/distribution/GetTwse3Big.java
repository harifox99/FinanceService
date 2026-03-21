package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.ThreeBigDao;
import org.bear.parser.distribution.Twse3BigParser;
import org.bear.util.HttpUtil;

public class GetTwse3Big 
{
	ThreeBigDao threeBigDao;
	
	public ThreeBigDao getThreeBigDao() {
		return threeBigDao;
	}

	public void setThreeBigDao(ThreeBigDao threeBigDao) {
		this.threeBigDao = threeBigDao;
	}

	public void getContent(String stockID, String startYear, String startMonth, String endYear, String endMonth)
	{
		Twse3BigParser parser = new Twse3BigParser();
		parser.setThreeBigDao(threeBigDao);
		parser.setDateString(startYear + startMonth);
		String url = "http://www.twse.com.tw/ch/trading/fund/TWT47U/TWT47U.php";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("yr", startYear));
		paramList.add(new BasicNameValuePair("w_date", startYear + startMonth + "01"));
		//證券種類若為個位數補0
		if (stockID.length() <= 1)
			paramList.add(new BasicNameValuePair("select2", "0" + stockID));
		else
			paramList.add(new BasicNameValuePair("select2", stockID));
		paramList.add(new BasicNameValuePair("value", "查詢"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.parse(7);	
	}
}
