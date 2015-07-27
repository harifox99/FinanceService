package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.TaifexOptionParser;
import org.bear.util.HttpUtil;
/**
 * 台指自營商選擇權未平倉餘額
 * @author edward
 *
 */
public class GetTaifexOption extends GetTaifexLot 
{
	public void getContent()
	{
		TaifexOptionParser parser = new TaifexOptionParser();		
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
		parser.parse(3);	
	}
}
