package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.TwseDailyIndexParser;
import org.bear.util.HttpUtil;
/**
 * 台股每日指數資料
 * @author edward
 *
 */
public class TwseDailyIndex extends GetTaifexLot {
	public void getContent()
	{
		TwseDailyIndexParser parser = new TwseDailyIndexParser();		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("selecType", "ms"));
		paramList.add(new BasicNameValuePair("qdate", date));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDate(date);
		parser.parse(0);	
	}
}
