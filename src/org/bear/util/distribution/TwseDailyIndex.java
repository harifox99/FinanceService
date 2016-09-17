package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.TwseDailyDataParser;
import org.bear.util.HttpUtil;
/**
 * 台股每日指數&成交量資料
 * @author edward
 *
 */
public class TwseDailyIndex extends GetTaifexLot 
{
	public void getContent()
	{
		TwseDailyDataParser parser = new TwseDailyDataParser();
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		String[] dateArray = date.split("/");
		paramList.add(new BasicNameValuePair("query_year", dateArray[0]));
		paramList.add(new BasicNameValuePair("query_month", dateArray[1]));
		String responseString = HttpUtil.send(url, paramList, 1, "UTF-8");
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(0);	
	}
}
